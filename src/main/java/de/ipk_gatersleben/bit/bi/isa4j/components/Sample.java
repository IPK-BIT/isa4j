package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class Sample extends Source {
	
	private List<FactorValue> factorValues = new ArrayList<FactorValue>();
	
	/**
	 * @return the factorValues
	 */
	public List<FactorValue> getFactorValues() {
		return factorValues;
	}

	/**
	 * @param factorValues the factorValues to set
	 */
	public void setFactorValues(List<FactorValue> factorValues) {
		factorValues.stream().forEach(Objects::requireNonNull);
		this.factorValues = factorValues;
	}
	
	public void addFactorValue(FactorValue factorValue) {
		if(this.factorValues.stream().map(FactorValue::getCategory).anyMatch(factorValue.getCategory()::equals))
			throw new RedundantItemException("Multiple FactorValues for Factor: " + factorValue.getCategory().getName());
		this.factorValues.add(factorValue);
	}

	public Sample(String name) {
		super(name);
	}
	
	public Sample(String name, List<Characteristic> characteristics) {
		super(name, characteristics);
	}
	
	private LinkedHashMap<String, String[]> getHeadersForFactorValues() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		for(FactorValue factorValue : this.factorValues) {
			List<String> factorValueColumns = new ArrayList<String>(3);
			String factorValueName = StringUtil.putNameInAttribute(StudyAssayAttribute.FACTOR_VALUE, factorValue.getCategory().getName());		
			factorValueColumns.add(factorValueName);
			factorValueColumns.addAll(this.getOntologyAnnotationExtensionHeaders(factorValue, c -> c.getValue()));
			if(factorValue.getUnit() != null) {
				factorValueColumns.add(StudyAssayAttribute.UNIT.toString());
				factorValueColumns.addAll(this.getOntologyAnnotationExtensionHeaders(factorValue, c -> c.getUnit()));				
			}
			headers.put(factorValueName, factorValueColumns.toArray(new String[0]));
		}
		
		return headers;
	}
	
	private HashMap<String, String[]> getFieldsForFactorValues() {
		HashMap<String, String[]> headers = new HashMap<String, String[]>();
		
		for(FactorValue factorValue : this.factorValues) {
			List<String> factorValueFields = new ArrayList<String>(3);
			String factorValueName = StringUtil.putNameInAttribute(StudyAssayAttribute.FACTOR_VALUE, factorValue.getCategory().getName());		
			factorValueFields.add(factorValue.getValue().getTerm());
			factorValueFields.addAll(this.getOntologyAnnotationExtensionFields(factorValue, c -> c.getValue()));
			if(factorValue.getUnit() != null) {
				factorValueFields.add(factorValue.getUnit().getTerm());
				factorValueFields.addAll(this.getOntologyAnnotationExtensionFields(factorValue, c -> c.getUnit()));				
			}
			headers.put(factorValueName, factorValueFields.toArray(new String[0]));
		}
		
		return headers;
	}
	
	public LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(StudyAssayAttribute.SAMPLE_NAME.toString(), new String[]{StudyAssayAttribute.SAMPLE_NAME.toString()});
		headers.putAll(this.getHeadersForCharacteristics());
		headers.putAll(this.getHeadersForFactorValues());
		
		return headers;
	}
	
	public Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(StudyAssayAttribute.SAMPLE_NAME.toString(), new String[]{this.getName()});
		fields.putAll(this.getFieldsForCharacteristics());
		fields.putAll(this.getFieldsForFactorValues());

		return fields;
	}
	
}
