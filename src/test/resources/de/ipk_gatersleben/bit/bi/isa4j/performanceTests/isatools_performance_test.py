import time
from memory_profiler import memory_usage
from isatools.model import *
from isatools import isatab

def measure_simple(n_rows):
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


def measure_medium(n_rows):
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

    # To build out assay graphs, we enumereate the samples from the study-level, and for each sample we create an
    # extraction process and a sequencing process. The extraction process takes as input a sample material, and produces
    # an extract material. The sequencing process takes the extract material and produces a data file. This will
    # produce three graphs, from sample material through to data, as follows:
    #
    # (sample_material-0)->(extraction)->(extract-0)->(sequencing)->(sequenced-data-0)
    # (sample_material-1)->(extraction)->(extract-1)->(sequencing)->(sequenced-data-1)
    # (sample_material-2)->(extraction)->(extract-2)->(sequencing)->(sequenced-data-2)
    #
    # Note that the extraction processes and sequencing processes are distinctly separate instances, where the three
    # graphs are NOT interconnected.

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

import csv
from datetime import datetime
with open('performance_test_results.csv', 'w') as csvfile:
    writer = csv.writer(csvfile, delimiter=',')
    writer.writerow(["platform", "row.complexity", "n.rows", "time.in.ns", "memory.usage.in.mb", "date.test.performed"])
    for i in [1, 10, 100, 500, 1000, 5000, 10000]:
        for r in range(0, 5):
            writer.writerow(["isatools", "simple", i, measure_simple(i), max(memory_usage((measure_simple, (i,)), interval=1)), str(datetime.now())])
    for i in [1, 10, 100, 500, 1000, 5000, 10000]:
        for r in range(0, 5):
            writer.writerow(["isatools", "medium", i, measure_medium(i), max(memory_usage((measure_medium, (i,)), interval=1)), str(datetime.now())])

import os
os.remove("i_investigation.txt")
os.remove("s_study.txt")
os.remove("a_assay.txt")
