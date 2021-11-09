/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.util;

import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;

/**
 * Utility class with functions to handle several {@link String} tasks
 * 
 * @author liufe, arendd, psaroudakis
 *
 */
public class StringUtil {
	/**
	 * In constants, the position of ? is the parameter to put in. So we need the
	 * index of ? this function is used only by writeToFile, get the index of ? and
	 * use by replace of {@link StringBuilder}.
	 *
	 * @param <T>       the attribute type
	 * @param attribute the attribute to add
	 * @param parameter to put in the {@link StringBuilder}
	 * @return the type
	 */
	public static <T> String putNameInAttribute(T attribute, String parameter) {
		return attribute.toString().replace(Symbol.WILDCARD.toString(), parameter);
	}

	/**
	 * Removes Symbol.TABs and Symbol.ENTERs from an input String and replaces them
	 * with Symbol.EMPTY. To be used on any String input by the user that ends up in
	 * the ISATab files.
	 * 
	 * @param input the input {@link String}
	 * @return the cleanded {@link String}
	 */
	public static String sanitize(String input) {
		if (input == null) {
			return null;
		} else {
			return input.replaceAll(Symbol.TAB.toString(), Symbol.SPACE.toString()).replaceAll(Symbol.ENTER.toString(),
					Symbol.SPACE.toString());
		}
	}
}