package de.ipk_gatersleben.bit.bi.isa4j.components;

public class ProtocolParameter {
	
	private OntologyAnnotation name;

	public ProtocolParameter(OntologyAnnotation name) {
		this.name = name;
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
		this.name = name;
	}

}
