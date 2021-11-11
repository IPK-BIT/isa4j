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

public class General {
	
	public static void validateInvestigationFile(Investigation investigation) {
    	//Make sure the investigation has at least one study and every study has at least one assay
    	if(investigation.getStudies().isEmpty())
    		throw new IllegalStateException(investigation.toString() + " has no studies attached to it.");
    	for(Study study : investigation.getStudies())
    		if(study.getAssays().isEmpty())
    			throw new IllegalStateException(study.toString() + " has no assays attached to it.");	
	}
	
	public static void validateStudyFile(Study study) {
		if(study.getInvestigation() == null)
			throw new IllegalStateException(study.toString() + " is not connected to an Investigation object.");
	}
	
	public static void validateAssayFile(Assay assay) {
		if(assay.getStudy() == null)
			throw new IllegalStateException(assay.toString() + " is not connected to a Study object.");
	}

}
