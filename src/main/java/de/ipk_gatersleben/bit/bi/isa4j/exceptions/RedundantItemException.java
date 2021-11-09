/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.exceptions;

/**
 * Exception to handling non-unique parameter or attribute names
 * 
 * @author psaroudakis, arendd
 *
 */
public class RedundantItemException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 5225657453967236194L;

	public RedundantItemException(String errorMsg) {
		super(errorMsg);
	}

}