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
	 * The type of the {@link Comment}.
	 */
	private String type;

	/**
	 * The value of the {@link Comment}.
	 */
	private String value;
	
	/**
	 * Constructor.
	 */
	public Comment(String type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Get the type of the {@link Comment}
	 *
	 * @return the type of {@link Comment}
	 */
	public String getType() {
		return type;
	}
	
	public String getName() {
		return type;
	}

	/**
	 * Set the type of the {@link Comment}
	 *
	 * @param type type of the {@link Comment}
	 */
	public void setType(String type) {
		if (type == null) {
//			LoggerUtil.logger.error("The type of comment can't be null!");
			return;
		}
		this.type = type;
	}

	/**
	 * Get the value of {@link Comment}
	 *
	 * @return the value of {@link Comment}
	 */
	public String getContent() {
		return value;
	}

	/**
	 * Set the value of the {@link Comment}
	 *
	 * @param value the value of the {@link Comment}
	 */
	public void setContent(String value) {
		this.value = value;
	}

}
