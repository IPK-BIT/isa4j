/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

/**
 * Super class of {@link Characteristic}, {@link Factor} and {@link Parameter}
 *
 * @author liufe, arendd
 */
public abstract class Component {

	/**
	 * Name of the {@link Component}
	 */
	private String name;

	/**
	 * Value of the {@link Component}
	 */
	private String value;

	/**
	 * Constructor providing name of the {@link Component}
	 *
	 * @param name name of the {@link Component}
	 */
	protected Component(String name) {
		this.name = name;
	}

	/**
	 * Constructor providing name and value of the {@link Component}
	 *
	 * @param name  name of the {@link Component}
	 * @param value value of the {@link Component}
	 */
	protected Component(String name, String value) {
		this.name = name;
		this.value = value;
	}


	/**
	 * get name of component
	 * 
	 * @return name of component
	 */
	public String getName() {
		return name;
	}

	/**
	 * get value
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * set name of component
	 * 
	 * @param name
	 */
	public void setName(String name) {
		if (name == null) {
			//LoggerUtil.logger.error("The name of component can't be null!");
			return;
		}
		this.name = name;
	}

	/**
	 * set value of component
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
