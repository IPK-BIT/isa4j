package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;

public class Sample extends Source implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	private List<FactorValue> factorValues = new ArrayList<FactorValue>();
	
	public Sample(String name) {
		super(name);
	}
	
	public Sample(String name, List<Characteristic> characteristics) {
		super(name, characteristics);
	}

	public void addFactorValue(FactorValue factorValue) {
		if(this.factorValues.stream().map(FactorValue::getCategory).anyMatch(factorValue.getCategory()::equals))
			throw new RedundantItemException("Multiple FactorValues for Factor: " + factorValue.getCategory().getName());
		this.factorValues.add(factorValue);
	}
	
	public CommentCollection comments() {
		return this.comments;
	}

	/**
	 * @return the factorValues
	 */
	public List<FactorValue> getFactorValues() {
		return factorValues;
	}
	
	Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(StudyAssayAttribute.SAMPLE_NAME.toString(), new String[]{this.getName()});
		fields.putAll(this.getFieldsForCharacteristics());
		fields.putAll(this.getFieldsForComments(this.comments));
		fields.putAll(this.getFieldsForValues(StudyAssayAttribute.FACTOR_VALUE, this.factorValues, fv -> fv.getCategory().getName()));

		return fields;
	}
	
	LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(StudyAssayAttribute.SAMPLE_NAME.toString(), new String[]{StudyAssayAttribute.SAMPLE_NAME.toString()});
		headers.putAll(this.getHeadersForCharacteristics());
		headers.putAll(this.getHeadersForComments(this.comments));
		headers.putAll(this.getHeadersForValues(StudyAssayAttribute.FACTOR_VALUE, this.factorValues, fv -> fv.getCategory().getName()));
		
		return headers;
	}
	
	/**
	 * @param factorValues the factorValues to set
	 */
	public void setFactorValues(List<FactorValue> factorValues) {
		factorValues.stream().forEach(Objects::requireNonNull);
		this.factorValues = factorValues;
	}
	
}
