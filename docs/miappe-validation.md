---
title: MIAPPE v1.1 Validation
nav_order: 5
---

# MIAPPE v1.1 Validation
isa4j is able to validate given investigation, study, or assay object against the Minimum Information About A Plant Phenotyping Standard ([https://www.miappe.org/](MIAPPE)).
You can also use the constants defined in `MIAPPEv1x1` to make sure you spell the field headers correctly and make use of your IDE's auto-completion feature.
Here is a small example showcasing the validation procedure:

```java
import java.io.IOException;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Characteristic;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Process;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Sample;
import de.ipk_gatersleben.bit.bi.isa4j.components.Source;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.configurations.MIAPPEv1x1;

public class SimpleMIAPPEExample {

	public static void main(String[] args) {
		Investigation myInvestigation = new Investigation("myInvestigationID");
		
		/* MIAPPE defines a set of required and optional pieces of information for the investigation file
		 * modeled as comments, you can add them by supplying the corresponding constant
		 */
		myInvestigation.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.INVESTIGATION_LICENSE, "MIT"));
		myInvestigation.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.MIAPPE_VERSION, "1.1"));
		
		/* Comments like these can also be added to study and assay objects */
		Study myStudy = new Study("myStudyID", "s_study1.txt");
		myStudy.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_START_DATE, "2020-07-23"));
		
		Protocol plantTalking = new Protocol("Plant Talking");
		myStudy.addProtocol(plantTalking);
		myInvestigation.addStudy(myStudy);
		
		Assay myAssay = new Assay("a_assay.txt");
		myStudy.addAssay(myAssay);
		
		/* When you have completed all the info in your configuration file, you can validate it against
		 * the MIAPPEv1.1 standard by calling the validate function in the InvestigationFile class.
		 * It will throw an exception here because not all required fields and protocols are required,
		 * if the validation is been successful, the function returns a boolean true.
		 */
		MIAPPEv1x1.InvestigationFile.validate(myInvestigation);
		
		try {
			myInvestigation.writeToFile("i_investigation.txt");
			
			/* Objects in Study and Assay files can be populated with defined Characteristics analogously */
			myStudy.openFile();
			Source source = new Source("OverallSource");
			source.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.GENUS, "Arabodipsis"));
			for(int i = 0; i < 5; i++) {
				
				Sample sample = new Sample("Sample " + i);
				sample.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.OBSERVATION_UNIT_TYPE, "pot"));
				/* plantTalking is a Protocol defined above */
				Process talkingProcess = new Process(plantTalking); 
				talkingProcess.setInput(source);
				talkingProcess.setOutput(sample);

				myStudy.writeLine(source);
				/* The study file can be validated as well, but there is a catch: You need to call the validate function AFTER
				 * at least one line has been written (that's when the headers are resolved) but BEFORE you close the file or 
				 * release the stream. So here we are doing it in the first iteration of your loop, alternatively you could do it
				 * after the loop (but before closing the file), then you would only run the risk of rendering the whole file and only
				 * finding out afterwards whether it is compliant with MIAPPEv1.1.
				 */
				if(i == 0)
					MIAPPEv1x1.StudyFile.validate(myStudy);
			}
			/* Here would be another option to validate the file  */
			myStudy.closeFile();
			
			/* The Assay file works completely analogously, just using the constants and methods in MIAPPEv1x1.AssayFile */
			
		} catch (IOException e) {
			System.err.println("Whoops, something went wrong!");
			e.printStackTrace();
		}
	}
}
```

The MIAPPEv1.1 configuration we are validating is modeled after the XML files at [https://github.com/MIAPPE/ISA-Tab-for-plant-phenotyping](https://github.com/MIAPPE/ISA-Tab-for-plant-phenotyping) at commit `0e1a29d` (October 2019).
We are planning to support other standards in the future (MIAME, MINSEQE, CIMR) and have therefore created a Ruby script which translates configuration XML files into a Java validation class like `MIAPPEv1x1`, automating most of the work and leaving only some manual curation tasks.
You can find it in the source code at [`src/main/resources/de/ipk_gatersleben/bit/bi/isa4j/configurations/config_from_xmls.rb`](https://github.com/IPK-BIT/isa4j/blob/master/src/main/resources/de/ipk_gatersleben/bit/bi/isa4j/configurations/config_from_xmls.rb) if you are curious, but at this point it is not an official (or very well documented) part of isa4j.
