package de.ipk_gatersleben.bit.bi.isa4j.performanceTests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.LocalDateTime;
import java.util.List;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Characteristic;
import de.ipk_gatersleben.bit.bi.isa4j.components.DataFile;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Material;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Process;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Sample;
import de.ipk_gatersleben.bit.bi.isa4j.components.Source;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;

public class PerformanceTester {
	
	public static long measureMinimal(ThreadMXBean bean, int nRows) throws IOException {
		long startingTime = bean.getCurrentThreadCpuTime();
		
		Investigation investigation = new Investigation("i1");
		Study study = new Study("s1", "s_study.txt");
		investigation.addStudy(study);
		
		Protocol protocol = new Protocol("sample collection");
		study.addProtocol(protocol);
		
		study.openFile();
		for(int i = 0; i < nRows; i++) {
			Source source = new Source("source_material-"+i);
			Sample sample = new Sample("sample_material-"+i);
			Process process = new Process(protocol);
			
			process.setInput(source);
			process.setOutput(sample);
			
			if(!study.hasWrittenHeaders())
				study.writeHeadersFromExample(source);
			study.writeLine(source);
		}
		study.closeFile();
		
		Assay assay = new Assay("a_assay.txt");
		Protocol sequencingProtocol = new Protocol("material sequencing");
		study.addAssay(assay);
		
		assay.openFile();
		for(int i = 0; i < nRows; i++) {
			Sample sample = new Sample("sample_material-"+i);
			DataFile dataFile = new DataFile("Raw Data File", "sequenced-data-"+i);
			Process process = new Process(sequencingProtocol);
			
			process.setInput(sample);
			process.setOutput(dataFile);
			
			if(!assay.hasWrittenHeaders())
				assay.writeHeadersFromExample(sample);
			assay.writeLine(sample);
		}
		assay.closeFile();
		
		return bean.getCurrentThreadCpuTime() - startingTime;
	}
	
	public static long measureReduced(ThreadMXBean bean, int nRows) throws IOException {
		long startingTime = bean.getCurrentThreadCpuTime();
		
		Investigation investigation = new Investigation("i1");
		Study study = new Study("s1", "s_study.txt");
		investigation.addStudy(study);
		
		Protocol protocol = new Protocol("sample collection");
		study.addProtocol(protocol);
		
	    Ontology ncbitaxon = new Ontology("NCBITaxon", null, null, "NCBI Taxonomy");
	    Characteristic organism = new Characteristic("Organism", new OntologyAnnotation("Homo Sapiens", "http://purl.bioontology.org/ontology/NCBITAXON/9606", ncbitaxon));
		
		study.openFile();
		for(int i = 0; i < nRows; i++) {
			Source source = new Source("source_material-"+i);
			Sample sample = new Sample("sample_material-"+i);
			sample.addCharacteristic(organism);
			Process process = new Process(protocol);
			
			process.setInput(source);
			process.setOutput(sample);
			
			if(!study.hasWrittenHeaders())
				study.writeHeadersFromExample(source);
			study.writeLine(source);
		}
		study.closeFile();
		
		Assay assay = new Assay("a_assay.txt");
		Protocol extractionProtocol = new Protocol("extraction");
		Protocol sequencingProtocol = new Protocol("sequencing");
		study.addAssay(assay);
		
		assay.openFile();
		for(int i = 0; i < nRows; i++) {
			Sample sample = new Sample("sample_material-"+i);
			Material extract = new Material("Extract Name", "extract-"+i);
			DataFile dataFile = new DataFile("Raw Data File", "sequenced-data-"+i);
			
			Process extraction = new Process(extractionProtocol);			
			extraction.setInput(sample);
			extraction.setOutput(extract);
			
			Process sequencing = new Process(sequencingProtocol);
			sequencing.setInput(extract);
			sequencing.setOutput(dataFile);
			
			if(!assay.hasWrittenHeaders())
				assay.writeHeadersFromExample(sample);
			assay.writeLine(sample);
		}
		assay.closeFile();
		
		return bean.getCurrentThreadCpuTime() - startingTime;
	}
	
	public static void main(String[] args) throws IOException {
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		
		// Warm up (discarded)
		measureReduced(threadBean, 1000);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("isa4J_performance_results.csv"));
		
		for(int nRows : List.of(1,3,5,10,25,50,100,250,500,1000,2500,5000,10000)) {
			for(int x = 0; x < 5; x++) {
				writer.write("isa4J,minimal,"+nRows+","+measureMinimal(threadBean, nRows)+",-1,"+LocalDateTime.now()+"\n");
			}
			for(int x = 0; x < 5; x++) {
				writer.write("isa4J,reduced,"+nRows+","+measureReduced(threadBean, nRows)+",-1,"+LocalDateTime.now()+"\n");
			}
		}
		
		writer.close();
	}

}
