/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.constants;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;

/**
 * Constants for fixed attribute names for {@link Study} and {@link Assay} file creation
 *
 * @author liufe, arendd, psaroudakis
 */
public enum StudyAssayAttribute {
	
    SOURCE_NAME("Source Name"),
    SAMPLE_NAME("Sample Name"),
    CHARACTERISTICS("Characteristics[?]"),
    TERM_SOURCE_REF("Term Source REF"),
    TERM_ACCESSION_NUMBER("Term Accession Number"),
    ASSAY_NAME("Assay Name"),
    RAW_DATA_FILE("Raw Data File"),
    IMAGE_FILE("Image File"),
    PARAMETER_VALUE("Parameter Value[?]"),
    UNIT("Unit"),
    DERIVED_DATA_FILE("Derived Data File"),
    COMMENT("Comment[?]"),
    PROTOCOL("Protocol REF"),
    PROTOCOL_DATE("Date"),
    FACTOR_VALUE("Factor Value[?]");

	private String value;

	private StudyAssayAttribute(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}