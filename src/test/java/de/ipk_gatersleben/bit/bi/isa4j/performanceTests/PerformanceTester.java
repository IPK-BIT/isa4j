/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.performanceTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Characteristic;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.DataFile;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.FactorValue;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Material;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.ParameterValue;
import de.ipk_gatersleben.bit.bi.isa4j.components.Process;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.ProtocolParameter;
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
			
			assay.writeLine(sample);
		}
		assay.closeFile();
		
		// Also write Investigation File (because python does it, too)
		investigation.writeToFile("i_investigation.txt");
		
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
			
			assay.writeLine(sample);
		}
		assay.closeFile();
		
		// Also write Investigation File (because python does it, too)
		investigation.writeToFile("i_investigation.txt");
		
		return bean.getCurrentThreadCpuTime() - startingTime;
	}
	
	public static long measureRealWorld(ThreadMXBean bean, int nRows) throws IOException {
		long startingTime = bean.getCurrentThreadCpuTime();
		
		Investigation investigation = new Investigation("i1");
		Study study = new Study("s1", "s_study.txt");
		investigation.addStudy(study);
		
		HashMap<String, Ontology> ontologies = new HashMap<String, Ontology>();
		ontologies.put("NCBITaxon", new Ontology("NCBITaxon", new URL("http://purl.obolibrary.org/obo/ncbitaxon"), null, "National Center for Biotechnology Information (NCBI) Organismal Classification"));
		ontologies.put("AGRO", new Ontology("AGRO", new URL("http://purl.obolibrary.org/obo/agro/releases/2018-05-14/agro.owl"), "2018-05-14", "Agronomy Ontology"));
		ontologies.put("UO", new Ontology("UO", new URL("http://data.bioontology.org/ontologies/UO"), "38802", "Units of Measurement Ontology"));
		investigation.setOntologies(new ArrayList<>(ontologies.values()));
		
	    // Factors
		Factor faSoilCover = new Factor("Soil Cover");
		Factor faPlantMovement = new Factor("Plant Movement");
		study.setFactors(List.of(faSoilCover, faPlantMovement));

		FactorValue favCovered = new FactorValue(faSoilCover, "covered");
		FactorValue favUncovered = new FactorValue(faSoilCover, "uncovered");
		FactorValue favRotating = new FactorValue(faPlantMovement, "rotating");
		FactorValue favStationary = new FactorValue(faPlantMovement, "stationary");
		
	    // Protocols
	    Protocol phenotyping = new Protocol("Phenotyping");
	    Protocol growth      = new Protocol("Growth");
	    Protocol watering    = new Protocol("Watering");
	    study.addProtocol(phenotyping);
	    study.addProtocol(growth);
	    study.addProtocol(watering);

	    Assay assay = new Assay("a_assay.txt");
	    study.addAssay(assay);
	    
	    List<Characteristic> commonCharacteristics = List.of(    
		    new Characteristic("Species",new OntologyAnnotation("thaliana")),
	        new Characteristic("Infraspecific Name",new OntologyAnnotation(" ")),
	        new Characteristic("Biological Material Latitude",new OntologyAnnotation("51.827721")),
	        new Characteristic("Biological Material Longitude",new OntologyAnnotation("11.27778")),
	        new Characteristic("Material Source ID",new OntologyAnnotation("http://eurisco.ipk-gatersleben.de/apex/f?p=103:16:::NO::P16_EURISCO_ACC_ID:1668187")),
	        new Characteristic("Seed Origin",new OntologyAnnotation("http://arabidopsis.info/StockInfo?NASC_id=22680")),
	        new Characteristic("Growth Facility",new OntologyAnnotation("small LemnaTec phytochamber")),
	        new Characteristic("Material Source Latitude",new OntologyAnnotation("51.827721")),
	        new Characteristic("Material Source Longitude",new OntologyAnnotation("11.27778"))
        );
	    Characteristic sampleCharacteristic = new Characteristic("Observation Unit Type", new OntologyAnnotation("plant"));
	    
	    // Growth Parameters
	    HashMap<String, String[]> growthParameters = new HashMap<String, String[]>(); // Name => [Value, Value REF, Value Accession, Unit, Unit REF, Unit Accession]
	    BufferedReader csvReader = new BufferedReader(new InputStreamReader(PerformanceTester.class.getResourceAsStream("growth_parameters.csv")));
	    String row = csvReader.readLine(); // Skip the first line (headers)
	    while ((row = csvReader.readLine()) != null) {
	        String[] data = row.split(";", -1);
	        growthParameters.put(data[0], Arrays.copyOfRange(data, 1, data.length));
	        growth.addParameter(new ProtocolParameter(data[0]));
	    }
	    csvReader.close();
	    
	    ArrayList<ParameterValue> growthParameterValues = new ArrayList<ParameterValue>();
	    for(ProtocolParameter param : growth.getParameters()) {
	    	String[] fieldValues = growthParameters.get(param.getName().getTerm());
	    	OntologyAnnotation unit = null;
	    	OntologyAnnotation value = null;
	    	if(!fieldValues[3].isEmpty()) {
	    		if(!fieldValues[4].isEmpty()) {
	    			unit = new OntologyAnnotation(fieldValues[3], fieldValues[5], ontologies.get(fieldValues[4]));
	    		} else {
	    			unit = new OntologyAnnotation(fieldValues[3]);
	    		}
	    		value = new OntologyAnnotation(fieldValues[0]);
	    	} else {
	    		if(!fieldValues[1].isEmpty()) {
	    			value = new OntologyAnnotation(fieldValues[0], fieldValues[2], ontologies.get(fieldValues[1]));
	    		} else
	    			value = new OntologyAnnotation(fieldValues[0]);
	    	}
	    	growthParameterValues.add(new ParameterValue(param, value, unit));
	    }
		
		study.openFile();
		for(int i = 0; i < nRows; i++) {
			Source source = new Source("Plant_"+i);
			Sample sample = new Sample("1135FA-"+i);
			
			Process process = new Process(growth);
			process.setInput(source);
			process.setOutput(sample);
			
			source.setCharacteristics(commonCharacteristics);
			process.setParameterValues(growthParameterValues);
			sample.addCharacteristic(sampleCharacteristic);
			
	        if(i % 2 == 0)
	            sample.setFactorValues(List.of(favCovered, favRotating));
	        else
	        	sample.setFactorValues(List.of(favUncovered, favStationary));
			
			study.writeLine(source);
		}
		study.closeFile();
		
	    // Read Phenotyping Parameters
		HashMap<String, ProtocolParameter> phenotypingParameters = new HashMap<String, ProtocolParameter>();
	    csvReader = new BufferedReader(new InputStreamReader(PerformanceTester.class.getResourceAsStream("phenotyping_parameters.csv")));
	    row = csvReader.readLine(); // Skip the first line (headers)
	    while ((row = csvReader.readLine()) != null) {
	        String[] data = row.split(";", -1);
	        ProtocolParameter param = new ProtocolParameter(data[0]);
	        phenotypingParameters.put(data[0], param);
	        phenotyping.addParameter(param);
	    }
	    csvReader.close();
	    
	    HashMap<String, ProtocolParameter> wateringParameters = new HashMap<String, ProtocolParameter>();
	    wateringParameters.put("Irrigation Type", new ProtocolParameter("Irrigaiton Type"));
	    wateringParameters.put("Volume", new ProtocolParameter("Volume"));

	    Comment datafileComment = new Comment("Image analysis tool", "IAP");
		assay.openFile();
		for(int i = 0; i < nRows; i++) {
			Sample sample = new Sample("1135FA-"+i);
			
			Process procPhenotyping = new Process(phenotyping);
			procPhenotyping.setInput(sample);
			
			DataFile dataFile = new DataFile("Raw Data File", ""+i+"FA_images/fluo/side/54/1135FA1001 side.fluo das_54 DEG_000 2011-10-12 11_09_36.png");
			procPhenotyping.setOutput(dataFile);
			
			procPhenotyping.setParameterValues(List.of(
                new ParameterValue(phenotypingParameters.get("Imaging Time"), "28.09.2011 12:34:37"),
                new ParameterValue(phenotypingParameters.get("Camera Configuration"), "A_Fluo_Side_Big_Plant"),
                new ParameterValue(phenotypingParameters.get("Camera Sensor"),"FLUO"),
                new ParameterValue(phenotypingParameters.get("Camera View"), "side"),
                new ParameterValue(phenotypingParameters.get("Imaging Angle"), 90.0, new OntologyAnnotation("degree", "http://purl.obolibrary.org/obo/UO_0000185", ontologies.get("UO")))
			));
			
			Process procWatering = new Process(watering);
			procWatering.setInput(dataFile);
			DataFile dataFile2 = new DataFile("Derived Data File", "derived_data_files/das_"+i+".txt");
			dataFile2.comments().add(datafileComment);
			procPhenotyping.setOutput(dataFile2);
			
			procWatering.setParameterValues(List.of(
	    	    new ParameterValue(wateringParameters.get("Irrigation Type"), "automated (LemnaTec target weight)"),
	    	    new ParameterValue(wateringParameters.get("Volume"), 80.4, new OntologyAnnotation("g", "http://purl.obolibrary.org/obo/UO_0000021", ontologies.get("UO")))
			));
			
			assay.writeLine(sample);
		}
		assay.closeFile();
		
		// Also write Investigation File (because python does it, too)
		investigation.writeToFile("i_investigation.txt");
		
		return bean.getCurrentThreadCpuTime() - startingTime;
	}
	
	
	/**
	 * Measure currently used memory (heap + non-heap). Because heap memory also includes dead objects,
	 * attempt to run the Garbage Collector first.
	 * @param bean
	 * @return
	 */
	private static long getCurrentMemoryUsage(MemoryMXBean bean) {
		System.gc();
		return bean.getHeapMemoryUsage().getUsed();
	}
	
	/**
	 * Create the real world complexity example exactly like above but instead of measuring the time taken
	 * measure memory usage (heap + non-heap) at different points of the code. Also measures usage every
	 * x rows when writing, where x = memoryMeasureFrequency
	 * @param bean
	 * @param nRows
	 * @param memoryMeasureFrequency
	 * @return Maximum memory used at any measured point (heap + non-heap)
	 * @throws IOException
	 */
	public static long measureRealWorldMemory(MemoryMXBean bean, int nRows, int memoryMeasureFrequency) throws IOException {
		List<Long> memorySnapshots = new ArrayList<Long>();
		memorySnapshots.add(getCurrentMemoryUsage(bean));
		
		Investigation investigation = new Investigation("i1");
		Study study = new Study("s1", "s_study.txt");
		investigation.addStudy(study);
		
		HashMap<String, Ontology> ontologies = new HashMap<String, Ontology>();
		ontologies.put("NCBITaxon", new Ontology("NCBITaxon", new URL("http://purl.obolibrary.org/obo/ncbitaxon"), null, "National Center for Biotechnology Information (NCBI) Organismal Classification"));
		ontologies.put("AGRO", new Ontology("AGRO", new URL("http://purl.obolibrary.org/obo/agro/releases/2018-05-14/agro.owl"), "2018-05-14", "Agronomy Ontology"));
		ontologies.put("UO", new Ontology("UO", new URL("http://data.bioontology.org/ontologies/UO"), "38802", "Units of Measurement Ontology"));
		investigation.setOntologies(new ArrayList<>(ontologies.values()));
		
	    // Factors
		Factor faSoilCover = new Factor("Soil Cover");
		Factor faPlantMovement = new Factor("Plant Movement");
		study.setFactors(List.of(faSoilCover, faPlantMovement));

		FactorValue favCovered = new FactorValue(faSoilCover, "covered");
		FactorValue favUncovered = new FactorValue(faSoilCover, "uncovered");
		FactorValue favRotating = new FactorValue(faPlantMovement, "rotating");
		FactorValue favStationary = new FactorValue(faPlantMovement, "stationary");
		
	    // Protocols
	    Protocol phenotyping = new Protocol("Phenotyping");
	    Protocol growth      = new Protocol("Growth");
	    Protocol watering    = new Protocol("Watering");
	    study.addProtocol(phenotyping);
	    study.addProtocol(growth);
	    study.addProtocol(watering);

	    Assay assay = new Assay("a_assay.txt");
	    study.addAssay(assay);
	    
	    List<Characteristic> commonCharacteristics = List.of(    
		    new Characteristic("Species",new OntologyAnnotation("thaliana")),
	        new Characteristic("Infraspecific Name",new OntologyAnnotation(" ")),
	        new Characteristic("Biological Material Latitude",new OntologyAnnotation("51.827721")),
	        new Characteristic("Biological Material Longitude",new OntologyAnnotation("11.27778")),
	        new Characteristic("Material Source ID",new OntologyAnnotation("http://eurisco.ipk-gatersleben.de/apex/f?p=103:16:::NO::P16_EURISCO_ACC_ID:1668187")),
	        new Characteristic("Seed Origin",new OntologyAnnotation("http://arabidopsis.info/StockInfo?NASC_id=22680")),
	        new Characteristic("Growth Facility",new OntologyAnnotation("small LemnaTec phytochamber")),
	        new Characteristic("Material Source Latitude",new OntologyAnnotation("51.827721")),
	        new Characteristic("Material Source Longitude",new OntologyAnnotation("11.27778"))
        );
	    Characteristic sampleCharacteristic = new Characteristic("Observation Unit Type", new OntologyAnnotation("plant"));
	    
	    // Growth Parameters
	    HashMap<String, String[]> growthParameters = new HashMap<String, String[]>(); // Name => [Value, Value REF, Value Accession, Unit, Unit REF, Unit Accession]
	    BufferedReader csvReader = new BufferedReader(new InputStreamReader(PerformanceTester.class.getResourceAsStream("growth_parameters.csv")));
	    String row = csvReader.readLine(); // Skip the first line (headers)
	    while ((row = csvReader.readLine()) != null) {
	        String[] data = row.split(";", -1);
	        growthParameters.put(data[0], Arrays.copyOfRange(data, 1, data.length));
	        growth.addParameter(new ProtocolParameter(data[0]));
	    }
	    csvReader.close();
	    
	    ArrayList<ParameterValue> growthParameterValues = new ArrayList<ParameterValue>();
	    for(ProtocolParameter param : growth.getParameters()) {
	    	String[] fieldValues = growthParameters.get(param.getName().getTerm());
	    	OntologyAnnotation unit = null;
	    	OntologyAnnotation value = null;
	    	if(!fieldValues[3].isEmpty()) {
	    		if(!fieldValues[4].isEmpty()) {
	    			unit = new OntologyAnnotation(fieldValues[3], fieldValues[5], ontologies.get(fieldValues[4]));
	    		} else {
	    			unit = new OntologyAnnotation(fieldValues[3]);
	    		}
	    		value = new OntologyAnnotation(fieldValues[0]);
	    	} else {
	    		if(!fieldValues[1].isEmpty()) {
	    			value = new OntologyAnnotation(fieldValues[0], fieldValues[2], ontologies.get(fieldValues[1]));
	    		} else
	    			value = new OntologyAnnotation(fieldValues[0]);
	    	}
	    	growthParameterValues.add(new ParameterValue(param, value, unit));
	    }
	    
		memorySnapshots.add(getCurrentMemoryUsage(bean));
		
		study.openFile();
		for(int i = 0; i < nRows; i++) {
			Source source = new Source("Plant_"+i);
			Sample sample = new Sample("1135FA-"+i);
			
			Process process = new Process(growth);
			process.setInput(source);
			process.setOutput(sample);
			
			source.setCharacteristics(commonCharacteristics);
			process.setParameterValues(growthParameterValues);
			sample.addCharacteristic(sampleCharacteristic);
			
	        if(i % 2 == 0)
	            sample.setFactorValues(List.of(favCovered, favRotating));
	        else
	        	sample.setFactorValues(List.of(favUncovered, favStationary));
	        
	        // Measure Memory Usage every X rows
			if(i % memoryMeasureFrequency == 0)
				memorySnapshots.add(getCurrentMemoryUsage(bean));
			
			study.writeLine(source);
		}
		study.closeFile();
		
	    // Read Phenotyping Parameters
		HashMap<String, ProtocolParameter> phenotypingParameters = new HashMap<String, ProtocolParameter>();
	    csvReader = new BufferedReader(new InputStreamReader(PerformanceTester.class.getResourceAsStream("phenotyping_parameters.csv")));
	    row = csvReader.readLine(); // Skip the first line (headers)
	    while ((row = csvReader.readLine()) != null) {
	        String[] data = row.split(";", -1);
	        ProtocolParameter param = new ProtocolParameter(data[0]);
	        phenotypingParameters.put(data[0], param);
	        phenotyping.addParameter(param);
	    }
	    csvReader.close();
	    
	    HashMap<String, ProtocolParameter> wateringParameters = new HashMap<String, ProtocolParameter>();
	    wateringParameters.put("Irrigation Type", new ProtocolParameter("Irrigaiton Type"));
	    wateringParameters.put("Volume", new ProtocolParameter("Volume"));
	    
		memorySnapshots.add(getCurrentMemoryUsage(bean));

	    Comment datafileComment = new Comment("Image analysis tool", "IAP");
		assay.openFile();
		for(int i = 0; i < nRows; i++) {
			Sample sample = new Sample("1135FA-"+i);
			
			Process procPhenotyping = new Process(phenotyping);
			procPhenotyping.setInput(sample);
			
			DataFile dataFile = new DataFile("Raw Data File", ""+i+"FA_images/fluo/side/54/1135FA1001 side.fluo das_54 DEG_000 2011-10-12 11_09_36.png");
			procPhenotyping.setOutput(dataFile);
			
			procPhenotyping.setParameterValues(List.of(
                new ParameterValue(phenotypingParameters.get("Imaging Time"), "28.09.2011 12:34:37"),
                new ParameterValue(phenotypingParameters.get("Camera Configuration"), "A_Fluo_Side_Big_Plant"),
                new ParameterValue(phenotypingParameters.get("Camera Sensor"),"FLUO"),
                new ParameterValue(phenotypingParameters.get("Camera View"), "side"),
                new ParameterValue(phenotypingParameters.get("Imaging Angle"), 90.0, new OntologyAnnotation("degree", "http://purl.obolibrary.org/obo/UO_0000185", ontologies.get("UO")))
			));
			
			Process procWatering = new Process(watering);
			procWatering.setInput(dataFile);
			DataFile dataFile2 = new DataFile("Derived Data File", "derived_data_files/das_"+i+".txt");
			dataFile2.comments().add(datafileComment);
			procPhenotyping.setOutput(dataFile2);
			
			procWatering.setParameterValues(List.of(
	    	    new ParameterValue(wateringParameters.get("Irrigation Type"), "automated (LemnaTec target weight)"),
	    	    new ParameterValue(wateringParameters.get("Volume"), 80.4, new OntologyAnnotation("g", "http://purl.obolibrary.org/obo/UO_0000021", ontologies.get("UO")))
			));
			
			if(i % memoryMeasureFrequency == 0)
				memorySnapshots.add(getCurrentMemoryUsage(bean));
			
			assay.writeLine(sample);
		}
		assay.closeFile();
		
		// Also write Investigation File (because python does it, too)
		investigation.writeToFile("i_investigation.txt");
		
		memorySnapshots.add(getCurrentMemoryUsage(bean));
		return Collections.max(memorySnapshots);
	}
	
	public static void main(String[] args) throws IOException {
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
		
		int numberOfRuns= 15;
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("isa4J_performance_results.csv"));
		
		// Warm up (discarded)
		measureRealWorld(threadBean, 1000);
		System.gc();
		long memoryBaseline = memoryBean.getHeapMemoryUsage().getUsed();
		
		for(int nRows : List.of(1,3,5,10,25,50,100,250,500)) { //,1000,2500,5000,10000,25000)) {
			System.out.println("nRows = " + nRows);
			for(int x = 0; x < numberOfRuns; x++) {
				writer.write("isa4J,minimal,"+nRows+","+measureMinimal(threadBean, nRows)+",-1,"+LocalDateTime.now()+"\n");
				System.gc();
			}
			for(int x = 0; x < numberOfRuns; x++) {
				writer.write("isa4J,reduced,"+nRows+","+measureReduced(threadBean, nRows)+",-1,"+LocalDateTime.now()+"\n");
				System.gc();
			}
			for(int x = 0; x < numberOfRuns; x++) {
				long cpuTime = measureRealWorld(threadBean, nRows);
				int memoryMeasureFrequency = Math.max(10, nRows/1000);
				writer.write("isa4J,real_world,"+nRows+","+cpuTime+","+(measureRealWorldMemory(memoryBean, nRows, memoryMeasureFrequency)-memoryBaseline)/1024.0/1204.0+","+LocalDateTime.now()+"\n");
				System.gc();
			}
		}
		
		writer.close();
		
		// Delete the created files
		new File("i_investigation.txt").delete();
		new File("s_study.txt").delete();
		new File("a_assay.txt").delete();
	}

}
