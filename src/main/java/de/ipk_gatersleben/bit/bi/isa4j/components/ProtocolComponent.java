package de.ipk_gatersleben.bit.bi.isa4j.components;

public class ProtocolComponent {
	
	private String name;
	private OntologyAnnotation type;
	
	public ProtocolComponent(String name, OntologyAnnotation type) {
		this.name = name;
		this.type = type;
	}

	public ProtocolComponent(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public OntologyAnnotation getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(OntologyAnnotation type) {
		this.type = type;
	}
	
	

}
