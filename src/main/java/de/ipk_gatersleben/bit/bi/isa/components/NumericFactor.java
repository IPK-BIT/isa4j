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
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;

/**
 * Sub class of {@link Factor} for numeric factors with a {@link Unit} for
 * the factor value.
 *
 * @author liufe, arendd
 */
public class NumericFactor extends Factor {
    /**
     * The {@link Unit} of the {@link NumericFactor}
     */
    private String unit;

    /**
     * Constructor with the name of the {@link NumericFactor}
     *
     * @param factorName name of the {@link NumericFactor}
     */
    public NumericFactor(String factorName) {
        super(factorName);
    }

    /**
     * Constructor providing name and value of the {@link NumericFactor}
     *
     * @param factorName  name of the {@link NumericFactor}
     * @param factorValue value of the {@link NumericFactor}
     */
    @JsonCreator
    public NumericFactor(@JsonProperty("nameOfFactor") String factorName, @JsonProperty("value") double factorValue) {
        super(factorName);
        setValue(factorValue);
    }

    /**
     * Full constructor with name, value and unit of the the {@link NumericFactor}
     *
     * @param factorName      name of the {@link NumericFactor}
     * @param factorValue     value of the {@link NumericFactor}
     * @param factorValueUnit unit of the {@link NumericFactor}
     */
    public NumericFactor(String factorName, double factorValue, Unit factorValueUnit) {
        super(factorName);
        setValue(factorValue);
        setUnit(factorValueUnit);
    }


    /**
     * Get the {@link Unit} of the value of the {@link NumericFactor}
     *
     * @return unit of the {@link NumericFactor}
     */
    public Unit getUnit() {
        return NumericParameter.getUnit(unit);
    }

    /**
     * Set {@link Unit} of the value of the {@link NumericFactor}
     *
     * @param factorValueUnit {@link Unit} of the value of the {@link NumericFactor}
     */
    public void setUnit(Unit factorValueUnit) {
        if (factorValueUnit == null) {
            return;
        }
        this.unit = factorValueUnit.getName();

        UnitMappingUtil.addUnitToCollection(factorValueUnit, this.unit);
    }

    /**
     * Set value of the {@link NumericFactor}
     *
     * @param value value of the {@link NumericFactor}
     */
    public void setValue(double value) {
        if (value % 1.0 == 0) {
            super.setValue(String.valueOf(Math.round(value)));
        } else {
            super.setValue(String.valueOf(value));
        }
    }

    /**
     * Set value and {@link Unit} of the {@link NumericFactor}
     *
     * @param value value
     * @param unit  unit
     */
    public void setValue(double value, Unit unit) {
        this.setValue(value);
        setUnit(unit);
    }
}
