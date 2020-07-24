/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;

public class WideTableFileTest {
	
	Study study;
	
	@BeforeEach
	void resetStudy() {
		this.study = new Study("Study ID","s_study.txt");
	}
	
	@Test
	void testDirectToStream() throws IOException {
		OutputStream os = new PipedOutputStream(new PipedInputStream());
		OutputStream os2 = new ByteArrayOutputStream();
		// Complain when trying to write to a stream while another is still opened
		study.setOutputStream(os);
		assertThrows(IllegalStateException.class, () -> study.setOutputStream(os2));
		// Make it work fine when the first is released
		study.releaseStream();
		study.setOutputStream(os2);
		// Make sure the first stream is still writable (i.e. it wasn't closed)
		String output = "Hello";
		os.write(output.getBytes());
	}
	
	@Test
	void testWriteHeadersFromExample() throws IOException {
		Source source = new Source("Source Name");
		source.addCharacteristic(new Characteristic("Beauty", new OntologyAnnotation("Very Beautiful")));
		source.addCharacteristic(new Characteristic("Size", new OntologyAnnotation("m", "accession", new Ontology("Ontology", null, null, null))));
		
		Sample sample = new Sample("Sample Name");
		sample.addCharacteristic(new Characteristic("Beauty", new OntologyAnnotation("Very Beautiful")));
		
		Process process = new Process(new Protocol("ProtocolName"));
		process.setInput(source);
		process.setOutput(sample);
		
		// Complain if there's no stream to write to
		assertThrows(IllegalStateException.class, () -> study.writeHeadersFromExample(source));
		
		OutputStream os = new ByteArrayOutputStream();
		study.setOutputStream(os);
		study.writeHeadersFromExample(source);
		study.releaseStream();
		
		assertEquals(
			"Source Name" + Symbol.TAB + "Characteristics[Beauty]" + Symbol.TAB + "Characteristics[Size]" + Symbol.TAB + "Term Source REF" + Symbol.TAB + "Term Accession Number"
		  + Symbol.TAB + "Protocol REF" 
		  + Symbol.TAB + "Sample Name" + Symbol.TAB + "Characteristics[Beauty]"
		  + Symbol.ENTER,
		  os.toString()
		);
	}
	
	@Test
	void testWriteLine() throws IOException {
		Source source1 = new Source("Source Name");
		Characteristic characteristic1 = new Characteristic("Characteristic", new OntologyAnnotation("Value"));
		source1.addCharacteristic(characteristic1);
		Sample sample1 = new Sample("Sample Name");
		Process process1 = new Process(new Protocol("Watering"));
		process1.setInput(source1);
		process1.setOutput(sample1);
		
		// Complain if there's no stream to write to
		assertThrows(IllegalStateException.class, () -> study.writeLine(source1));
		
		ByteArrayOutputStream os1   = new ByteArrayOutputStream();
    	this.study.setOutputStream(os1);
    	
    	this.study.writeHeadersFromExample(source1);
    	
    	// Complain if elements are missing that are present in the header
    	process1.setOutput(null);
    	assertThrows(NullPointerException.class, () -> this.study.writeLine(source1));
    	
    	// Complain if a group suddenly has more columns than defined in the header (the Characteristic now gets a Term Accession Number for which there is no header)
    	characteristic1.setValue(new OntologyAnnotation("Value","Term Accession",new Ontology("Ontology", null, null, null)));
    	assertThrows(IllegalStateException.class, () -> this.study.writeLine(source1));
    	
    	this.study.releaseStream();
	}

}
