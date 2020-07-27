---
title: Writing Study and Assay Files
nav_order: 2
parent: Getting Started
---

# Writing Study and Assay files

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

isa4j is designed to work with study and assay files that are too large to fit in memory, so instead of populating a study object with all sources and samples and then writing everything in one go, you build and flush out the file iteratively.
First you tell isa4j to open the file for writing:

```java
study1.openFile(); // study1 is defined above and should be attached to an Investigation
```

This will use the filename that you passed when you created the study or that you set with `.setFilename`, so no argument is allowed here (this is done to ensure the filename mentioned in the Investigation File matches the one you actually write to).

You then create Source and Sample Objects, connect them via a Process and flush them out into the file before creating the next set of objects (Of course instead of looping through meaningless numbers like in this example, you would loop through your database entries, CSV entries, or whatever things you have that you want to describe with ISA-Tab.):

```java
for(int i = 0; i < 5; i++) {
	Source source = new Source("Source Name");
	Sample sample = new Sample("Sample Name");
	Process talkingProcess = new Process(plantTalking); // plantTalking is a Protocol defined above
	talkingProcess.setInput(source);
	talkingProcess.setOutput(sample);

	study1.writeLine(source);
}
```

The method to write a line to the Study File is `writeLine(initiator)`, `initiator` being the first object in the line that is then connected via a chain of processes and samples/materials etc. to the last (that's why you did `talkingProcess.setInput` and `talkingProcess.setOutput`).
In our case that initator would be `source`.
It is very important that all lines in your loop have the same structure since headers will be based on the first line you write.
So if a Source in a later line has a Characteristic attached to it which the first one didn't have, there will be no corresponding header in the output and isa4j will complain.
Therefore make sure to always create and attach the same Comments, Characteristics etc. in every line.
If some Source simply doesn't have a certain Characteristic, still do create and attach it but simply give it an empty String or null value.
This is an inconvinience which we plan to improve on in future versions of isa4j, it just requires some more thinking and coding on our part.

After you have written everything you want to write, you can (and should) close the file with

```java
study1.closeFile();
```

## Attributes for Sources, Samples, Processes etc.
In the real world, Sources and Samples often have more than just a name.
You can attach (depending on the type of object) ParameterValues (for Processes), FactorValues (for Samples), and Characteristics (Sources, Samples, and Materials).
Many objects can also be annotated with Comments.
The way you do this is very similar to how you populated the Investigation File above:

```java
Source source = new Source("Source Name");
source.addCharacteristic(new Characteristic("Characteristic 1", new OntologyAnnotation("Characteristic1Value","Char1Acc",creditOntology))); // creditOntology defined above

Sample sample = new Sample("Sample Name");
sample.addFactorValue(new FactorValue(
	soilCoverage, // Factor devined above
	34.12, // Value (can also be a String or an OntologyAnnotation)
	new OntologyAnnotation("%") // Unit
));

Process process = new Process(plantTalking);
process.addParameterValue(
	new ParameterValue(toneOfVoice, "soft") // similar to FactorValue: can have double/string/OntologyAnnotation values and an optional unit
); 

// Comments work the same way as before
source.comments().add(new Comment("Comment Name", "Comment Value"));
```

Of course it makes sense to define Objects that you are going to use multiple times outside of your loop, so a slightly more realistic example could look like this:

```java
Characteristic species = new Characteristic("Organism", new OntologyAnnotation("Arabidopsis thaliana","http://purl.obolibrary.org/obo/NCBITaxon_3702",ncbiTaxonomy)); // ontology not defined here
ParameterValue softSpeaking = new ParameterValue(toneOfVoice, "Very softly");
study1.openFile();
for(int i = 0; i < 5; i++) {
	Source source = new Source("Source Name");
	source.addCharacteristic(species);
	Sample sample = new Sample("Sample Name");
	sample.addFactorValue(new FactorValue(soilCoverage, i*10, new OntologyAnnotation("%")));
	Process talkingProcess = new Process(plantTalking); // plantTalking is a Protocol defined above
	talkingProcess.addParameterValue(softSpeaking);
	talkingProcess.setInput(source);
	talkingProcess.setOutput(sample);

	if(!study1.hasWrittenHeaders())
		study1.writeHeadersFromExample(source);
	study1.writeLine(source);
}
study1.closeFile();
```

## Writing to Streams
Like the Investigation File, you can also write Study Files to a stream instead of a file.

```java
study1.setOutputStream(os); // os = some OutputStream, instead of study1.openFile()
// Write your lines here
study1.releaseStream(); // instead of study1.closeFile()
```

## What about Assays?
Assays work in the exact same way that Studies do, they also have `openFile, writeHeadersFromExample, writeLine, closeFile` as well as `setOutputStream` and `releaseStream` methods.
In addition to Sources and Samples, you may want to make use of the `Material` and `DataFile` classes when writing Assay Files (although you **can** also use them in the Study File).

```java
Material material = new Material("Extract Name", "Extract No. 232");
extractionProcess.setInput(sample);
extractionProcess.setOutput(material);

DataFile sequenceFile = new DataFile("Raw Data File", "seq-232.fasta");
sequencingProcess.setInput(material);
sequencingProcess.setOutput(sequenceFile);
```

As you can see here, lines can contain more than one Processes, so instead of the simple `Source->Process->Sample` chain we constructed above, it can also be more complex (like here: `Sample->Process->Material->Process->Datafile`).
There is no limit to the amount of things you can connect, just make sure you always set the correct in- and outputs for the connecting processes.

## Writing Concurrently
If you have multiple study or assay files and want to create them even faster, you can also parallelize the creation process for each of them into a separate thread or process.
Just make sure you're not writing to the same file from multiple threads/processes at the same time, that will create chaos.


