/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;

/**
 * 
 * Interface for providing functions for the specific {@link Study} and
 * {@link Assay} fields of an ISA configuration
 * 
 * @author psaroudakis, arendd
 *
 */
public interface WideTableConfigEnum {
	/**
	 * Provide the name of the field.
	 * 
	 * @return name
	 */
	public String getFieldName();

	/**
	 * Check if the specific field is required in the corresponding configuration
	 * 
	 * @return <code>true</code> if the field is required
	 */
	public boolean isRequired();

	/**
	 * Provide the position of the field within the {@link Study} or {@link Assay}
	 * File
	 * 
	 * @return index
	 */
	public int getGroupIndex();
}
