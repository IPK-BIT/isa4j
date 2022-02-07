/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.ipk_gatersleben.bit.bi.isa4j.configurations.GeneralValidation;

/**
 * Collection of simple unit test to check the implementation of the general
 * validation of the ISA model
 * 
 * @author psaroudakis, arendd
 *
 */

public class GeneralValidationTest {

	/**
	 * Test for checking if the validation of the Investigation <-> Study <->
	 * association is working
	 */
	@Test
	void testValidateInvestigation() {

		Investigation investigation = new Investigation("Investigation");

		Study study = new Study("study");


		Assay assay = new Assay("assay");

		Assertions.assertThrows(IllegalStateException.class, () -> GeneralValidation.validateInvestigationFile(investigation),
				"Should not be possible to validate Investigations without Studies");

		investigation.addStudy(study);

		Assertions.assertThrows(IllegalStateException.class, () -> GeneralValidation.validateInvestigationFile(investigation),
				"Should not be possible to validate Investigations with Studies that have no Assays");

		study.addAssay(assay);

		Assertions.assertTrue(GeneralValidation.validateInvestigationFile(investigation));

	}
	
	@Test
	void testValidateStudy() {
		Study study = new Study("study");
		
		Assertions.assertThrows(IllegalStateException.class, () -> GeneralValidation.validateStudyFile(study),
				"Should not be possible to validate Study without attached Investigation");
		
		Investigation investigation = new Investigation("Investigation");
		investigation.addStudy(study);
		
		Assertions.assertTrue(GeneralValidation.validateStudyFile(study));
	}
	
	@Test
	void testValidateAssay() {
		Assay assay = new Assay("assay");
		
		Assertions.assertThrows(IllegalStateException.class, () -> GeneralValidation.validateAssayFile(assay),
				"Should not be possible to validate Assay without attached Study");
		
		Study study = new Study("study");
		study.addAssay(assay);
		
		Assertions.assertTrue(GeneralValidation.validateAssayFile(assay));
	}

}
