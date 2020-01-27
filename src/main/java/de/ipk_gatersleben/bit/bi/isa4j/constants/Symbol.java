/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.constants;

/**
 * Constants for frequently used symbols String symbols and literals
 *
 * @author liufe, arendd
 */
public enum Symbol {

	TAB("\t"), ENTER(System.getProperty("line.separator")), SPACE(" "), SEMICOLON(";"), EMPTY(""),ATTRIBUTE_REPLACE("?");

	private String value;

	private Symbol(String val) {
		this.value = val;
	}

	@Override
	public String toString() {
		return value;
	}
}
