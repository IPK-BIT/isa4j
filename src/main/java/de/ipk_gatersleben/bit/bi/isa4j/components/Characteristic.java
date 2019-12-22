package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

public class Characteristic {
	
	private String category;
	private OntologyAnnotation value;
	
	public Characteristic(String category, OntologyAnnotation value) {
		this.category = category;
		this.value = value;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the value
	 */
	public OntologyAnnotation getValue() {
		return value;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(OntologyAnnotation value) {
		Objects.requireNonNull(value);
		this.value = value;
	}

}
