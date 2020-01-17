package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;

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
	
	
	/**
	 * These two methods are meant to simplify getFields and getHeaders for Objects that can have TERM SOURCE REF and TERM ACCESSION NUMBERS but don't have to
	 * @param <T>
	 * @param thing
	 * @param lambda
	 * @return
	 */
	protected <T> ArrayList<String> getOntologyAnnotationExtensionHeaders(T thing, Function<T, OntologyAnnotation> lambda) {
		ArrayList<String> extensionHeaders = new ArrayList<String>(2);
		OntologyAnnotation ontologyAnnotation = lambda.apply(thing);
		if(ontologyAnnotation.getSourceREF() != null)
			extensionHeaders.add(StudyAssayAttribute.TERM_SOURCE_REF.toString());
		if(ontologyAnnotation.getTermAccession() != null)
			extensionHeaders.add(StudyAssayAttribute.TERM_ACCESSION_NUMBER.toString());
		return extensionHeaders;
	}
	
	protected <T> ArrayList<String> getOntologyAnnotationExtensionFields(T thing, Function<T, OntologyAnnotation> lambda) {
		ArrayList<String> extensionFields = new ArrayList<String>(2);
		OntologyAnnotation ontologyAnnotation = lambda.apply(thing);
		if(ontologyAnnotation.getSourceREF() != null)
			extensionFields.add(ontologyAnnotation.getSourceREF().getName());
		if(ontologyAnnotation.getTermAccession() != null)
			extensionFields.add(ontologyAnnotation.getTermAccession());
		return extensionFields;
	}
	
	private StudyOrAssayTableObject nextStudyOrAssayTableObject;

	/**
	 * @return the nextStudyOrAssayTableObject
	 */
	StudyOrAssayTableObject getNextStudyOrAssayTableObject() {
		return nextStudyOrAssayTableObject;
	}

	/**
	 * @param nextStudyOrAssayTableObject the nextStudyOrAssayTableObject to set
	 */
	protected void setNextStudyOrAssayTableObject(StudyOrAssayTableObject nextStudyOrAssayTableObject) {
		this.nextStudyOrAssayTableObject = nextStudyOrAssayTableObject;
	}
	
	
//	/**
//	 * @return the nextItem
//	 */
//	public StudyOrAssayTableObject getNextStudyOrAssayTableObject();
	
//	/**
//	 * @param nextItem the nextItem to set
//	 */
//	public void setNextStudyOrAssayTableObject(StudyOrAssayTableObject nextItem);

}
