/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.components;

import com.fasterxml.jackson.annotation.*;

import de.ipk_gatersleben.bit.bi.isa4j.components.Component;

/**
 * Class to represent a characteristic of a observed object
 *
 * @author liufe, arendd
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = NonNumericCharacteristic.class),
		@JsonSubTypes.Type(value = NumericCharacteristic.class) })
public abstract class Characteristic extends Component {

	/**
	 * Constructor providing name and value of the {@link Characteristic}
	 *
	 * @param nameOfCharacteristic  name of the {@link Characteristic}
	 * @param valueOfCharacteristic value of the {@link Characteristic}
	 */
	@JsonCreator
	public Characteristic(@JsonProperty("name") String nameOfCharacteristic,
			@JsonProperty("value") String valueOfCharacteristic) {
		super(nameOfCharacteristic, valueOfCharacteristic);
	}

	/**
	 * Constructor providing name
	 *
	 * @param nameOfCharacteristic name of {@link Characteristic}
	 */
	public Characteristic(String nameOfCharacteristic) {
		super(nameOfCharacteristic);
	}

}
