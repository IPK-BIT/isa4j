package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

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
	 * Sets the category of this characteristic, i.e. the part that will be
	 * printed in the header of the ISA file: Characteristic[category]
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = StringUtil.sanitize(Objects.requireNonNull(category, "Characteristic category cannot be null"));
	}

	/**
	 * Sets the value of this characteristic. Values of characteristics with
	 * the same category identifier will be printed in the same column.
	 * @param value the value to set
	 */
	public void setValue(OntologyAnnotation value) {
		this.value = Objects.requireNonNull(value, "Characteristic value cannot be null");
	}

}
