package de.ipk_gatersleben.bit.bi.isa4j.components;

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
	void testInvestigationRequiedFields() {

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
	@Test
	void testStudy() {
		
		Study study = new Study("myStudy");
		
		Source studySource = new Source("Plant_1");
		studySource.addCharacteristic(new Characteristic(MIAPPEv1x1.StudyFile.ORGANISM, "barley"));

		Assertions.assertTrue(MIAPPEv1x1.StudyFile.validate(study));

		
	}
	
	
}
