package isa4J;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Characteristic;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.FactorValue;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Person;
import de.ipk_gatersleben.bit.bi.isa4j.components.Process;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.ProtocolComponent;
import de.ipk_gatersleben.bit.bi.isa4j.components.ProtocolParameter;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;
import de.ipk_gatersleben.bit.bi.isa4j.components.Sample;
import de.ipk_gatersleben.bit.bi.isa4j.components.Source;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;

public class Playground {

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException {
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
		
		
		Ontology ontology1 = new Ontology("UO", new URL("http://data.bioontology.org/ontologies/UO"), null, "Description of lala");
		Ontology ontology2 = new Ontology("CRediT", new URL("http://purl.org/credit/ontology#"), "1.40", "Description of CrEDIT");
		investigation.addOntology(ontology1);
		investigation.addOntology(ontology2);
		
		investigation.comments().add(new Comment("Investigation Type", "Value"));
		investigation.comments().add(new Comment("Another Comment", "Hello"));
		
		Person karlheinz = new Person("Schmidt", "Karl-Heinz", "khschmitt@wunderland.de", "Schmidt GmbH", "Schmidtstraße 1, 543423 Schmidttown");
		karlheinz.setFax("FAxi1223");
		Person ursel = new Person("Wurst", "Ursel", null, "Wurstwaren Ursel", null);
		OntologyAnnotation role1 = new OntologyAnnotation("Huz");
		role1.setTerm("Terminator");
		role1.setSourceREF(ontology2);
		role1.setTermAccession("ACredIT#1321");
		ursel.addRole(role1);
		ursel.comments().add(new Comment("Person REF", "urselRef"));
		ursel.comments().add(new Comment("Ursel says", "Bye bye World"));
		
		OntologyAnnotation role2 = new OntologyAnnotation("Hs");
		role2.setTerm("Secret Role");
		karlheinz.addRole(role2);
		karlheinz.addRole(role1);
		karlheinz.comments().add(new Comment("Person REF", "karlheinzREf"));
		karlheinz.comments().add(new Comment("Karlheinz says", "Hello World!"));
		
		Publication paper1 = new Publication("Wurst und Utilitarismus", "the authors");
		paper1.setDOI("doi.org/ursel.wurst12321");
		
		Publication paper2 = new Publication("Hello World", "more authors");
		paper2.setPubmedID("pubmedio.com");
		
		investigation.addPublication(paper1);
		investigation.addPublication(paper2);
		
		OntologyAnnotation paper1Status = new OntologyAnnotation("hey");
		paper1Status.setSourceREF(ontology1);
		paper1Status.setTerm("Published");
		paper1Status.setTermAccession("Term Accession alala");
		paper1.setStatus(paper1Status);
		
		investigation.addContact(ursel);
		investigation.addContact(karlheinz);
		
//		Study study1 = new Study("Study #1", "s_study1.txt");
//		Study study2 = new Study("Study #2", "s_study2.txt");
		
		study1.setTitle("A super great Study");
		study2.setDescription("This one is even greater");
		study1.comments().add(new Comment("Comment1", "Value"));
		study1.comments().add(new Comment("Comment 2", "value"));
		
		study2.addDesignDescriptor(new OntologyAnnotation("Simple"));
		study2.addDesignDescriptor(paper1Status);
		
		Protocol prot1 = new Protocol("Protocol1");
		prot1.setDescription("hi");
		prot1.setType(new OntologyAnnotation("Term 1", "Lululala", ontology2));
		
		ProtocolParameter param1 = new ProtocolParameter(new OntologyAnnotation("Param1", "ACESSS", ontology1));
		ProtocolParameter param2 = new ProtocolParameter(new OntologyAnnotation("Param2"));
		
		prot1.addParameter(param1);
		prot1.addParameter(param2);
		
		study1.addProtocol(prot1);
		
		study1.addContact(karlheinz);
		
		investigation.addStudy(study1);
		investigation.addStudy(study2);
		
		investigation.writeToFile("test.txt");
		
		// Study and Assay Files
		Characteristic char1 = new Characteristic("Organism", new OntologyAnnotation("Arabidopsis thaliana"));
		Characteristic char2 = new Characteristic("Plant Role", new OntologyAnnotation("Contributor", "Acess.123", ontology1));
		
		Source source1 = new Source("Source 1");
		
		Process process = new Process(prot1);
		process.setInput(source1);
		
		Sample sample1 = new Sample("Plant 1");
		sample1.addCharacteristic(char2);
		sample1.addCharacteristic(char1);
		
		Factor f1 = new Factor("FactorName");
		Factor f2 = new Factor("Another Factor");
		Factor f3 = new Factor("A simple Factor");
		sample1.addFactorValue(new FactorValue(f1, 43.75, new OntologyAnnotation("hi", "access", ontology1)));
		sample1.addFactorValue(new FactorValue(f2, new OntologyAnnotation("value", "valueAccess", ontology1)));
		sample1.addFactorValue(new FactorValue(f3, 43.3));
		
		process.setOutput(sample1);
		
		
		study1.openFile();
		study1.writeHeadersFromExample(source1);
		study1.writeLine(source1);
		study1.closeFile();
		
	}

}
