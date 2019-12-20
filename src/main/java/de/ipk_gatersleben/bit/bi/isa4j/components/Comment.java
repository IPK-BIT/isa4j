/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.Study;

/**
 * Class representing a {@link Comment} that can be assigned to an
 * {@link Investigation}, {@link Study} or {@link Assay}.
 *
 * @author liufe, arendd
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
	public Comment(String type, String value) {
		this.name = type;
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
	 * @param name name of the {@link Comment}
	 */
	public void setName(String name) {
		this.name = name;
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
		this.value = value;
	}

}
