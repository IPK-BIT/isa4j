package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

public class ProtocolParameter {
	
	private OntologyAnnotation name;

	public ProtocolParameter(OntologyAnnotation name) {
		this.setName(name);
	}
	
	public ProtocolParameter(String name) {
		this(new OntologyAnnotation(name, null, null));
	}

	/**
	 * @return the name
	 */
	public OntologyAnnotation getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(OntologyAnnotation name) {
		this.name = Objects.requireNonNull(name, "ProtocolParameter name OntologyAnnotation cannot be null");
	}

}
