## Welcome
isa4J is a comprehensive and scalable Java Library for the programmatic generation of experimental metadata descriptions using the ISATab container format.
Every experiment or rather investigation is described using the hierarchical ISA (Investigation, Study, Assay) structure (for details see: [ISA Model and Serialization Specifications](https://isa-specs.readthedocs.io/en/latest/isatab.html)).
We're assuming you're familiar with the ISATab framework in the remainder of the manual so if you're not, please [read up about it first](https://isa-specs.readthedocs.io/en/latest/).

## 1. License and Citation
The software provided as-is and made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html), granting you the freedom to run, use, study, share, and modify the software in any way you want as long as any derivative work is distributed under the same or equivalent terms ([details](https://en.wikipedia.org/wiki/GNU_General_Public_License#Terms_and_conditions)).
If you're referring to isa4J in a scientific publication, please cite our paper:

> Citation forthcoming

## 2. Installation
TODO

## 3. Usage

### 3.1 Creating the Investigation File
With only a few exceptions, isa4J classes and variables are named in keeping with the [ISA model schemas](https://github.com/ISA-tools/isa-api/tree/master/isatools/resources/schemas/isa_model_version_1_0_schemas/core) and all ISA classes are available in the `de.ipk_gatersleben.bit.bi.isa4j.components` package.

```java
// Create a new Investigation (Investigation identifier is required)
Investigation investigation = new Investigation("InvestigationID");

// All of the following are optional and just for demonstrative purposes.
investigation.setTitle("Investigation Title");
investigation.setDescription("Investigation Description");
investigation.setSubmissionDate(LocalDate.of(2019, 12, 22));
investigation.setPublicReleaseDate(LocalDate.of(2020, 1, 16));

// If you want to refer to Ontologies lateron (e.g. in the Study or Assay Files), define them here
// and add them to your investigation.
Ontology creditOntology = new Ontology("CRediT", new URL("http://purl.org/credit/ontology"), null, "CASRAI Contributor Roles Taxonomy (CRediT)");
Ontology unitOntology   = new Ontology("UO", new URL("http://data.bioontology.org/ontologies/UO"), "38802", "Units of Measurement Ontology"); // 38802 is the version
investigation.addOntology(creditOntology);
investigation.addOntology(unitOntology);
// Alternatively you can also set the Ontologies all at once:
investigation.setOntologies(new ArrayList<Ontology>(List.of(creditOntology, unitOntology)));

// If you have any publications to link, you can do so similarly to ontologies
Publication statsStories = new Publication("Five Things I wish my Mother had told me, about Statistics that is", "Philip M. Dixon");
statsStories.setDOI("https://doi.org/10.4148/2475-7772.1013");
statsStories.setStatus(new OntologyAnnotation("Published")); // if we had a fitting Ontology, we could also add it here along an Ontology term accession number
investigation.addPublication(statsStories);

// You can add Investigation Contacts in a likewise manner
Person schlomo = new Person("Schlomo", "Hootkins", "schlomoHootkins@miofsiwa.foo", "Ministry of Silly Walks", "4 Hanover House, 14 Hanover Square, London W1S 1HP");
Person agatha  = new Person("Agatha", "Stroganoff", null, "Stroganoff Essential Eels", null);
// Agatha doesn't have an email or a postal address, but she has a fax number
agatha.setFax("+49 3553N714L 33l2");
// Add them as investigation contacts
investigation.addContact(schlomo);
investigation.addContact(agatha);

// Many objects can take comments. They all implement the Commentable interface
schlomo.comments().add(new Comment("Smell", "Very bad"));
schlomo.comments().add(new Comment("Hair", "Fabolous"));
investigation.comments().add(new Comment("Usability", "None"));

// At some point you will want to create one or more Study objects and attach them to your investigation
Study study1 = new Study("Study1ID", "s_study1.txt");
Study study2 = new Study("Study2ID"); // now the filename will be automatically set to "s_Study2ID.txt"
investigation.addStudy(study1);
investigation.addStudy(study2);

// Like the Investigation, studies also can have a Title and Description, Contacts and Publications.
// you can populate them just like you did the investigation

// Don't forget to define Study Factors and Protocols
study1.addFactor(new Factor("soil coverage", new OntologyAnnotation("Factor Type", "Factor Type Acccession Number", unitOntology))); // unitOntology doesn't make sense here, just for demonstrative purposes.
Protocol plantTalking = new Protocol("Plant Talking");
ProtocolParameter toneOfVoice = new ProtocolParameter("Tone of Voice");
plantTalking.addComponent(new ProtocolComponent("Component Name", new OntologyAnnotation("Component Type")));
plantTalking.addParameter(toneOfVoice);

// Like you can studies to an investigation, you can add assays to a study
Assay assay1 = new Assay("a_assay.txt");
study1.addAssay(assay1);

// When you have added everything, you can simply write the investigation file to a location you specify:
investigation.writeToFile("./i_investigation.txt");
// Instead of writing to a file, you can also write to an open outputstream (e.g. if you're using isa4J in a REST server application)
OutputStream os = new ByteArrayOutputStream(); // of course you would already have a stream
investigation.writeToStream(os);
```

Not all features of isa4J are shown in the example above, some are left for you to explore ;-).

## 3.2 Writing Study and Assay files
