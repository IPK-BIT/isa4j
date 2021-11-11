package de.ipk_gatersleben.bit.bi.isa4j.configurations;

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
		
	}
	
	protected static void validateAssayFile(Assay assay) {
		
	}

}
