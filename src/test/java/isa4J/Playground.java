package isa4J;

import java.io.IOException;
import java.net.URL;

import de.ipk_gatersleben.bit.bi.isa4j.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;

public class Playground {

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException {
		Investigation investigation = new Investigation("investigation_id");
		
		Ontology ontology1 = new Ontology("UO", new URL("http://data.bioontology.org/ontologies/UO"), null, "Description of lala");
		Ontology ontology2 = new Ontology("CRediT", new URL("http://purl.org/credit/ontology#"), "1.40", "Description of CrEDIT");
		investigation.addOntology(ontology1);
		investigation.addOntology(ontology2);
		
		investigation.writeToFile("test.txt");
	}

}
