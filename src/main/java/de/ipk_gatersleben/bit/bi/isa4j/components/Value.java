package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

abstract class Value<T> {
	private T category;
	
	public Value(T category, OntologyAnnotation value, OntologyAnnotation unit) {
		Objects.requireNonNull(category);
		Objects.requireNonNull(value);
		
		this.category = category;
		this.setValue(value);
		this.setUnit(unit);
	}
	
	public Value(T category, OntologyAnnotation value) {
		this(category, value, null);
	}
	
	public Value(T category, String value) {
		this(category, new OntologyAnnotation(value, null, null));
	}
	
	public Value(T category, double value, OntologyAnnotation unit) {
		this(category, new OntologyAnnotation(String.valueOf(value), null, null), unit);
	}
	
	public Value(T category, double value) {
		this(category, value, null);
	}

	/**
	 * @return the category
	 */
	public T getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(T category) {
		Objects.requireNonNull(category);
		this.category = category;
	}
	private OntologyAnnotation value;
	private OntologyAnnotation unit;
	
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
