/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudyTest {
	
	Study study;
	
	@BeforeEach
	void resetStudy() {
		this.study = new Study("Study ID","s_study.txt");
	}
	
	@Test
	void testWriteLine() throws IOException {
		// Like in the investigation tests, compare our output to one generated by python isatools
    	BufferedReader correctFile = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("python_originals/s_study.txt")));
    	ByteArrayOutputStream os   = new ByteArrayOutputStream();
    	this.study.setOutputStream(os);
    	
    	Protocol protocol1 = new Protocol("foobar Protocol");
    	ProtocolParameter parameter1 = new ProtocolParameter("Irrigation Type");
    	ProtocolParameter parameter2 = new ProtocolParameter("Volume");
    	Protocol protocol2 = new Protocol("Protocol2");
    	Ontology ontology1 = new Ontology("foobar Ontology", null, null, null);
    	Factor fa_drought_stress = new Factor("drought stress");
    	for(int i = 1; i < 6; i++) {
    		Source source = new Source("Source no. " + i);
    		source.addCharacteristic(new Characteristic("Characteristic 1", new OntologyAnnotation("Characteristic1Value"+i)));
    		source.addCharacteristic(new Characteristic("Characteristic 2", new OntologyAnnotation("Characteristic2Value"+i,"Char2Acc",ontology1)));
    		source.comments().add(new Comment("A Comment", "CommVal" + i));
    		
    		Sample sample = new Sample("Sample no. " + i);
    		sample.addCharacteristic(new Characteristic("Characteristic 3", new OntologyAnnotation("Characteristic3Value"+i,"Char3Acc",ontology1)));
    		sample.addFactorValue(new FactorValue(fa_drought_stress, new OntologyAnnotation("nope", "nopeAcc", ontology1)));
    		sample.comments().add(new Comment("A Comment", "Comm2Val" + i));
    		sample.comments().add(new Comment("Another Comment", "Comm3Val" + i));
    		
    		Process process = new Process(protocol1);
    		process.addParameterValue(new ParameterValue(parameter1, new OntologyAnnotation("IT"+i)));
    		process.addParameterValue(new ParameterValue(parameter2, 12.4, new OntologyAnnotation("l")));
    		process.setDate(LocalDate.of(2020, Month.JANUARY, 16));
    		process.comments().add(new Comment("Process Comment", "Comm4Val" + i));
    		process.setInput(source);
    		process.setOutput(sample);
    		
    		Sample sample2 = new Sample("Target Sample");
    		sample2.addFactorValue(new FactorValue(fa_drought_stress, 34.12, new OntologyAnnotation("m")));
    		Process process2 = new Process(protocol2);
    		process2.setDateTime(LocalDateTime.of(2020, 1, 16, 13, 53, 23));
    		process2.setInput(sample);
    		process2.setOutput(sample2);
    		
    		study.writeLine(source);
    	}
    	
    	this.study.releaseStream();
    	
    	BufferedReader ourFile	   = new BufferedReader(new StringReader(os.toString()));
    	
    	String correctLine = null;
    	String ourLine	   = null;
    	while((correctLine = correctFile.readLine()) != null && (ourLine = ourFile.readLine()) != null) {
    		assertEquals(correctLine, ourLine);
    	}
    	// Assert that both files are finished here as well
    	assertNull(ourFile.readLine());
    	assertNull(correctFile.readLine());
	}

}
