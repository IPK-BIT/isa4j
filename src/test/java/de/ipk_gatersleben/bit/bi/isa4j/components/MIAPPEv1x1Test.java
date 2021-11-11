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

	/**
	 * Test the "is_required" fields in the MIAPPE 1.1 configuration of the
	 * investigation file. MIAPPE_VERSION is required, INVESTIGATION_LICENSE is not
	 * required
	 */
	@Test
	void testInvestigationForRequiedFields() {

		Comment commentLicense = new Comment(MIAPPEv1x1.InvestigationFile.INVESTIGATION_LICENSE, "CC-BY 4.0");
		Comment commentVersion = new Comment(MIAPPEv1x1.InvestigationFile.MIAPPE_VERSION, "1.1");

		Investigation investigationOne = new Investigation("Investigation Without_MIAPPE_Version");

		Investigation investigationTwo = new Investigation("Investigation Without_License");

		investigationOne.comments().add(commentLicense);

		Study study = new Study("myStudyID");
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_START_DATE, "01.01.2021"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_CONTACT_INSTITUTION, "IPK"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_COUNTRY, "Germany"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_EXPERIMENTAL_SITE_NAME, "IPK"));
		study.comments().add(
				new Comment(MIAPPEv1x1.InvestigationFile.DESCRIPTION_OF_GROWTH_FACILITY, "Plant cultivation hall"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.TRAIT_DEFINITION_FILE, "TDF File"));

		Assay assay = new Assay("myAssayID");

		study.addAssay(assay);

		investigationOne.addStudy(study);
		investigationTwo.addStudy(study);

		Assertions.assertThrows(MissingFieldException.class,
				() -> MIAPPEv1x1.InvestigationFile.validate(investigationOne),
				"It should not be allowed to create investigation without 'MIAPPE_VERSION");

		investigationTwo.comments().add(commentVersion);

		Assertions.assertTrue(MIAPPEv1x1.InvestigationFile.validate(investigationTwo));

	}

	/**
	 * Test for checking if the validation of the Investigation <-> Study <->
	 * association is working
	 */
	@Test
	void testInvestigationStudy() {

		Investigation investigation = new Investigation("Investigation");

		investigation.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.MIAPPE_VERSION, "1.1"));

		Study study = new Study("myStudy");
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_START_DATE, "01.01.2021"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_CONTACT_INSTITUTION, "IPK"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_COUNTRY, "Germany"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.STUDY_EXPERIMENTAL_SITE_NAME, "IPK"));
		study.comments().add(
				new Comment(MIAPPEv1x1.InvestigationFile.DESCRIPTION_OF_GROWTH_FACILITY, "Plant cultivation hall"));
		study.comments().add(new Comment(MIAPPEv1x1.InvestigationFile.TRAIT_DEFINITION_FILE, "TDF File"));

		Assay assay = new Assay("myAssay");

		Assertions.assertThrows(IllegalStateException.class, () -> MIAPPEv1x1.InvestigationFile.validate(investigation),
				"Should not be possible to validate Investigations without Studies");

		investigation.addStudy(study);

		Assertions.assertThrows(IllegalStateException.class, () -> MIAPPEv1x1.InvestigationFile.validate(investigation),
				"Should not be possible to validate Investigations with Studies that have no Assays");

		study.addAssay(assay);

		Assertions.assertTrue(MIAPPEv1x1.InvestigationFile.validate(investigation));

	}

	/**
	 * Test the "is_required" fields in the MIAPPE 1.1 configuration of the study
	 * file, e.g. 'Charateristics[Organism]' is required.
	 */
	@Test
	void testStudyForRequiredFields() throws IOException {

		// create simple Study //
		Study study = new Study("myStudy");

		// just write the study into memory without saving into a file //
		study.setOutputStream(new ByteArrayOutputStream());

		Protocol growing = new Protocol("Growing");

		for (int i = 0; i < 5; i++) {
			Source source = new Source("Plant " + i);
//			source.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.ORGANISM, "barley"));
			Sample sample = new Sample("Sample " + i);
			Process growthProcess = new Process(growing);
			growthProcess.setInput(source);
			growthProcess.setOutput(sample);

			study.writeLine(source);
		}

		Assertions.assertThrows(IllegalStateException.class, () -> MIAPPEv1x1.StudyFile.validate(study),
				"It should not be possible to write a study without Characteristics[Organism]");

		study.closeFile();

	}

	/**
	 * Test the "is_required" fields in the MIAPPE 1.1 configuration of the assay
	 * file -> no specific field is required.
	 */
	@Test
	void testAssayForRequiredFields() throws IOException {

		// create simple Assay //
		Assay assay = new Assay("myAssay");

		// just write the assay into memory without saving into a file //
		assay.setOutputStream(new ByteArrayOutputStream());

		Protocol growing = new Protocol("Growing");

		for (int i = 0; i < 5; i++) {
			Source source = new Source("Plant " + i);
			Sample sample = new Sample("Sample " + i);
			Process growthProcess = new Process(growing);
			growthProcess.setInput(source);
			growthProcess.setOutput(sample);

			assay.writeLine(source);
		}

		Assertions.assertTrue(MIAPPEv1x1.AssayFile.validate(assay));

		assay.closeFile();

	}

}
