package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class StudyOrAssayTableObject {
	
	/**
	 * Return a map of field headers -> field values for this object, as it would be printed in a
	 * Study or Assay File. For example, for a Source object this could look like:
	 * {
	 * 	"Source Name" 				=> ["Plant 1"],
	 * 	"Characteristic[Organism] 	=> ["Arabidopsis thaliana", "NCBITaxon", "http://purl.obolibrary.org/obo/NCBITaxon_3702"],
	 *  "Characteristic[Genotype]	=> ["Col0"]
	 * }
	 * 
	 * For a Process it could look like the following:
	 * {
	 * 	"Protocol REF"					=> ["Growth"],
	 *  "ParameterValue[Rooting medium]	=> ["85% substrate, 15% sand"],
	 *  "ParameterValue[Container type] => ["pot", AgroOntology, "http://purl.obolibrary.org/obo/AGRO_00000309"],
	 * }
	 * @return
	 */
	public abstract Map<String, String[]> getFields();
	
	/**
	 * Like above, but here the order of items matters.
	 * @return
	 */
	public abstract LinkedHashMap<String, String[]> getHeaders();
	
//	/**
//	 * @return the nextItem
//	 */
//	public StudyOrAssayTableObject getNextStudyOrAssayTableObject();
	
//	/**
//	 * @param nextItem the nextItem to set
//	 */
//	public void setNextStudyOrAssayTableObject(StudyOrAssayTableObject nextItem);

}
