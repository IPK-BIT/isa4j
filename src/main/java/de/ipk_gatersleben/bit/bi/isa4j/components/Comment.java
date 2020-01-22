/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;


/**
 * Class representing a {@link Comment} that can be assigned to an
 * {@link Investigation}, {@link Study}, {@link Assay}, {@link DataFile},
 * {@link Factor}, {@link OntologyAnnotation}, {@link Person}, {@link Process},
 * {@link Protocol}, {@link Publication}, {@link Sample}, or {@link Source}.
 *
 */
public class Comment {

	/**
	 * The name of the {@link Comment}.
	 */
	private String name;

	/**
	 * The value of the {@link Comment}.
	 */
	private String value;
	
	/**
	 * Constructor.
	 */
	/**
	 * @param name Name of the comment. Cannot be null.
	 * @param value
	 */
	public Comment(String name, String value) {
		this.setName(name);
		this.value = value;
	}

	/**
	 * Get the name of the {@link Comment}
	 *
	 * @return the name of {@link Comment}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the {@link Comment}
	 *
	 * @param name name of the {@link Comment}. Cannot be null
	 */
	public void setName(String name) {
		this.name = StringUtil.sanitize(Objects.requireNonNull(name, "Comment name cannot be null"));
	}

	/**
	 * Get the value of {@link Comment}
	 *
	 * @return the value of {@link Comment}
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value of the {@link Comment}
	 *
	 * @param value the value of the {@link Comment}
	 */
	public void setValue(String value) {
		this.value = StringUtil.sanitize(value);
	}

}
