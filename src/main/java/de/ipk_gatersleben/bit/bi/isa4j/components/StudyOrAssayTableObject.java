package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public abstract class StudyOrAssayTableObject {
	
	/**
	 * Return a map of field headers -> field values for this object, as it would be printed in a
	 * Study or Assay File. For example, for a Source object this could look like:
	 * {
	 * 	"Source Name" 				=> ["Plant 1"],
	 * 	"Characteristic[Organism]" 	=> ["Arabidopsis thaliana", "NCBITaxon", "http://purl.obolibrary.org/obo/NCBITaxon_3702"],
	 *  "Characteristic[Genotype]"	=> ["Col0"]
	 * }
	 * 
	 * For a Process it could look like the following:
	 * {
	 * 	"Protocol REF"						=> ["Growth"],
	 *  "ParameterValue[Rooting medium]"	=> ["85% substrate, 15% sand"],
	 *  "ParameterValue[Container type]" 	=> ["pot", AgroOntology, "http://purl.obolibrary.org/obo/AGRO_00000309"],
	 * }
	 * @return
	 */
	public abstract Map<String, String[]> getFields();
	
	/**
	 * Like above, but here the order of items matters and instead of having actual values in the String arrays you only
	 * have the headers:
	 * {
	 * 	"Source Name" 				=> ["Source Name"],
	 * 	"Characteristic[Organism]" 	=> ["Characteristic [Organism]", "Term Source REF", "Term Accession Number"],
	 *  "Characteristic[Genotype]"	=> ["Characteristic [Genotype]"]
	 * }
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
	
	protected <T extends Value<?>> LinkedHashMap<String, String[]> getHeadersForValues(StudyAssayAttribute tName, List<T> tValues, Function<T, String> lambda) {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		for(T tValue : tValues) {
			List<String> tValueColumns = new ArrayList<String>(3);
			String tValueName = StringUtil.putNameInAttribute(tName, lambda.apply(tValue));		
			tValueColumns.add(tValueName);
			tValueColumns.addAll(this.getOntologyAnnotationExtensionHeaders(tValue, c -> c.getValue()));
			if(tValue.getUnit() != null) {
				tValueColumns.add(StudyAssayAttribute.UNIT.toString());
				tValueColumns.addAll(this.getOntologyAnnotationExtensionHeaders(tValue, c -> c.getUnit()));				
			}
			headers.put(tValueName, tValueColumns.toArray(new String[0]));
		}
		
		return headers;
	}
	
	protected <T extends Value<?>> HashMap<String, String[]> getFieldsForValues(StudyAssayAttribute tName, List<T> tValues, Function<T, String> lambda) {
		HashMap<String, String[]> headers = new HashMap<String, String[]>();
		
		for(T tValue : tValues) {
			List<String> tValueFields = new ArrayList<String>(3);
			String tValueName = StringUtil.putNameInAttribute(tName, lambda.apply(tValue));		
			tValueFields.add(tValue.getValue().getTerm());
			tValueFields.addAll(this.getOntologyAnnotationExtensionFields(tValue, c -> c.getValue()));
			if(tValue.getUnit() != null) {
				tValueFields.add(tValue.getUnit().getTerm());
				tValueFields.addAll(this.getOntologyAnnotationExtensionFields(tValue, c -> c.getUnit()));				
			}
			headers.put(tValueName, tValueFields.toArray(new String[0]));
		}
		
		return headers;
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
