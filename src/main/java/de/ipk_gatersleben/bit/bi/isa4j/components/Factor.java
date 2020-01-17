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

/**
 * Class representing a factor value to describe an observed in the
 * {@link Study} or {@link Assay}, independent from a {@link Protocol}
 *
 * @author liufe, arendd
 */

public class Factor implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	public CommentCollection comments() {
		return this.comments;
	}

	/**
	 * {@link OntologyAnnotation} description of {@link Factor}
	 */
	private OntologyAnnotation type;
	
	private String name;
	
	public Factor(String name, OntologyAnnotation type) {
		this.name = Objects.requireNonNull(name, "Factor name cannot be null");
		this.type = type;
	}
	
	public Factor(String name) {
		this(name, null);
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
		this.name = name;
	}

	/**
	 * Set {@link OntologyAnnotation} of the {@link Factor} type
	 *
	 * @param type {@link OntologyAnnotation} of the {@link Factor} type
	 */
	public void setType(OntologyAnnotation type) {
		this.type = type;
	}
}
