/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;

/**
 * Class for validating the structure of the general ISA model
 * 
 * @author psaroudakis, arendd
 *
 */
public class GeneralValidation {

	/**
	 * Test if the given {@link Investigation} is associated with {@link Study} and
	 * {@link Assay}
	 * 
	 * @param investigation the {@link Investigation} to check
	 * @return <code>true</code> if the validation was successful
	 * 
	 */
	public static boolean validateInvestigationFile(Investigation investigation) {
		// Make sure the investigation has at least one study and every study has at
		// least one assay
		if (investigation.getStudies().isEmpty())
			throw new IllegalStateException(investigation.toString() + " has no studies attached to it.");
		for (Study study : investigation.getStudies())
			if (study.getAssays().isEmpty())
				throw new IllegalStateException(study.toString() + " has no assays attached to it.");
		return true;
	}

	/**
	 * Test if the given {@link Study} is linked to an {@link Investigation}
	 * 
	 * @param study the {@link Study} to check
	 * @return <code>true</code> if the validation was successful
	 */
	public static boolean validateStudyFile(Study study) {
		if (study.getInvestigation() == null)
			throw new IllegalStateException(study.toString() + " is not connected to an Investigation object.");
		return true;
	}

	/**
	 * Test if the given {@link Assay} is linked to a {@link Study}
	 * 
	 * @param assay the {@link Assay} to check
	 * @return <code>true</code> if the validation was successful
	 */
	public static boolean validateAssayFile(Assay assay) {
		if (assay.getStudy() == null)
			throw new IllegalStateException(assay.toString() + " is not connected to a Study object.");
		return true;
	}

}
