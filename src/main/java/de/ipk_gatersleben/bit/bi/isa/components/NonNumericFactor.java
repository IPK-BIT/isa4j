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
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;

/**
 * Sub class of {@link Factor} for non numeric factors without a
 * {@link Unit} for the factor value.
 *
 * @author liufe, arendd
 */
public class NonNumericFactor extends Factor {

    private OntologyAnnotation ontologyTermOfValue;

    /**
     * Constructor providing the name of the {@link NonNumericFactor}
     *
     * @param factorName name of the {@link NonNumericFactor}
     */
    @JsonCreator
    public NonNumericFactor(@JsonProperty("nameOfFactor") String factorName) {
        super(factorName);
    }

    /**
     * Constructor providing name and {@link OntologyAnnotation} of value of the
     * {@link NonNumericFactor}
     *
     * @param factorName              the name of the {@link NonNumericFactor}
     * @param factorValueOntologyTerm the {@link OntologyAnnotation} of the value of the
     *                                {@link NonNumericFactor}
     */
    public NonNumericFactor(String factorName, OntologyAnnotation factorValueOntologyTerm) {
        super(factorName);
        setValue(factorValueOntologyTerm.getTerm());
        setOntologyTermOfValue(factorValueOntologyTerm);
    }

    /**
     * Constructor providing name and value of the {@link NonNumericFactor}
     *
     * @param factorName  the name of the {@link NonNumericFactor}
     * @param factorValue the value the name of the {@link NonNumericFactor}
     */
    public NonNumericFactor(String factorName, String factorValue) {
        super(factorName, factorValue);
    }


    /**
     * Get the {@link OntologyAnnotation} of the value of the {@link NonNumericFactor}
     *
     * @return the {@link OntologyAnnotation} of the value of the {@link NonNumericFactor}
     */
    public OntologyAnnotation getOntologyTermOfValue() {
        return ontologyTermOfValue;
    }

    /**
     * Set the {@link OntologyAnnotation} of the value of the {@link NonNumericFactor}
     *
     * @param ontologyTermOfValue the {@link OntologyAnnotation} to set for the value of
     *                            the {@link NonNumericFactor}
     */
    public void setOntologyTermOfValue(OntologyAnnotation ontologyTermOfValue) {

        if (ontologyTermOfValue != null && super.getValue() != null && !ontologyTermOfValue.getTerm().equals(super.getValue())) {
            LoggerUtil.logger.error("The valueOntologyTerm of Factor is not right! Factor: '" + super.getTerm() + "' value: '"
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
            LoggerUtil.logger.error("The ontolgyTerm of value in NonNumericFactor can't be null");
            return;
        }
        if (super.getTerm() != null && !ontologyTermOfValue.getTerm().equals(super.getTerm())) {
            LoggerUtil.logger.error("The ontologyTerm is not same with value in NonNumericFactor: " + getTerm());
            return;
        }
        this.ontologyTermOfValue = ontologyTermOfValue;
        super.setValue(ontologyTermOfValue.getTerm());
//        checkOntologyOfValue(studyOrAssay);
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
