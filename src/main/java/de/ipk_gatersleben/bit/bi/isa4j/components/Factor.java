/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class representing a factor to describe an observed sample in the
 * {@link Study} or {@link Assay}, independent from a {@link Protocol}
 */

public class Factor implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	private String name;

	/**
	 * {@link OntologyAnnotation} description of {@link Factor}
	 */
	private OntologyAnnotation type;
	
	public Factor(String name) {
		this(name, null);
	}
	
	public Factor(String name, OntologyAnnotation type) {
		this.name = Objects.requireNonNull(name, "Factor name cannot be null");
		this.type = type;
	}
	
	public CommentCollection comments() {
		return this.comments;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get {@link OntologyAnnotation} for the type of the {@link Factor}
	 *
	 * @return the {@link OntologyAnnotation} for the type of this {@link Factor}
	 */
	public OntologyAnnotation getType() {
		return type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = StringUtil.sanitize(Objects.requireNonNull(name, "Factor Name cannot be null"));
	}

	/**
	 * Set {@link OntologyAnnotation} of the {@link Factor} type
	 *
	 * @param type {@link OntologyAnnotation} of the {@link Factor} type
	 */
	public void setType(OntologyAnnotation type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "<Factor> '" + this.name + "'";
	}
}
