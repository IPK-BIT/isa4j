/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
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
	
	@Override
	public String toString() {
		return "<ProtocolComponent> '" + this.name + "'";
	}

}
