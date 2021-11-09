/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Person;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.ProtocolParameter;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class InvestigationTest {
	
	Investigation investigation;
	
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
    	assertEquals("LineName" + Symbol.TAB + "Ontology1" + Symbol.ENTER, Investigation.lineFromList("LineName", ontologies, o -> o.getName()));
    	
    	ontologies.add(ontology2);
    	assertEquals("LineName" + Symbol.TAB + "Ontology1" + Symbol.TAB + "Ontology2" + Symbol.ENTER, Investigation.lineFromList("LineName", ontologies, o -> o.getName()));
    	
    	// Should replace nulls by empty strings
    	ontologies.add(ontology3);
    	assertEquals("LineName" + Symbol.TAB + Symbol.EMPTY + Symbol.TAB + "Version 2" + Symbol.TAB + Symbol.EMPTY + Symbol.ENTER, Investigation.lineFromList("LineName", ontologies, o -> o.getVersion()));

    	// Should only return linename and enter for an empty list, no TABs
    	assertEquals("LineName" + Symbol.ENTER, Investigation.lineFromList("LineName", new ArrayList<Ontology>(), o -> o.getName()));
    }

    @Test
    void testOntologyLinesFromList() {
    	List<Publication> publications = new ArrayList<Publication>();
    	Publication publication1 = new Publication(Symbol.EMPTY.toString(), null);
    	Publication publication2 = new Publication(Symbol.EMPTY.toString(), null);
    	Publication publication3 = new Publication(Symbol.EMPTY.toString(), null);
    	Publication publication4 = new Publication(Symbol.EMPTY.toString(), null);
    	publication1.setStatus(new OntologyAnnotation("Status1"));
    	publication2.setStatus(null);
    	publication3.setStatus(new OntologyAnnotation("Status3", "Accession 3", null));
    	publication4.setStatus(new OntologyAnnotation(null, null, new Ontology("Ontology1", null, null, null)));
    	publications.add(publication1);
    	publications.add(publication2);
    	publications.add(publication3);
    	publications.add(publication4);
    	
    	String result = Investigation.ontologyLinesFromList("PubStatus", publications, p -> p.getStatus());
    	
    	// Result should (always) have 3 lines
    	assertEquals(3, result.split(Symbol.ENTER.toString()).length);
    	// Result should have 3 * 4 tabs (1 LineName + 4 Publications columns over 3 lines)
    	assertEquals(12, result.split(Symbol.TAB.toString()).length - 1);
    	// Result should be containing the correct information
    	String lineName1 = "PubStatus" + Symbol.TAB;
    	String lineName2 = "PubStatus" + Symbol.SPACE + InvestigationAttribute.TERM_ACCESSION_NUMBER + Symbol.TAB;
    	String lineName3 = "PubStatus" + Symbol.SPACE + InvestigationAttribute.TERM_SOURCE_REF+ Symbol.TAB;
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
    	assertEquals(StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 1") + Symbol.TAB + "Comment Value 1" + Symbol.ENTER, Investigation.formatSimpleComments(comments));
    	
    	// Work with multiple comments and print them in sorted order)
    	comments.add(new Comment("Comment Type 3", "Comment Value 3"));
    	comments.add(new Comment("Comment Type 2", "Comment Value 2"));
    	assertEquals(
    		StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 1") + Symbol.TAB + "Comment Value 1" + Symbol.ENTER
    	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 2") + Symbol.TAB + "Comment Value 2" + Symbol.ENTER
    	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Comment Type 3") + Symbol.TAB + "Comment Value 3" + Symbol.ENTER, 
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
	   		StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Shared Comment") + Symbol.TAB + "value1" + Symbol.TAB + "value2" + Symbol.ENTER
	   	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Unique Comment") + Symbol.TAB + "hello!" + Symbol.TAB + Symbol.EMPTY + Symbol.ENTER
	   	  + StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, "Another Comment") + Symbol.TAB + Symbol.EMPTY + Symbol.TAB + "bye bye!" + Symbol.ENTER,
	   	  Investigation.formatComments(people));   	
    }
    
    @Test
    void testWriteToStream() throws IOException {
    	// We have created an Investigation File with the python API (python code is in the resources folder) and we'll try to create
    	// the same file with isa4J. We're going to loop through each of the lines of the python original and compare each line with our own result.
    	// This will yield a more helpful assertionException if something doesn't match than if we just compare the whole file at once.
    	
    	this.investigation.setTitle("Drought Stress Response in Arabidopsis thaliana");
    	this.investigation.setDescription("An experiment about drought stress in Arabidopsis thaliana");
    	this.investigation.setSubmissionDate(LocalDate.of(2019, Month.JANUARY, 16));
    	
    	// Comments
    	this.investigation.comments().add(new Comment("Owning Organisation URI", "http://www.ipk-gatersleben.de/"));
    	this.investigation.comments().add(new Comment("Investigation Keywords", "plant phenotyping, image analysis, arabidopsis thaliana, lemnatec"));
    	this.investigation.comments().add(new Comment("License", "CC BY 4.0 (Creative Commons Attribution) - https://creativecommons.org/licenses/by/4.0/legalcode"));
    	this.investigation.comments().add(new Comment("MIAPPE version", "1.1"));
    	
    	// Ontologies
    	Ontology creditOntology = new Ontology("CRediT",new URL("http://purl.org/credit/ontology#"),null,"CASRAI Contributor Roles Taxonomy (CRediT)");
    	Ontology agroOntology   = new Ontology("AGRO",new URL("http://purl.obolibrary.org/obo/agro/releases/2018-05-14/agro.owl"),"2018-05-14","Agronomy Ontology");
    	Ontology uoOntology     = new Ontology("UO",new URL("http://data.bioontology.org/ontologies/UO"),"38802","Units of Measurement Ontology");
    	this.investigation.addOntology(creditOntology);
    	this.investigation.addOntology(agroOntology);
    	this.investigation.addOntology(uoOntology);
    	
    	// Study
    	Study study = new Study("1745AJ", "s_study.txt");
    	study.setTitle("Drought Stress Response in Arabidopsis thaliana");
    	this.investigation.addStudy(study);
    	
    	// Factors
    	Factor factor = new Factor("drought stress");
    	factor.comments().add(new Comment("Study Factor Description", "Which plants were subjected to drought stress and which ones were not?"));
    	factor.comments().add(new Comment("Study Factor Values", "drought;well watered"));
    	study.addFactor(factor);
    	
    	// Comments
    	study.comments().add(new Comment("Study Start Date", ""));
    	study.comments().add(new Comment("Study Country", "Germany"));
    	study.comments().add(new Comment("Study Experimental Site", "LemnaTec Facility"));
    	study.comments().add(new Comment("Study Longitude", "11.27778"));
    	
    	// Design
    	OntologyAnnotation studyDesign = new OntologyAnnotation("Study Design");
    	studyDesign.comments().add(new Comment("Observation Unit Level Hierarchy", "side>lane>block>pot"));
    	studyDesign.comments().add(new Comment("Experimental Unit Level Hierarchy", "plant"));
    	study.addDesignDescriptor(studyDesign);
    	
    	// Assay
    	study.addAssay(new Assay("a_assay.txt"));
    	
    	// Contacts (Study and Investigation)
    	Person astrid = new Person("Junker", "Astrid", "junkera@ipk-gatersleben.de","Leibniz Institute of Plant Genetics and Crop Plant Research (IPK) Gatersleben","Corrensstrasse 3, 06466 Stadt Seeland, OT Gatersleben, Germany");
    	astrid.addRole(new OntologyAnnotation("project administration role","http://purl.org/credit/ontology#CREDIT_00000007",creditOntology));
    	astrid.comments().add(new Comment("Person ID", "https://orcid.org/0000-0002-4656-0308"));
    	Person dennis = new Person("Psaroudakis", "Dennis", "psaroudakis@ipk-gatersleben.de","Leibniz Institute of Plant Genetics and Crop Plant Research (IPK) Gatersleben","Corrensstrasse 3, 06466 Stadt Seeland, OT Gatersleben, Germany");
    	dennis.addRole(new OntologyAnnotation("data curation role","http://purl.org/credit/ontology#CREDIT_00000002",creditOntology));
    	dennis.comments().add(new Comment("Person ID", "https://orcid.org/0000-0002-7521-798X"));
    	investigation.addContact(astrid);
    	investigation.addContact(dennis);
    	study.addContact(astrid);
    	study.addContact(dennis);
    	
    	// Protocols
    	Protocol prot_phenotyping = new Protocol("Phenotyping");
    	Protocol prot_watering    = new Protocol("Watering");
    	study.addProtocol(prot_phenotyping);
    	study.addProtocol(prot_watering);
    	prot_watering.addParameter(new ProtocolParameter(new OntologyAnnotation("Irrigation Type")));
    	prot_watering.addParameter(new ProtocolParameter(new OntologyAnnotation("Volume")));
    	prot_watering.addParameter(new ProtocolParameter(new OntologyAnnotation("Watering Time")));
    	
    	// Publication
    	Publication pub = new Publication("A title", "Psaroudakis, D");
    	pub.setDOI("PUB DOI");
    	pub.setStatus(new OntologyAnnotation("fictional","access123",creditOntology));
    	this.investigation.addPublication(pub);
    		
    	BufferedReader correctFile = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("python_originals/i_investigation.txt")));
    	ByteArrayOutputStream os   = new ByteArrayOutputStream();
    	this.investigation.writeToStream(os);
    	BufferedReader ourFile	   = new BufferedReader(new StringReader(os.toString()));
    	
    	
    	String correctLine = null;
    	String ourLine	   = null;
    	while((correctLine = correctFile.readLine()) != null && (ourLine = ourFile.readLine()) != null) {
    		assertEquals(correctLine, ourLine);
    	}
    	// Assert that both files are finished here as well
    	assertNull(ourFile.readLine());
    	assertNull(correctFile.readLine());
    	
    	correctFile.close();
    	ourFile.close();
    	
    }
}