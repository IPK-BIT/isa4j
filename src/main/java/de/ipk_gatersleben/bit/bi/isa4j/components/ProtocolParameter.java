/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
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
		this.name = Objects.requireNonNull(name, "ProtocolParameter name OntologyAnnotation object cannot be null");
	}
	
	@Override
	public String toString() {
		return "<ProtocolParameter> '" + this.name + "'";
	}

}
