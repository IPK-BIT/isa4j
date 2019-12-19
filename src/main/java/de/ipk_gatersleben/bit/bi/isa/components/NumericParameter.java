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
import de.ipk_gatersleben.bit.bi.isa.Investigation;
import de.ipk_gatersleben.bit.bi.isa.util.UnitMappingUtil;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;

/**
 * Sub class of {@link Parameter} for numeric parameters with a {@link Unit} for
 * the parameter value.
 *
 * @author liufe, arendd
 */
public class NumericParameter extends Parameter {

    /**
     * Constructor providing name and value of the parameter
     *
     * @param nameOfParameter  name of the parameter
     * @param valueOfParameter value of the parameter
     */
    @JsonCreator
    public NumericParameter(@JsonProperty("nameOfParameter") String nameOfParameter, @JsonProperty("valueOfParameter") double valueOfParameter) {
        super(nameOfParameter);
        setValue(valueOfParameter);
    }

    /**
     * Constructor
     *
     * @param nameOfParameter      name of parameter
     * @param valueOfParameter     value of parameter
     * @param unitOfParameterValue unit of parameter value
     */
    public NumericParameter(String nameOfParameter, double valueOfParameter, Unit unitOfParameterValue) {
        super(nameOfParameter);
        setValue(valueOfParameter);
        setUnit(unitOfParameterValue);
    }

    /**
     * Constructor
     *
     * @param nameOfParameter  name of paramter
     * @param valueOfParameter value of paramter
     * @param unit             unit of parameter
     */
    public NumericParameter(String nameOfParameter, int valueOfParameter, Unit unit) {
        super(nameOfParameter);
        setValue(valueOfParameter);
        setUnit(unit);
    }


    /**
     * Constructor
     *
     * @param nameOfParameter name of parameter
     */
    public NumericParameter(String nameOfParameter) {
        super(nameOfParameter);
    }

    /**
     * unit of parameter
     */
    private String unit;

    /**
     * get unit of parameter
     *
     * @return unit of parameter
     */
    public Unit getUnit() {
        return getUnit(unit);
    }


    /**
     * set unit of parameter
     *
     * @param unit unit of parameter
     */
    public void setUnit(Unit unit) {
        if (unit == null) {
            return;
        }
        this.unit = unit.getName();

        UnitMappingUtil.addUnitToCollection(unit, this.unit);
    }


    /**
     * set value of parameter
     *
     * @param value value
     */
    public void setValue(double value) {
        if (value % 1.0 == 0) {
            super.setValue(String.valueOf(Math.round(value)));
        } else {
            super.setValue(String.valueOf(value));
        }
    }

    /**
     * set value and unit of value
     *
     * @param value value
     * @param unit  unit
     */
    public void setValue(double value, Unit unit) {
        this.setValue(value);
        this.setUnit(unit);
    }

    /**
     * get unit of parameter
     *
     * @param unit name of unit
     * @return unit of parameter
     */
    protected static Unit getUnit(String unit) {
        if (unit != null) {
            OntologyAnnotation unitOntology = Investigation.unitMap.get(unit);
            if (unitOntology != null && unit.equals(unitOntology.getTerm())) {
                return new Unit(Investigation.unitMap.get(unit));
            } else return new Unit(unit);
        } else return null;
    }
}
