---
title: "Simple Working Example"
nav_order: 4
---

Here is a simple complete working example that you can just copy and paste into your favorite IDE and run.
You can play with it and use it as a template to extend to your use case.

```java

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
		// Set further attributes, add Contacts, Publications, Ontologies...
		
		Study myStudy = new Study("myStudyID", "s_study1.txt");
		myStudy.setTitle("A very important study");
		Protocol plantTalking = new Protocol("Plant Talking");
		myStudy.addProtocol(plantTalking);
		// Set further Study attributes, add Contacts, Publications...
		myInvestigation.addStudy(myStudy);
		
		Assay myAssay = new Assay("a_assay.txt");
		// Set further Assay attributes...
		myStudy.addAssay(myAssay);
		
		// Write the files
		try {
			// Investigation File
			myInvestigation.writeToFile("i_investigation.txt");
			
			// Study File
			myStudy.openFile();
			// Instead of looping through useless numbers, here you would loop through your database, CSV etc.
			for(int i = 0; i < 5; i++) {
				Source source = new Source("Source " + i);
				Sample sample = new Sample("Sample " + i);
				Process talkingProcess = new Process(plantTalking); // plantTalking is a Protocol defined above
				talkingProcess.setInput(source);
				talkingProcess.setOutput(sample);

				if(!myStudy.hasWrittenHeaders())
					myStudy.writeHeadersFromExample(source);
				myStudy.writeLine(source);
			}
			myStudy.closeFile();
			
			// Assay File
			myAssay.openFile();
			for(int i = 0; i < 5; i++) {
				Sample sample = new Sample("Sample " + i);
				DataFile sequenceFile = new DataFile("Raw Data File", "seq-"+ i + ".fasta");
				Process talkingProcess = new Process(plantTalking); // plantTalking is a Protocol defined above
				talkingProcess.setInput(sample);
				talkingProcess.setOutput(sequenceFile);

				if(!myAssay.hasWrittenHeaders())
					myAssay.writeHeadersFromExample(sample);
				myAssay.writeLine(sample);
			}
			myAssay.closeFile();
			
		} catch (IOException e) {
			System.err.println("Whoops, something went wrong!");
			e.printStackTrace();
		}
	}

}
```
