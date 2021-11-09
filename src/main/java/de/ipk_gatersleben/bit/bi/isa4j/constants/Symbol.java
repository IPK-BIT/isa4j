/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.constants;

/**
 * Constants for frequently used symbols String symbols and literals
 *
 * @author liufe, arendd, psaroudakis
 */
public enum Symbol {

	TAB("\t"), ENTER(System.getProperty("line.separator")), SPACE(" "), SEMICOLON(";"), EMPTY(""), WILDCARD("?");

	private String value;

	private Symbol(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}