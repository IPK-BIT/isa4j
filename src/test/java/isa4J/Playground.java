/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package isa4J;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import de.ipk_gatersleben.bit.bi.isa4j.configurations.MIAPPEv1x1;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;

public class Playground {

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException {
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
		
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		System.out.println(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed());
		System.out.println(threadBean.getCurrentThreadCpuTime());
		
		// Create a new Investigation (Investigation identifier is required)
		Investigation investigation = new Investigation("InvestigationID");
		
		// All of the following are optional and just for demonstrative purposes.
		investigation.setTitle("Investigation Title");
		investigation.setDescription("Investigation Description");
		investigation.setSubmissionDate(LocalDate.of(2019, 12, 22));
		investigation.setPublicReleaseDate(LocalDate.of(2020, 1, 16));
		
		investigation.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.MIAPPE_VERSION, "1.1"));
		
		Study s1 = new Study("s1");
		Protocol plantTalking = new Protocol("Plant Talking");
		s1.addProtocol(plantTalking);
		investigation.addStudy(s1);
		
		/* Study File */
		s1.openFile();
		/* Here you would loop through your database, CSV, JSON etc. */
		for(int i = 0; i < 5; i++) {
			Source source = new Source("Source " + i);
			source.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.GENUS, new OntologyAnnotation("Arabidopsis thaliana")));
			Sample sample = new Sample("Sample " + i);
			/* plantTalking is a Protocol defined above */
			Process talkingProcess = new Process(plantTalking); 
			talkingProcess.setInput(source);
			talkingProcess.setOutput(sample);
			
			s1.writeLine(source);
		}
		MIAPPEv1x1.StudyFile.validate(s1);
		//MIAPPEv1x1.validateStudyHeaders(headers)
		
		MIAPPEv1x1.InvestigationFile.validate(investigation);
		
		//MIAPPEv1x1.validateInvestigation(investigation);	
	}

}
