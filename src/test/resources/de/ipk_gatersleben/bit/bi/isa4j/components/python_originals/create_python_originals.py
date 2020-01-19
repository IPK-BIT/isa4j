# This was executed with isatools installed from GitHub (https://github.com/ISA-tools/isa-api)
# in version ee60fd53e61e0ecfa92f3a0a0954f6fd3032f832
from isatools.model import *
from isatools import isatab
import shutil

investigation = Investigation(identifier="Investigation ID")
investigation.title = "Drought Stress Response in Arabidopsis thaliana"
investigation.description = "An experiment about drought stress in Arabidopsis thaliana"
investigation.submission_date = "2019-01-16"

## Comments
investigation.comments.extend([
    Comment(name="Owning Organisation URI", value="http://www.ipk-gatersleben.de/"),
    Comment(name="Investigation Keywords", value="plant phenotyping, image analysis, arabidopsis thaliana, lemnatec"),
    Comment(name="License", value="CC BY 4.0 (Creative Commons Attribution) - https://creativecommons.org/licenses/by/4.0/legalcode"),
    Comment(name="MIAPPE version", value="1.1"),
])

# Ontologies
ontologies = {}
ontologies["CRediT"] = OntologySource(name = "CRediT", file="http://purl.org/credit/ontology#", description="CASRAI Contributor Roles Taxonomy (CRediT)")
ontologies["AGRO"] = OntologySource(name = "AGRO", file="http://purl.obolibrary.org/obo/agro/releases/2018-05-14/agro.owl", description="Agronomy Ontology", version="2018-05-14")
ontologies["UO"] = OntologySource(name = "UO", file="http://data.bioontology.org/ontologies/UO", description="Units of Measurement Ontology", version="38802")
investigation.ontology_source_references.extend(ontologies.values())

# Study
study = Study(filename="s_study.txt")
study.identifier = "1745AJ"
study.title = "Drought Stress Response in Arabidopsis thaliana"
investigation.studies.append(study)

## Factors
fa_drought_stress = StudyFactor(name="drought stress", comments=[
    Comment(name="Study Factor Description", value="Which plants were subjected to drought stress and which ones were not?"),
    Comment(name="Study Factor Values", value="drought;well watered")
])
study.factors.append(fa_drought_stress)

## Comments
study.comments.extend([
    Comment(name="Study Start Date", value=""),
    Comment(name="Study Country", value="Germany"),
    Comment(name="Study Experimental Site", value="LemnaTec Facility"),
    Comment(name="Study Longitude", value="11.27778")
])

## Design
study_design = OntologyAnnotation(term="Study Design")
study_design.comments.extend([
    Comment(name="Observation Unit Level Hierarchy", value="side>lane>block>pot"),
    Comment(name="Experimental Unit Level Hierarchy", value="plant"),
])
study.design_descriptors.append(study_design)

## Assay
assay = Assay(filename="a_assay.txt")
study.assays.append(assay)

## Contacts
contacts = [
    ### Astrid
    Person(
      last_name = "Junker",
      first_name = "Astrid",
      email = "junkera@ipk-gatersleben.de",
      address = "Corrensstrasse 3, 06466 Stadt Seeland, OT Gatersleben, Germany",
      affiliation = "Leibniz Institute of Plant Genetics and Crop Plant Research (IPK) Gatersleben",
      roles = [
        OntologyAnnotation(
          term = "project administration role",
          term_source = ontologies["CRediT"],
          term_accession = "http://purl.org/credit/ontology#CREDIT_00000007"
        ),
      ],
      comments = [
        Comment(
          name = "Person ID",
          value = "https://orcid.org/0000-0002-4656-0308"
        )
      ]
    ),
    ### Dennis
    Person(
      last_name = "Psaroudakis",
      first_name = "Dennis",
      email = "psaroudakis@ipk-gatersleben.de",
      address = "Corrensstrasse 3, 06466 Stadt Seeland, OT Gatersleben, Germany",
      affiliation = "Leibniz Institute of Plant Genetics and Crop Plant Research (IPK) Gatersleben",
      roles = [
        OntologyAnnotation(
          term = "data curation role",
          term_source = ontologies["CRediT"],
          term_accession = "http://purl.org/credit/ontology#CREDIT_00000002"
        ),
      ],
      comments = [
        Comment(
          name = "Person ID",
          value = "https://orcid.org/0000-0002-7521-798X"
        )
      ]
    )
]
investigation.contacts.extend(contacts)
study.contacts.extend(contacts)

## Protocols
prot_phenotyping = Protocol(name="Phenotyping")
prot_watering    = Protocol(name="Watering")
study.protocols.append(prot_phenotyping)
study.protocols.append(prot_watering)
parameter1 = ProtocolParameter(parameter_name=OntologyAnnotation(term="Irrigation Type"))
parameter2 = ProtocolParameter(parameter_name=OntologyAnnotation(term="Volume"))
parameter3 = ProtocolParameter(parameter_name=OntologyAnnotation(term="Watering Time"))
prot_watering.parameters.extend([parameter1, parameter2, parameter3])

## Publication
pub = Publication(doi="PUB DOI",title="A title",author_list="Psaroudakis, D",status=OntologyAnnotation(term="fictional",term_accession="access123",term_source=ontologies["CRediT"]))
investigation.publications.append(pub)

## ------- Study File -----------
protocol1 = Protocol(name="foobar Protocol")
protocol2 = Protocol(name="Protocol2")
ontology1 = OntologySource(name="foobar Ontology")
for i in range(1,6):
    source = Source("Source no. " + str(i))
    source.characteristics.append(Characteristic(
        category=OntologyAnnotation(term="Characteristic 1"),
        value=OntologyAnnotation(term="Characteristic1Value"+str(i))))
    source.characteristics.append(Characteristic(
        category=OntologyAnnotation(term="Characteristic 2"),
        value=OntologyAnnotation(term="Characteristic2Value"+str(i),term_source=ontology1,term_accession="Char2Acc")))
    source.comments.append(Comment(name="A Comment", value="CommVal" + str(i)))

    sample = Sample("Sample no. " + str(i))
    sample.characteristics.append(Characteristic(
        category=OntologyAnnotation(term="Characteristic 3"),
        value=OntologyAnnotation(term="Characteristic3Value"+str(i),term_source=ontology1,term_accession="Char3Acc")))
    sample.factor_values.append(FactorValue(factor_name=fa_drought_stress, value=OntologyAnnotation(term="nope",term_source=ontology1,term_accession="nopeAcc")))
    sample.comments.append(Comment(name="A Comment", value="Comm2Val" + str(i)))
    sample.comments.append(Comment(name="Another Comment", value="Comm3Val" + str(i)))

    process = Process(executes_protocol=protocol1)
    process.parameter_values.append(ParameterValue(category=parameter1,value=OntologyAnnotation(term="IT"+str(i))))
    process.parameter_values.append(ParameterValue(category=parameter2,value=12.4,unit=OntologyAnnotation(term="l")))
    process.date = "2020-01-16"
    process.comments.append(Comment(name="Process Comment", value="Comm4Val" + str(i)))
    process.inputs.append(source)
    process.outputs.append(sample)

    sample2 = Sample("Target Sample")
    sample2.factor_values.append(FactorValue(factor_name=fa_drought_stress, value=34.12, unit=OntologyAnnotation(term="m")))
    process2 = Process(executes_protocol=protocol2)
    process2.date = "2020-01-16T13:53:23"
    process2.inputs.append(sample)
    process2.outputs.append(sample2)

    study.sources.append(source)
    study.samples.append(sample)
    study.samples.append(sample2)
    study.process_sequence.append(process)
    study.process_sequence.append(process2)

## ------- Assay File -----------
for i in range(1,6):
    sample = Sample("Sample no. " + str(i))


    # The following code is taken from https://isatools.readthedocs.io/en/latest/example-createSimpleISAtab.html
    # create an extraction process that executes the extraction protocol

    extraction_process = Process(executes_protocol=protocol1)

    # extraction process takes as input a sample, and produces an extract material as output

    extraction_process.inputs.append(sample)
    material = Material(name="extract-{}".format(i))
    material.type = "Extract Name"
    extraction_process.outputs.append(material)

    # create a sequencing process that executes the sequencing protocol

    sequencing_process = Process(executes_protocol=protocol2)
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
#    assay.measurement_type = OntologyAnnotation(term="gene sequencing")
#    assay.technology_type = OntologyAnnotation(term="nucleotide sequencing")

isatab.dump(investigation, ".")
shutil.copyfile("i_investigation.txt", "../../isa4J/src/test/resources/de/ipk_gatersleben/bit/bi/isa4j/components/python_originals/i_investigation.txt")
shutil.copyfile("s_study.txt", "../../isa4J/src/test/resources/de/ipk_gatersleben/bit/bi/isa4j/components/python_originals/s_study.txt")
shutil.copyfile("a_assay.txt", "../../isa4J/src/test/resources/de/ipk_gatersleben/bit/bi/isa4j/components/python_originals/a_assay.txt")
