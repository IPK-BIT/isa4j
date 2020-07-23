/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package isa4J;

import java.io.IOException;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.DataFile;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Process;
import de.ipk_gatersleben.bit.bi.isa4j.components.Sample;
import de.ipk_gatersleben.bit.bi.isa4j.components.Source;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;

public class SimpleWorkingExample {

	public static void main(String[] args) {
		Investigation myInvestigation = new Investigation("myInvestigationID");
		myInvestigation.setTitle("A very important Investigation");
		/* Set further attributes, add Contacts, Publications, Ontologies...*/
		
		Study myStudy = new Study("myStudyID", "s_study1.txt");
		myStudy.setTitle("A very important study");
		Protocol plantTalking = new Protocol("Plant Talking");
		myStudy.addProtocol(plantTalking);
		/* Set further Study attributes, add Contacts, Publications... */
		myInvestigation.addStudy(myStudy);
		
		Assay myAssay = new Assay("a_assay.txt");
		/* Set further Assay attributes... */
		myStudy.addAssay(myAssay);
		
		/* Write the files */
		try {
			/* Investigation File */
			myInvestigation.writeToFile("i_investigation.txt");
			
			/* Study File */
			myStudy.openFile();
			/* Here you would loop through your database, CSV, JSON etc. */
			for(int i = 0; i < 5; i++) {
				Source source = new Source("Source " + i);
				Sample sample = new Sample("Sample " + i);
				/* plantTalking is a Protocol defined above */
				Process talkingProcess = new Process(plantTalking); 
				talkingProcess.setInput(source);
				talkingProcess.setOutput(sample);

				myStudy.writeLine(source);
			}
			myStudy.closeFile();
			
			/* Assay File */
			myAssay.openFile();
			for(int i = 0; i < 5; i++) {
				Sample sample = new Sample("Sample " + i);
				DataFile seqFile = new DataFile("Raw Data File", "seq-"+ i + ".fasta");
				/* plantTalking is a Protocol defined above */
				Process talkingProcess = new Process(plantTalking); 
				talkingProcess.setInput(sample);
				talkingProcess.setOutput(seqFile);

				myAssay.writeLine(sample);
			}
			myAssay.closeFile();
			
		} catch (IOException e) {
			System.err.println("Whoops, something went wrong!");
			e.printStackTrace();
		}
	}
}