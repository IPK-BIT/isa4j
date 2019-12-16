/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.util;

import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;

/**
 * Utility class with functions to handle several {@link String} tasks
 * 
 * @author liufe, arendd
 *
 */
public class StringUtil {
	/**
	 * In constants, the position of ? is the parameter to put in. So we need the
	 * index of ? this function is used only by writeToFile, get the index of ? and
	 * use by replace of {@link StringBuilder}.
	 *
	 * @param stringBuilder {@link StringBuilder} to take parameter
	 * @param parameter     to put in the {@link StringBuilder}
	 * @return the {@link StringBuilder}
	 */
	public static StringBuilder putParameterInStringBuilder(StringBuilder stringBuilder, String parameter) {
		int index = stringBuilder.indexOf(Symbol.ATTRIBUTE_REPLACE.toString());
		return stringBuilder.replace(index, index + 1, parameter);
	}

	/**
	 * Merge two attribute ending with \t.
	 *
	 * @param firstAttribute  first attribute to merge
	 * @param secondAttribute second attribute to merge
	 * @return the merged attribute
	 */
	public static String mergeAttributes(String firstAttribute, String secondAttribute) {
		StringBuilder stringBuilder = new StringBuilder(firstAttribute);
		stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(Symbol.SPACE).append(secondAttribute);
		return stringBuilder.toString();
	}

}