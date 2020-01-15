package de.ipk_gatersleben.bit.bi.isa4j;

import static de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil.mergeAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Person;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class InvestigationTest {
	
	Investigation investigation = new Investigation("Investigation ID");
	
	@BeforeEach
	void resetInvestigation() {
		this.investigation = new Investigation("Investigation ID");
	}
	
    @Test
    void testAddStudy() {
    	// In the beginning, the list should be empty
    	assertTrue(this.investigation.getStudies().isEmpty());
    	
    	// When adding a study, it should be in the list
    	Study study1 = new Study("study1");
    	this.investigation.addStudy(study1);
    	assertTrue(this.investigation.getStudies().contains(study1));
    	
    	// When adding another study, both should be in the list
    	Study study2 = new Study("study2");
    	this.investigation.addStudy(study2);
    	assertTrue(this.investigation.getStudies().contains(study1));
    	assertTrue(this.investigation.getStudies().contains(study2));
    	
    	// When trying to add the same study again, an error should be thrown
    	assertThrows(RedundantItemException.class, () -> this.investigation.addStudy(study1));
    	
    	// When trying to add a study with the same identifier or filename, an error should be thrown as well
    	Study study3 = new Study("study1");
    	assertThrows(RedundantItemException.class, () -> this.investigation.addStudy(study3));
    	Study study4 = new Study("study4", "s_study1.txt");
    	assertThrows(RedundantItemException.class, () -> this.investigation.addStudy(study4));
    }
    
    @Test
    void testSetStudies() {
    	// Previously saved studies should be erased by SetStudies
    	Study study1 = new Study("study1");
    	Study study2 = new Study("study2");
    	Study study3 = new Study("study1");
    	Study study4 = new Study("study4", "s_study1.txt");
    	
    	List<Study> newStudies = new ArrayList<Study>();
    	newStudies.add(study2);
    	
    	this.investigation.addStudy(study1);
    	this.investigation.setStudies(newStudies);
    	
    	assertFalse(this.investigation.getStudies().contains(study1));
    	assertEquals(1, this.investigation.getStudies().size());
    	
    	// An error should be thrown if the list contains studies with the same identifiers or filenames
    	newStudies.add(study1);
    	newStudies.add(study2); // duplicate
    	assertThrows(RedundantItemException.class, () -> this.investigation.setStudies(newStudies));
    	newStudies.clear();
    	newStudies.add(study1);
    	newStudies.add(study3);
    	assertThrows(RedundantItemException.class, () -> this.investigation.setStudies(newStudies));
    	newStudies.clear();
    	newStudies.add(study1);
    	newStudies.add(study4);
    	assertThrows(RedundantItemException.class, () -> this.investigation.setStudies(newStudies));
    }
    
    @Test
    void testLineFromList() throws MalformedURLException {
    	List<Ontology> ontologies = new ArrayList<Ontology>();
    	Ontology ontology1 = new Ontology("Ontology1", new URL("http://ontology1.com/"), null, "Description 1");
    	Ontology ontology2 = new Ontology("Ontology2", new URL("http://ontology2.com/"), "Version 2", "Description 2");
    	Ontology ontology3 = new Ontology("Ontology3", new URL("http://ontology3.com/"), null, "Description 3");
    	
    	// Should return correctly formatted line
    	ontologies.add(ontology1);
    	assertEquals("LineName" + Symbol.TAB + "Ontology1" + Symbol.ENTER, Investigation.lineFromList("LineName" + Symbol.TAB, ontologies, o -> o.getName())); // The Line Names already contain tabs.
    	
    	ontologies.add(ontology2);
    	assertEquals("LineName" + Symbol.TAB + "Ontology1" + Symbol.TAB + "Ontology2" + Symbol.ENTER, Investigation.lineFromList("LineName\t", ontologies, o -> o.getName()));
    	
    	// Should replace nulls by empty strings
    	ontologies.add(ontology3);
    	assertEquals("LineName" + Symbol.TAB + Symbol.EMPTY + Symbol.TAB + "Version 2" + Symbol.TAB + Symbol.EMPTY + Symbol.ENTER, Investigation.lineFromList("LineName\t", ontologies, o -> o.getVersion()));

    }

    @Test
    void testOntologyLinesFromList() {
    	List<Publication> publications = new ArrayList<Publication>();
    	Publication publication1 = new Publication();
    	Publication publication2 = new Publication();
    	Publication publication3 = new Publication();
    	Publication publication4 = new Publication();
    	publication1.setStatus(new OntologyAnnotation("Status1"));
    	publication2.setStatus(null);
    	publication3.setStatus(new OntologyAnnotation("Status3", "Accession 3", null));
    	publication4.setStatus(new OntologyAnnotation(null, null, new Ontology("Ontology1", null, null, null)));
    	publications.add(publication1);
    	publications.add(publication2);
    	publications.add(publication3);
    	publications.add(publication4);
    	
    	String result = Investigation.ontologyLinesFromList("PubStatus" + Symbol.TAB, publications, p -> p.getStatus());
    	
    	// Result should (always) have 3 lines
    	assertEquals(3, result.split(Symbol.ENTER.toString()).length);
    	// Result should have 3 * 4 tabs (1 LineName + 4 Publications columns over 3 lines)
    	assertEquals(12, result.split(Symbol.TAB.toString()).length - 1);
    	// Result should be containing the correct information
    	String lineName1 = "PubStatus" + Symbol.TAB;
    	String lineName2 = mergeAttributes("PubStatus"+Symbol.TAB,InvestigationAttribute.TERM_ACCESSION_NUMBER.toString());
    	String lineName3 = mergeAttributes("PubStatus"+Symbol.TAB,InvestigationAttribute.TERM_SOURCE_REF.toString());
    	assertEquals(
    			lineName1 + "Status1" + Symbol.TAB + Symbol.EMPTY + Symbol.TAB + "Status3" + Symbol.TAB + Symbol.EMPTY + Symbol.ENTER
    		+	lineName2 + Symbol.EMPTY + Symbol.TAB + Symbol.EMPTY + Symbol.TAB + "Accession 3" + Symbol.TAB + Symbol.EMPTY + Symbol.ENTER
    		+	lineName3 + Symbol.EMPTY + Symbol.TAB + Symbol.EMPTY + Symbol.TAB + Symbol.EMPTY + Symbol.TAB + "Ontology1" + Symbol.ENTER,
    			result);
    }
    
    @Test
    void testFormatSimpleComments() {
    	List<Comment> comments = new ArrayList<Comment>();
    	
    	// Return an empty String for an empty list
    	assertEquals("", Investigation.formatSimpleComments(comments));
    	
    	// Work with just one comment
    	comments.add(new Comment("Comment Type 1", "Comment Value 1"));
    	assertEquals(StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 1") + "Comment Value 1" + Symbol.ENTER, Investigation.formatSimpleComments(comments));
    	
    	// Work with multiple comments and print them in the order they are given (not sorted etc)
    	comments.add(new Comment("Comment Type 3", "Comment Value 3"));
    	comments.add(new Comment("Comment Type 2", "Comment Value 2"));
    	assertEquals(
    		StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 1") + "Comment Value 1" + Symbol.ENTER
    	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 3") + "Comment Value 3" + Symbol.ENTER
    	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 2") + "Comment Value 2" + Symbol.ENTER, 
    	  Investigation.formatSimpleComments(comments));

    }

    @Test
    void testFormatComments() {
    	Person person1 = new Person("LN", "FN", null, null, null);
	   	person1.comments().add(new Comment("Shared Comment", "value1"));
	   	person1.comments().add(new Comment("Unique Comment", "hello!"));
	   	
	   	Person person2 = new Person("LN2", "FN2", null, null, null);
	   	person2.comments().add(new Comment("Another Comment", "bye bye!"));
	   	person2.comments().add(new Comment("Shared Comment", "value2"));
	   	
	   	List<Person> people = new ArrayList<>(List.of(person1, person2));
	   	
	   	// Shared comments should be printed on the same line while unique comments should each have their own line,
	   	// independently of the order they are added to the person
	   	assertEquals(
	   		StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Shared Comment") + "value1" + Symbol.TAB + "value2" + Symbol.ENTER
	   	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Unique Comment") + "hello!" + Symbol.TAB + Symbol.EMPTY + Symbol.ENTER
	   	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Another Comment") + Symbol.EMPTY + Symbol.TAB + "bye bye!" + Symbol.ENTER,
	   	  Investigation.formatComments(people));   	
    }
    
    @Test
    void testWriteToFile() throws IOException {
    	// We have created an Investigation File with the python API (python code is in the resources folder) and we'll try to create
    	// the same file with isa4J. We're going to loop through each of the lines of the python original and compare each line with our own result.
    	// This will yield a more helpful assertionException if something doesn't match than if we just compare the whole file at once.
    	
    	
    	
    	BufferedReader correctFile = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("python_originals/i_investigation.txt")));
    	PipedOutputStream os       = new PipedOutputStream();
    	BufferedReader ourFile	   = new BufferedReader(new InputStreamReader(new PipedInputStream(os)));
    	this.investigation.writeToStream(os);
    	
    	
    	String correctLine = null;
    	String ourLine	   = null;
    	while((correctLine = correctFile.readLine()) != null && (ourLine = ourFile.readLine()) != null) {
    		assertEquals(correctLine, ourLine);
    	}
    	// Assert that our file is finished here as well
    	assertNull(ourFile.readLine());
    	
    }
}
