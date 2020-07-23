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

isa4J is designed to work with study and assay files that are too large to fit in memory, so instead of populating a study object with all sources and samples and then writing everything in one go, you build and flush out the file iteratively.
First you tell isa4J to open the file for writing:

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

	// WRITE TO FILE HERE (see below)
}
```

The method to write a line to the Study File is `writeLine(initiator)`, `initiator` being the first object in the line that is then connected via a chain of processes and samples/materials etc. to the last (that's why you did `talkingProcess.setInput` and `talkingProcess.setOutput`).
In our case that initator would be `source`.
But if you fill in `study1.writeLine(source)` at the marked location above, you will be met with an error:

```
Exception in thread "main" java.lang.IllegalStateException: Headers were not written yet
```

The reason is that, as mentioned before, isa4J doesn't know anything about the structure of the rows before or after the current one.
So if, for example, a Source in a later line has a Characteristic attached to it, that needs to be accounted for by having a corresponding entry in the file header and keeping an empty column for all the Sources that do not have this Characteristic.
That is why you have to explicitly tell isa4J what headers you need by passing an examplary initiator (e.g. Source) connected to a Process/Sample/Material/Datafile chain *that contains every field any of your future rows will need*:

```java
study1.writeHeadersFromExample(source);
```

So you need to figure out what your Study File should look like before you start to write it.
If all lines are of homogenous structure, you can go the easy way and simply include it in the loop:

```java
for(int i = 0; i < 5; i++) {
	Source source = new Source("Source Name");
	Sample sample = new Sample("Sample Name");
	Process talkingProcess = new Process(plantTalking); // plantTalking is a Protocol defined above
	talkingProcess.setInput(source);
	talkingProcess.setOutput(sample);

	if(!study1.hasWrittenHeaders())
		study1.writeHeadersFromExample(source);
	study1.writeLine(source);
}
```

Alternatively you could create one dummy or mock object set before the loop and write the headers from that, but in many situations this will be the easiest way and worth the small performance cost of checking for headers at each iteration.

After you have written everything you want to write, you can close the file with

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


