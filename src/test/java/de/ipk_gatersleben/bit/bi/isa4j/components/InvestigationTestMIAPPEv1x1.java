package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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

public class InvestigationTestMIAPPEv1x1 {

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

		try {
			MIAPPEv1x1.InvestigationFile.validate(investigationOne);
			Assertions.assertFalse(true);
		} catch (MissingFieldException exp) {
			Assertions.assertTrue(true);
		}

		investigationTwo.comments().add(commentVersion);

		Assertions.assertTrue(MIAPPEv1x1.InvestigationFile.validate(investigationTwo));

	}

	/**
	 * Test Investigation <-> Study association
	 */
	@Test
	void testInvestigationStudy() {

//		Investigation investigation = new Investigation("Investigation Without_MIAPPE_Version");
//		Comment commentVersion = new Comment(MIAPPEv1x1.InvestigationFile.MIAPPE_VERSION, "1.1");
//		
//		investigation.comments().add(commentVersion);
//
//		Assertions.assertFalse(MIAPPEv1x1.InvestigationFile.validate(investigation));

	}

	/**
	 * Test the "is_required" fields in the MIAPPE 1.1 configuration of the study
	 * file, e.g. 'Charateristics[Organism]' is required.
	 */
	@Test
	void testStudyForRequiredFields() throws IOException {

		// create simple Study //
		Study study = new Study("myStudyID");

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
		try {
			MIAPPEv1x1.StudyFile.validate(study);
			Assertions.assertFalse(true);
		} catch (MissingFieldException exp) {
			Assertions.assertTrue(true);
		}
		study.closeFile();

	}

	/**
	 * Test the "is_required" fields in the MIAPPE 1.1 configuration of the assay
	 * file -> no specific field is required.
	 */
	@Test
	void testAssayForRequiredFields() throws IOException {

		// create simple Assay //
		Assay assay = new Assay("myAssayID");

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
