/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

abstract class Value<T> {
	private T category;
	
	private OntologyAnnotation unit;
	
	private OntologyAnnotation value;
	
	public Value(T category, double value) {
		this(category, value, null);
	}
	
	public Value(T category, double value, OntologyAnnotation unit) {
		this(category, new OntologyAnnotation(String.valueOf(value), null, null), unit);
	}
	
	public Value(T category, OntologyAnnotation value) {
		this(category, value, null);
	}

	public Value(T category, OntologyAnnotation value, OntologyAnnotation unit) {
		this.setCategory(category);
		this.setValue(value);
		this.setUnit(unit);
	}

	public Value(T category, String value) {
		this(category, new OntologyAnnotation(value, null, null));
	}
	/**
	 * @return the category
	 */
	public T getCategory() {
		return category;
	}
	/**
	 * @return the unit
	 */
	public OntologyAnnotation getUnit() {
		return unit;
	}
	
	/**
	 * @return the value
	 */
	public OntologyAnnotation getValue() {
		return value;
	}

	public boolean hasUnit() {
		return !(this.unit == null);
	}
	
	/**
	 * @param category the category to set
	 */
	public void setCategory(T category) {
		this.category = Objects.requireNonNull(category, "Category cannot be null");
	}
	
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(OntologyAnnotation unit) {
		this.unit = unit;
	}

	public void setValue(int value) {
		this.value = new OntologyAnnotation(String.valueOf(value), null, null);
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(OntologyAnnotation value) {
		this.value = Objects.requireNonNull(value, "Value cannot be null");
	}
	
	public void setValue(String value) {
		this.value = new OntologyAnnotation(Objects.requireNonNull(value, "Value cannot be null"), null, null);
	}

}
