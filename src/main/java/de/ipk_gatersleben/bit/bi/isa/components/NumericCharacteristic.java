/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.ipk_gatersleben.bit.bi.isa.util.UnitMappingUtil;

/**
 * Sub class for a {@link Characteristic} with a numeric value that has to have
 * {@link Unit}.
 *
 * @author liufe, arendd
 */
public class NumericCharacteristic extends Characteristic {
    /**
     * The {@link Unit} of the {@link NumericCharacteristic}
     */
    private String unit;

    /**
     * Constructor providing name and value of the {@link NumericCharacteristic}
     *
     * @param nameOfCharacteristic  name of the {@link NumericCharacteristic}
     * @param valueOfCharacteristic value of the {@link NumericCharacteristic}
     */
    @JsonCreator
    public NumericCharacteristic(@JsonProperty("nameOfCharacteristic") String nameOfCharacteristic,
                                 @JsonProperty("value") double valueOfCharacteristic) {
        super(nameOfCharacteristic);
        setValue(valueOfCharacteristic);
    }

    /**
     * Full constructor with name, value and unit of the the
     * {@link NumericCharacteristic}
     *
     * @param nameOfCharacteristic  name of the {@link NumericCharacteristic}
     * @param valueOfCharacteristic value of the {@link NumericCharacteristic}
     * @param unitOfCharacteristic  unit of the {@link NumericCharacteristic}
     */
    public NumericCharacteristic(String nameOfCharacteristic, double valueOfCharacteristic, Unit unitOfCharacteristic) {
        super(nameOfCharacteristic);
        setValue(valueOfCharacteristic);
        setUnit(unitOfCharacteristic);
    }

    /**
     * Get the {@link Unit} of the value of the {@link NumericCharacteristic}
     *
     * @return unit of the {@link NumericCharacteristic}
     */
    public Unit getUnit() {
        return NumericParameter.getUnit(unit);
    }

    /**
     * Set {@link Unit} of the value of the {@link NumericCharacteristic}
     *
     * @param unitOfValue {@link Unit} of the value of the
     *                    {@link NumericCharacteristic}
     */
    public void setUnit(Unit unitOfValue) {
        if (unitOfValue == null) {
            return;
        }
        this.unit = unitOfValue.getName();

        UnitMappingUtil.addUnitToCollection(unitOfValue, this.unit);
    }

    /**
     * Set value of the {@link NumericCharacteristic}
     *
     * @param value value of the {@link NumericCharacteristic}
     */
    public void setValue(double value) {
        if (value % 1.0 == 0) {
            super.setValue(String.valueOf(Math.round(value)));
        } else {
            super.setValue(String.valueOf(value));
        }
    }

    /**
     * Set value and {@link Unit} of the {@link NumericCharacteristic}
     *
     * @param value value
     * @param unit  unit
     */
    public void setValue(double value, Unit unit) {
        this.setValue(value);
        setUnit(unit);
    }
}
