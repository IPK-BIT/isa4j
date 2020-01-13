package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

public class FactorValue {
	
	private Factor category;
	private OntologyAnnotation value;
	private OntologyAnnotation unit;
	
	private FactorValue(Factor category, OntologyAnnotation value, OntologyAnnotation unit) {
		Objects.requireNonNull(category);
		Objects.requireNonNull(value);
		
		this.category = category;
		this.value = value;
		this.unit = unit;
	}
	
	public FactorValue(Factor category, OntologyAnnotation value) {
		this(category, value, null);
	}
	
	public FactorValue(Factor category, String value) {
		this(category, new OntologyAnnotation(value, null, null));
	}
	
	public FactorValue(Factor category, double value, OntologyAnnotation unit) {
		this(category, new OntologyAnnotation(String.valueOf(value), null, null), unit);
	}
	
	public FactorValue(Factor category, double value) {
		this(category, value, null);
	}

	/**
	 * @return the category
	 */
	public Factor getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Factor category) {
		Objects.requireNonNull(category);
		this.category = category;
	}

	/**
	 * @return the value
	 */
	public OntologyAnnotation getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(OntologyAnnotation value) {
		Objects.requireNonNull(value);
		this.value = value;
	}
	
	public void setValue(String value) {
		Objects.requireNonNull(value);
		this.value = new OntologyAnnotation(value, null, null);
	}
	
	public void setValue(int value) {
		this.value = new OntologyAnnotation(String.valueOf(value), null, null);
	}

	/**
	 * @return the unit
	 */
	public OntologyAnnotation getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(OntologyAnnotation unit) {
		this.unit = unit;
	}
	
	public boolean hasUnit() {
		return !(this.unit == null);
	}

}
