/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.constants;

/**
 * Constants for fixed attribute names for {@link Assay} file creation
 *
 * @author liufe, arendd
 */
public enum StudyAssayAttribute {
    SOURCE_NAME("Source Name"),

    SAMPLE_NAME("Sample Name"),
    CHARACTERISTICS("Characteristics[?]"),
    TERM_SOURCE_REF("Term Source REF"),
    TERM_ACCESSION_NUMBER("Term Accession Number"),
    ASSAY_NAME("Assay Name" + Symbol.TAB),
    RAW_DATA_FILE("Raw Data File" + Symbol.TAB),
    IMAGE_FILE("Image File" + Symbol.TAB),
    PARAMETER_VALUE("Parameter Value[?]" + Symbol.TAB),
    UNIT("Unit" + Symbol.TAB),
    DERIVED_DATA_FILE("Derived Data File" + Symbol.TAB),
    COMMENT("Comment[?]" + Symbol.TAB),
    EXTRACT_NAME("Extract Name" + Symbol.TAB),
    LABELED_EXTRACT_NAME("Labeled Extract Name" + Symbol.TAB),
    PROTOCOL("Protocol REF" + Symbol.TAB),
    FACTOR_VALUE("Factor Value[?]" + Symbol.TAB);

	private String value;

	private StudyAssayAttribute(String val) {
		this.value = val;
	}

	@Override
	public String toString() {
		return value;
	}
}
