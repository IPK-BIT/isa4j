/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;

public class General {
	
	protected static void validateInvestigationFile(Investigation investigation) {
    	//Make sure the investigation has at least one study and every study has at least one assay
    	if(investigation.getStudies().isEmpty())
    		throw new IllegalStateException(investigation.toString() + " has no studies attached to it.");
    	for(Study study : investigation.getStudies())
    		if(study.getAssays().isEmpty())
    			throw new IllegalStateException(study.toString() + " has no assays attached to it.");	
	}
	
	protected static void validateStudyFile(Study study) {
		Objects.requireNonNull(study.getInvestigation(), study.toString() + " is not connected to an Investigation object.");
	}
	
	protected static void validateAssayFile(Assay assay) {
		Objects.requireNonNull(assay.getStudy(), assay.toString() + " is not connected to a Study object.");
	}

}
