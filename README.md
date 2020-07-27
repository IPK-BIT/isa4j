[![Maven Central](https://img.shields.io/maven-central/v/de.ipk-gatersleben/isa4j.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22de.ipk-gatersleben%22%20AND%20a:%22isa4j%22)

## Welcome
isa4j is a comprehensive and scalable Java Library for the programmatic generation of experimental metadata descriptions using the ISATab container format.
We're assuming you're familiar with the ISA-Tab framework in the remainder of the manual; if you're not, please [read up about it first](https://isa-specs.readthedocs.io/en/latest/).

## License and Citation
The software provided as-is and made available under the terms of the MIT license (https://spdx.org/licenses/MIT.html), granting you the freedom to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software as long as you include the license and copyright notice in all copies or substantial portions of isa4j ([details](https://en.wikipedia.org/wiki/MIT_License)).
<!-- If you're referring to isa4j in a scientific publication, we'd be grateful if you could cite our paper: -->

<!-- > Citation forthcoming -->

## Usage

### Installation

#### Gradle

```
repositories {
	mavenCentral()
}    
dependencies {
	implementation group: 'de.ipk-gatersleben', name:'isa4J', version:'###REPLACE-WITH-VERSION-NUMBER###'
}
```

#### Maven

```
<dependency>
  <groupId>de.ipk-gatersleben</groupId>
  <artifactId>isa4J</artifactId>
  <version>###REPLACE-WITH-VERSION-NUMBER###</version>
</dependency>
```

If you don't use Gradle or Maven, please see the [Installation page](https://ipk-bit.github.io/isa4j/installation.html) in the documentation for further instructions.

### Simple Working Example

Here is a simple complete working example that you can just copy and paste into your favorite IDE and run.
You can play with it and use it as a template to extend to your use case.
More info on how to use isa4j can be found on the [documentation website](https://ipk-bit.github.io/isa4j/).

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
```


## Documentation
Please see our [GitHub Page](https://ipk-bit.github.io/isa4j) for detailed documentation as well as an evaluation of isa4j's scalability.
