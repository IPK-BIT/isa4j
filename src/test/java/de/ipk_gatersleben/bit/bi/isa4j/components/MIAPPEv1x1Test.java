/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.ipk_gatersleben.bit.bi.isa4j.configurations.MIAPPEv1x1;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.MissingFieldException;

/**
 * Collection of simple unit test to check the implementation of the
 * Investigation file for the MIAPPE 1.1 configuration
 * 
 * @author psaroudakis, arendd
 *
 */

public class MIAPPEv1x1Test {
	
	private Investigation validInvestigation;
	private Study validStudy;
	private Assay validAssay;
	
	@BeforeEach
	void prepareObjects () {
		this.validInvestigation = new Investigation("Valid Investigation");
		validInvestigation.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.MIAPPE_VERSION, "1.1"));
		
		this.validStudy = new Study("Valid Study");
		this.validStudy.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_START_DATE, "01.01.2021"));
		this.validStudy.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_CONTACT_INSTITUTION, "IPK"));
		this.validStudy.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_COUNTRY, "Germany"));
		this.validStudy.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_EXPERIMENTAL_SITE_NAME, "IPK"));
		this.validStudy.comments().add(
				new Comment(MIAPPEv1x1.InvestigationFile.DESCRIPTION_OF_GROWTH_FACILITY, "Plant cultivation hall"));
		this.validStudy.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.TRAIT_DEFINITION_FILE, "TDF File"));
		
		this.validAssay = new Assay("Valid Assay");
	}
	
	/**
	 * This test is just to make sure the MIAPPEv1x1 validation internally also calls the general validation procudure
	 * (which checks whether there are at least one study/assay connected)
	 */
	@Test
	void testGeneralConfigurationValidation() {
		Assertions.assertThrows(IllegalStateException.class,
				() -> MIAPPEv1x1.InvestigationFile.validate(this.validInvestigation),
				"It should not be allowed to create investigation without connected studies. " +
				"Perhaps the general validation procedure is not being called?");
	}
	
	/**
	 * Test the "is_required" fields in the MIAPPE 1.1 configuration of the
	 * investigation file, just of the Investigation object.
	 */
	@Test
	void testInvestigationFileFaultyInvestigation() {
		Investigation faultyInvestigation = new Investigation("Faulty Investigation");
		faultyInvestigation.addStudy(this.validStudy);
		this.validStudy.addAssay(this.validAssay);
		
		Assertions.assertThrows(MissingFieldException.class,
				() -> MIAPPEv1x1.InvestigationFile.validate(faultyInvestigation),
				"It should not be allowed to validate investigation without 'MIAPPE_VERSION");
	}
	
	/**
	 * Test the required fields in the MIAPPE 1.1 configuration of the
	 * investigation file, just of the Study object.
	 */
	@Test
	void testInvestigationFileFaultyStudy() {
		Study faultyStudy = new Study("Faulty Study");
		this.validInvestigation.addStudy(faultyStudy);
		// Case 1: Study has not assays attached to it
		Assertions.assertThrows(IllegalStateException.class,
				() -> MIAPPEv1x1.InvestigationFile.validate(this.validInvestigation),
				"It should not be allowed to validate investigation without valid Study");
		
		// Case 2: Study is missing required fields
		faultyStudy.addAssay(this.validAssay);
		Assertions.assertThrows(MissingFieldException.class,
				() -> MIAPPEv1x1.InvestigationFile.validate(this.validInvestigation),
				"It should not be allowed to validate investigation without valid Study");
		
	}
	
	/**
	 * Test that a valid investigation file is returns true.
	 */
	@Test
	void testInvestigationFileValid() {
		this.validInvestigation.addStudy(this.validStudy);
		this.validStudy.addAssay(this.validAssay);
		
		Assertions.assertTrue(MIAPPEv1x1.InvestigationFile.validate(this.validInvestigation));
	}

	/**
	 * Test the required fields and structures in the MIAPPE 1.1 configuration of the study
	 * file, e.g. 'Charateristics[Organism]' is required.
	 */
	@Test
	void testStudyFile() throws IOException {

		Study study = this.validStudy;
		this.validInvestigation.addStudy(study);
		Protocol growing = new Protocol("Growth");
		Protocol phenotyping = new Protocol("Phenotyping");
		
		// Case 1: Missing Organism and Observation Unit Type characteristics
		// just write the study into memory without saving into a file //
		study.setOutputStream(new ByteArrayOutputStream());

		for (int i = 0; i < 5; i++) {
			Source source = new Source("Plant " + i);
			Sample sample = new Sample("Sample " + i);
			Process growthProcess = new Process(growing);
			growthProcess.setInput(source);
			growthProcess.setOutput(sample);

			study.writeLine(source);
		}
		Assertions.assertThrows(IllegalStateException.class, () -> MIAPPEv1x1.StudyFile.validate(study),
				"It should not be possible to write a study without Characteristics[Organism]");

		study.closeFile();
		
		
		// Case 2: Missing Growth or Phenotyping Protocol
		study.setOutputStream(new ByteArrayOutputStream());

		for (int i = 0; i < 5; i++) {
			Source source = new Source("Plant " + i);
			source.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.ORGANISM, "barley"));
			Sample sample = new Sample("Sample " + i);
			sample.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.OBSERVATION_UNIT_TYPE, "plant"));
			Process growthProcess = new Process(growing);
			growthProcess.setInput(source);
			growthProcess.setOutput(sample);

			study.writeLine(source);
		}

		Assertions.assertThrows(IllegalStateException.class, () -> MIAPPEv1x1.StudyFile.validate(study),
				"It should not be possible to write a study without Growth or Phenotyping Protocols");

		study.closeFile();
		
		study.addProtocol(growing);
		study.addProtocol(phenotyping);
		
		// Case 3: Valid Study but validation called without headers being written
		Assertions.assertThrows(IllegalStateException.class, () -> MIAPPEv1x1.StudyFile.validate(study),
				"It should not be possible to validate Study without headers");
		
		// Case 4: Valid Study
		study.setOutputStream(new ByteArrayOutputStream());

		for (int i = 0; i < 5; i++) {
			Source source = new Source("Plant " + i);
			source.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.ORGANISM, "barley"));
			Sample sample = new Sample("Sample " + i);
			sample.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.OBSERVATION_UNIT_TYPE, "plant"));
			Process growthProcess = new Process(growing);
			growthProcess.setInput(source);
			growthProcess.setOutput(sample);

			study.writeLine(source);
		}

		Assertions.assertTrue(MIAPPEv1x1.StudyFile.validate(study));

		study.closeFile();

	}

}
