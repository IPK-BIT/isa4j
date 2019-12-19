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
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;

public class NonNumericCharacteristic extends Characteristic {

    private OntologyAnnotation ontologyTermOfValue;

    /**
     * Constructor providing the name of the {@link NonNumericCharacteristic}
     *
     * @param nameOfCharacteristic  name of the {@link NonNumericCharacteristic}
     * @param valueOfCharacteristic the value the name of the {@link NonNumericCharacteristic}
     */
    @JsonCreator
    public NonNumericCharacteristic(@JsonProperty("nameOfCharacteristic") String nameOfCharacteristic, @JsonProperty("valueOfCharacteristic") String valueOfCharacteristic) {
        super(nameOfCharacteristic, valueOfCharacteristic);
    }

    /**
     * Constructor providing name and {@link OntologyAnnotation} of value of the
     * {@link NonNumericCharacteristic}
     *
     * @param nameOfCharacteristic              the name of the {@link NonNumericCharacteristic}
     * @param ontologyTermOfCharacteristicValue the {@link OntologyAnnotation} of the value of the
     *                                          {@link NonNumericCharacteristic}
     */
    public NonNumericCharacteristic(String nameOfCharacteristic, OntologyAnnotation ontologyTermOfCharacteristicValue) {
        super(nameOfCharacteristic, ontologyTermOfCharacteristicValue.getTerm());
        setOntologyTermOfValue(ontologyTermOfCharacteristicValue);
    }

    /**
     * Get the {@link OntologyAnnotation} of the value of the {@link NonNumericCharacteristic}
     *
     * @return the {@link OntologyAnnotation} of the value of the {@link NonNumericCharacteristic}
     */
    public OntologyAnnotation getOntologyTermOfValue() {
        return ontologyTermOfValue;
    }

    /**
     * Set the {@link OntologyAnnotation} of the value of the {@link NonNumericCharacteristic}
     *
     * @param ontologyTermOfValue the {@link OntologyAnnotation} to set for the value of
     *                            the {@link NonNumericCharacteristic}
     */
    public void setOntologyTermOfValue(OntologyAnnotation ontologyTermOfValue) {

        if (ontologyTermOfValue != null && super.getValue() != null && !ontologyTermOfValue.getTerm().equals(super.getValue())) {
            LoggerUtil.logger.error("The valueOntologyTerm of Characteristic is not right! Characteristic: '" + super.getTerm() + "' value: '"
                    + super.getValue() + "' " + "ontologyTerm of value: '" + ontologyTermOfValue.getTerm() + "'");
            return;
        }
        this.ontologyTermOfValue = ontologyTermOfValue;
        if (ontologyTermOfValue != null && super.getValue() == null) {
            super.setValue(ontologyTermOfValue.getTerm());
        }
    }

    /**
     * Set the value of {@link NonNumericFactor} as {@link OntologyAnnotation}
     *
     * @param ontologyTermOfValue the {@link OntologyAnnotation} to set
     */
    public void setValue(OntologyAnnotation ontologyTermOfValue) {
        if (ontologyTermOfValue == null) {
            LoggerUtil.logger.error("The ontolgyTerm of value in NonNumericCharacteristic can't be null");
            return;
        }
        if (super.getTerm() != null && !ontologyTermOfValue.getTerm().equals(super.getTerm())) {
            LoggerUtil.logger.error("The ontologyTerm is not same with value in NonNumericCharacteristic: " + ontologyTermOfValue.getTerm());
            return;
        }
        this.ontologyTermOfValue = ontologyTermOfValue;
        super.setValue(ontologyTermOfValue.getTerm());
    }

    /**
     * Set the value of {@link NonNumericFactor}
     *
     * @param value the value to set for the {@link NonNumericFactor}
     */
    public void setValue(String value) {
        super.setValue(value);
    }
}
