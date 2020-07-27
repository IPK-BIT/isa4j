import time
import csv
import os
import gc
from datetime import datetime
from memory_profiler import memory_usage
from isatools.model import *
from isatools import isatab

# This is the first method of 3 that measures how long it takes to write n_rows in Study and Assay File each.
# It returns the CPU execution time in NS
# The rows in this example are as simple as possible, no extra Characteristics, FactorValues etc.
# It is a simplified version of the example given in the docs: https://isatools.readthedocs.io/en/latest/example-createSimpleISAtab.html
def measure_minimal(n_rows):
    starting_time = time.process_time_ns()
    investigation = Investigation()
    investigation.identifier = "i1"

    study = Study(filename="s_study.txt")
    study.identifier = "s1"
    investigation.studies.append(study)

    sample_collection_protocol = Protocol(name="sample collection")
    study.protocols.append(sample_collection_protocol)


    for i in range(0, n_rows):
        source = Source(name='source_material-{}'.format(i))
        sample = Sample(name="sample_material-{}".format(i))
        study.samples.append(sample)
        study.sources.append(source)

        sample_collection_process = Process(executes_protocol=sample_collection_protocol)
        sample_collection_process.inputs.append(source)
        sample_collection_process.outputs.append(sample)

        study.process_sequence.append(sample_collection_process)

    assay = Assay(filename="a_assay.txt")
    sequencing_protocol = Protocol(name='sequencing', protocol_type=OntologyAnnotation(term="material sequencing"))
    study.protocols.append(sequencing_protocol)

    for i, sample in enumerate(study.samples):

       sequencing_process = Process(executes_protocol=sequencing_protocol)
       sequencing_process.name = "assay-name-{}".format(i)
       sequencing_process.inputs.append(sample)

       datafile = DataFile(filename="sequenced-data-{}".format(i), label="Raw Data File", generated_from=[sample])
       sequencing_process.outputs.append(datafile)

       assay.samples.append(sample)
       assay.data_files.append(datafile)
       assay.process_sequence.append(sequencing_process)

    study.assays.append(assay)

    isatab.dump(investigation, "./")
    return time.process_time_ns() - starting_time


# This is the second complexity level. Basically the example from the docs,
# just without Investigation File Attributes:
# https://isatools.readthedocs.io/en/latest/example-createSimpleISAtab.html
# Also, batch_create_materials was replaced with a manual loop because it was super slow
def measure_reduced(n_rows):
    starting_time = time.process_time_ns()
    investigation = Investigation()
    investigation.identifier = "i1"

    study = Study(filename="s_study.txt")
    study.identifier = "s1"
    investigation.studies.append(study)

    sample_collection_protocol = Protocol(name="sample collection")
    study.protocols.append(sample_collection_protocol)

    ncbitaxon = OntologySource(name='NCBITaxon', description="NCBI Taxonomy")
    characteristic_organism = Characteristic(category=OntologyAnnotation(term="Organism"),
            value=OntologyAnnotation(term="Homo Sapiens", term_source=ncbitaxon,
                term_accession="http://purl.bioontology.org/ontology/NCBITAXON/9606"))
    for i in range(0, n_rows):
        source = Source(name='source_material-{}'.format(i))
        sample = Sample(name="sample_material-{}".format(i))
        sample.characteristics.append(characteristic_organism)
        study.samples.append(sample)
        study.sources.append(source)

        sample_collection_process = Process(executes_protocol=sample_collection_protocol)
        sample_collection_process.inputs.append(source)
        sample_collection_process.outputs.append(sample)

        study.process_sequence.append(sample_collection_process)

    # Next, we build n Assay object and attach two protocols, extraction and sequencing.
    assay = Assay(filename="a_assay.txt")
    extraction_protocol = Protocol(name='extraction')
    study.protocols.append(extraction_protocol)
    sequencing_protocol = Protocol(name='sequencing')
    study.protocols.append(sequencing_protocol)

    for i, sample in enumerate(study.samples):

        # create an extraction process that executes the extraction protocol

       extraction_process = Process(executes_protocol=extraction_protocol)

       # extraction process takes as input a sample, and produces an extract material as output

       extraction_process.inputs.append(sample)
       material = Material(name="extract-{}".format(i))
       material.type = "Extract Name"
       extraction_process.outputs.append(material)

       # create a sequencing process that executes the sequencing protocol

       sequencing_process = Process(executes_protocol=sequencing_protocol)
       sequencing_process.name = "assay-name-{}".format(i)
       sequencing_process.inputs.append(extraction_process.outputs[0])

       # Sequencing process usually has an output data file

       datafile = DataFile(filename="sequenced-data-{}".format(i), label="Raw Data File", generated_from=[sample])
       sequencing_process.outputs.append(datafile)

       # Ensure Processes are linked forward and backward. plink(from_process, to_process) is a function to set
       # these links for you. It is found in the isatools.model package

       plink(extraction_process, sequencing_process)

       # make sure the extract, data file, and the processes are attached to the assay

       assay.samples.append(sample)
       assay.data_files.append(datafile)
       assay.other_material.append(material)
       assay.process_sequence.append(extraction_process)
       assay.process_sequence.append(sequencing_process)
       assay.measurement_type = OntologyAnnotation(term="gene sequencing")
       assay.technology_type = OntologyAnnotation(term="nucleotide sequencing")

    # attach the assay to the study

    study.assays.append(assay)

    isatab.dump(investigation, "./")
    return time.process_time_ns() - starting_time

# These Rows were modelled after https://doi.ipk-gatersleben.de/DOI/1c0c2b3e-7478-48b1-81f6-47981f44a5cb/3e0482d7-4580-4d77-8b89-653f4171d0f6/2/1847940088
def measure_real_world(n_rows):
    starting_time = time.process_time_ns()
    investigation = Investigation()
    investigation.identifier = "i1"

    study = Study(filename="s_study.txt")
    study.identifier = "s1"
    investigation.studies.append(study)

    # Ontologies
    ontologies = {}
    ontologies["NCBITaxon"] = OntologySource(name = "NCBITaxon", file="http://purl.obolibrary.org/obo/ncbitaxon", description="National Center for Biotechnology Information (NCBI) Organismal Classification")
    ontologies["AGRO"] = OntologySource(name = "AGRO", file="http://purl.obolibrary.org/obo/agro/releases/2018-05-14/agro.owl", description="Agronomy Ontology", version="2018-05-14")
    ontologies["UO"] = OntologySource(name = "UO", file="http://data.bioontology.org/ontologies/UO", description="Units of Measurement Ontology", version="38802")
    investigation.ontology_source_references.extend(ontologies.values())

    # Factors
    fa_soil_cover = StudyFactor(name="Soil Cover")
    fa_plant_movement = StudyFactor(name="Plant Movement")
    study.factors.extend([fa_soil_cover, fa_plant_movement])

    fav_covered = FactorValue(factor_name = fa_soil_cover, value="covered")
    fav_uncovered = FactorValue(factor_name = fa_soil_cover, value="uncovered")
    fav_rotating = FactorValue(factor_name = fa_plant_movement, value="rotating")
    fav_stationary = FactorValue(factor_name = fa_plant_movement, value="stationary")

    # Protocols
    prot_phenotyping = Protocol(name="Phenotyping")
    prot_growth      = Protocol(name="Growth")
    prot_watering    = Protocol(name="Watering")
    study.protocols.append(prot_phenotyping)
    study.protocols.append(prot_growth)
    study.protocols.append(prot_watering)

    assay = Assay(filename="a_assay.txt")
    study.assays.append(assay)

    # Characteristics
    common_characteristics = [
        Characteristic(category=OntologyAnnotation(term="Organism"),value=OntologyAnnotation(term="Arabidopsis thaliana",term_source=ontologies["NCBITaxon"],term_accession="http://purl.obolibrary.org/obo/NCBITaxon_3702")),
        Characteristic(category=OntologyAnnotation(term="Genus"),value=OntologyAnnotation(term="Arabidopsis",term_source=ontologies["NCBITaxon"],term_accession="http://purl.obolibrary.org/obo/NCBITaxon_3701")),
        Characteristic(category=OntologyAnnotation(term="Species"),value=OntologyAnnotation(term="thaliana")),
        Characteristic(category=OntologyAnnotation(term="Infraspecific Name"),value=OntologyAnnotation(term=" ")),
        Characteristic(category=OntologyAnnotation(term="Biological Material Latitude"),value=OntologyAnnotation(term="51.827721")),
        Characteristic(category=OntologyAnnotation(term="Biological Material Longitude"),value=OntologyAnnotation(term="11.27778")),
        Characteristic(category=OntologyAnnotation(term="Material Source ID"),value=OntologyAnnotation(term="http://eurisco.ipk-gatersleben.de/apex/f?p=103:16:::NO::P16_EURISCO_ACC_ID:1668187")),
        Characteristic(category=OntologyAnnotation(term="Seed Origin"),value=OntologyAnnotation(term="http://arabidopsis.info/StockInfo?NASC_id=22680")),
        Characteristic(category=OntologyAnnotation(term="Growth Facility"),value=OntologyAnnotation(term="small LemnaTec phytochamber")),
        Characteristic(category=OntologyAnnotation(term="Material Source Latitude"),value=OntologyAnnotation(term="51.827721")),
        Characteristic(category=OntologyAnnotation(term="Material Source Longitude"),value=OntologyAnnotation(term="11.27778"))
    ]
    sample_characteristic = Characteristic(category=OntologyAnnotation(term="Observation Unit Type"),value=OntologyAnnotation(term="plant"))

    # Growth Parameters
    growth_parameters = {} # Name => [Value, Value REF, Value Accession, Unit, Unit REF, Unit Accession]
    with open("growth_parameters.csv") as gp:
        r = csv.DictReader(gp, delimiter=';')
        for row in r:
            growth_parameters[row["Parameter name"]] = list(row.values())[1:len(row)]
            prot_growth.parameters.append(ProtocolParameter(parameter_name=OntologyAnnotation(term=row["Parameter name"])))

    growth_parameter_values = []
    for param in prot_growth.parameters:
        field_values = growth_parameters[param.parameter_name.term]
        if field_values[3]:
            if field_values[4]:
                unit = OntologyAnnotation(term=field_values[3], term_accession=field_values[5], term_source = ontologies[field_values[4]])
            else:
                unit = OntologyAnnotation(term=field_values[3])
            # If there is a unit, the value should be a number
            value = float(field_values[0])
        else:
            unit = None
            if field_values[1]:
                value = OntologyAnnotation(term=field_values[0], term_accession=field_values[2], term_source = ontologies[field_values[1]])
            else:
                value = OntologyAnnotation(term=field_values[0])

        growth_parameter_values.append(ParameterValue(
            category=param,
            value = value,
            unit = unit
        ))

    # Write Study File
    for i in range(0, n_rows):
        source = Source(name='Plant_{}'.format(i))
        sample = Sample(name="1135FA-{}".format(i))
        study.samples.append(sample)
        study.sources.append(source)

        proc_growth = Process(executes_protocol=prot_growth)
        proc_growth.inputs.append(source)
        proc_growth.outputs.append(sample)
        study.process_sequence.append(proc_growth)

        source.characteristics.extend(common_characteristics)
        proc_growth.parameter_values.extend(growth_parameter_values)
        if i % 2 == 0:
            sample.factor_values.extend([fav_covered, fav_rotating])
        else:
            sample.factor_values.extend([fav_uncovered, fav_stationary])
        sample.characteristics.append(sample_characteristic)

    ## Read Phenotyping Parameters
    prot_phenotyping_parameters = {}
    with open("phenotyping_parameters.csv") as gp:
        r = csv.DictReader(gp, delimiter=';')
        for row in r:
            param = ProtocolParameter(parameter_name=OntologyAnnotation(term=row["Parameter name"]))
            prot_phenotyping_parameters[row["Parameter name"]] = param
            prot_phenotyping.parameters.append(param)

    prot_watering_parameters = {
            "Irrigation Type": ProtocolParameter(parameter_name=OntologyAnnotation(term="Irrigation Type")),
            "Volume": ProtocolParameter(parameter_name=OntologyAnnotation(term="Volume")),
    }
    prot_watering.parameters = prot_watering_parameters.values()


    datafile_comment = Comment(name="Image analysis tool", value="IAP")
    for i, sample in enumerate(study.samples):
       phenotyping_process = Process(executes_protocol=prot_phenotyping)
       phenotyping_process.inputs.append(sample)

       datafile = DataFile(filename="{}FA_images/fluo/side/54/1135FA1001 side.fluo das_54 DEG_000 2011-10-12 11_09_36.png".format(i), label="Raw Data File", generated_from=[sample])
       phenotyping_process.outputs.append(datafile)

       phenotyping_process.parameter_values.extend([
        ParameterValue(category=prot_phenotyping_parameters["Imaging Time"], value="28.09.2011 12:34:37"),
        ParameterValue(category=prot_phenotyping_parameters["Camera Configuration"], value="A_Fluo_Side_Big_Plant"),
        ParameterValue(category=prot_phenotyping_parameters["Camera Sensor"], value="FLUO"),
        ParameterValue(category=prot_phenotyping_parameters["Camera View"], value="side"),
        ParameterValue(category=prot_phenotyping_parameters["Imaging Angle"], value=90.0, unit=OntologyAnnotation(term="degree", term_source=ontologies["UO"], term_accession="http://purl.obolibrary.org/obo/UO_0000185")),
        ])

       watering_process = Process(executes_protocol=prot_watering)
       watering_process.inputs.append(datafile)
       datafile2 = DataFile(filename="derived_data_files/das_{}.txt".format(i), label="Derived Data File", generated_from=[datafile])
       datafile2.comments.append(datafile_comment)
       watering_process.outputs.append(datafile2)

       watering_process.parameter_values.extend([
        ParameterValue(category=prot_watering_parameters["Irrigation Type"], value="automated (LemnaTec target weight)"),
        ParameterValue(category=prot_watering_parameters["Volume"], value=80.4, unit=OntologyAnnotation(term="g", term_source=ontologies["UO"], term_accession="http://purl.obolibrary.org/obo/UO_0000021")),
       ])

       plink(phenotyping_process, watering_process)
       assay.samples.append(sample)
       assay.data_files.append(datafile)
       assay.data_files.append(datafile2)
       assay.process_sequence.append(phenotyping_process)
       assay.process_sequence.append(watering_process)

    isatab.dump(investigation, "./")
    return time.process_time_ns() - starting_time

with open('isatools_performance_test_results.csv', 'w') as csvfile:
    writer = csv.writer(csvfile, delimiter=',')
    writer.writerow(["platform", "row.complexity", "n.rows", "time.in.ns", "memory.usage.in.mb", "date.test.performed"])
    # Warm-Up
    measure_real_world(100)
    gc.collect()
    baseline_memory = memory_usage()[0]
    nrows = [1,3,5,10,25,50,100,250,500,1000,2500,5000,10000,25000]
    for i in nrows:
        for r in range(0, 5):
            writer.writerow(["isatools", "minimal", i, measure_minimal(i), -1, str(datetime.now())])
            gc.collect() # do this now so it doesn't interfere with the running time
    for i in nrows:
        for r in range(0, 5):
            writer.writerow(["isatools", "reduced", i, measure_reduced(i), -1, str(datetime.now())])
            gc.collect()
    for i in nrows:
        for r in range(0, 5):
            # Here we have to split it into two because we're measuring memory usage as well
            time_taken = measure_real_world(i)
            gc.collect()
            writer.writerow(["isatools", "real_world", i, time_taken, max(memory_usage((measure_real_world, (i,)), interval=1)) - baseline_memory, str(datetime.now())])
            gc.collect() # also collect GC after each memory measuring run

os.remove("i_investigation.txt")
os.remove("s_study.txt")
os.remove("a_assay.txt")
