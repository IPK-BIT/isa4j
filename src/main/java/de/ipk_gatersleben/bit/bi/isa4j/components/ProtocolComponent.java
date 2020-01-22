package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class ProtocolComponent {
	
	private String name;
	private OntologyAnnotation type;
	
	public ProtocolComponent(String name) {
		this.name = name;
	}

	public ProtocolComponent(String name, OntologyAnnotation type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public OntologyAnnotation getType() {
		return type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = StringUtil.sanitize(name);
	}

	/**
	 * @param type the type to set
	 */
	public void setType(OntologyAnnotation type) {
		this.type = type;
	}
	
	

}
