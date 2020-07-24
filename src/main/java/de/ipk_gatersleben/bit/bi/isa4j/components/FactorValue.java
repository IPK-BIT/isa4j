/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

public class FactorValue extends Value<Factor> {

	public FactorValue(Factor category, double value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public FactorValue(Factor category, double value) {
		super(category, value);
	}

	public FactorValue(Factor category, OntologyAnnotation value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public FactorValue(Factor category, OntologyAnnotation value) {
		super(category, value);
	}

	public FactorValue(Factor category, String value) {
		super(category, value);
	}
	
	@Override
	public String toString() {
		return "<FactorValue> '" + (this.hasUnit() ? this.getValue().getTerm() + " " + this.getUnit().getTerm() : this.getValue().getTerm())
				+ " (" + this.getCategory().toString() + ")";
	}
}
