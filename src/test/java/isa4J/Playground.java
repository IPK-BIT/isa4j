package isa4J;

import java.io.IOException;
import java.net.URL;

import de.ipk_gatersleben.bit.bi.isa4j.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Contact;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyTerm;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;

public class Playground {

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException {
		Investigation investigation = new Investigation("investigation_id");
		
		Ontology ontology1 = new Ontology("UO", new URL("http://data.bioontology.org/ontologies/UO"), null, "Description of lala");
		Ontology ontology2 = new Ontology("CRediT", new URL("http://purl.org/credit/ontology#"), "1.40", "Description of CrEDIT");
		investigation.addOntology(ontology1);
		investigation.addOntology(ontology2);
		
		investigation.addComment(new Comment("Investigation Type", "Value"));
		investigation.addComment(new Comment("Another Comment", "Hello"));
		
		Contact karlheinz = new Contact("Schmidt", "Karl-Heinz", "khschmitt@wunderland.de", "Schmidt GmbH", "Schmidtstraße 1, 543423 Schmidttown");
		Contact ursel = new Contact("Wurst", "Ursel", null, "Wurstwaren Ursel", null);
		
		Publication paper1 = new Publication();
		paper1.addAuthor(karlheinz);
		paper1.addAuthor(ursel);
		paper1.setTitle("Wurst und Utilitarismus");
		paper1.setDOI("doi.org/ursel.wurst12321");
		
		Publication paper2 = new Publication();
		paper2.addAuthor(karlheinz);
		paper2.setTitle("Hello World");
		paper2.setPubmedID("pubmedio.com");
		
		investigation.addPublication(paper1);
		investigation.addPublication(paper2);
		
		OntologyTerm paper1Status = new OntologyTerm();
		paper1Status.setSourceREF(ontology1);
		paper1Status.setName("Published");
		paper1Status.setTermAccessionNumber("Term Accession alala");
		paper1.setStatusOntology(paper1Status);
		
		investigation.writeToFile("test.txt");
	}

}
