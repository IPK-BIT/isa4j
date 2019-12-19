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

/**
 * Sub class of {@link Parameter} for non numeric parameters without a
 * {@link Unit} for the parameter value.
 *
 * @author liufe, arendd
 */
public class NonNumericParameter extends Parameter {

//    @JsonIgnore
//    private Object studyOrAssay;
    /**
     * {@link OntologyAnnotation} for the value of the {@link NonNumericParameter}
     */
    private OntologyAnnotation ontologyTermOfValue;

    /**
     * Constructor providing name of the parameter
     *
     * @param nameOfParameter name of parameter
     */
    @JsonCreator
    public NonNumericParameter(@JsonProperty("nameOfParameter") String nameOfParameter) {
        super(nameOfParameter);
    }

    /**
     * Constructor providing name and ontology of value of parameter
     *
     * @param nameOfParameter              the name of parameter
     * @param ontologyTermOfParameterValue the value of parameter
     */
    public NonNumericParameter(String nameOfParameter, OntologyAnnotation ontologyTermOfParameterValue) {
        super(nameOfParameter);
        setValueOfParameter(ontologyTermOfParameterValue.getTerm());
        setOntologyTermOfValue(ontologyTermOfParameterValue);
    }

    /**
     * Constructor providing name and value of parameter
     *
     * @param nameOfParameter  the name of parameter
     * @param valueOfParameter the value of parameter
     */
    public NonNumericParameter(String nameOfParameter, String valueOfParameter) {
        super(nameOfParameter, valueOfParameter);
    }

    /**
     * get the ontologyterm for value
     *
     * @return the ontologyterm for value
     */
    public OntologyAnnotation getOntologyTermOfValue() {
        return ontologyTermOfValue;
    }

    /**
     * set the ontologyterm for value
     *
     * @param ontologyTermOfValue the ontologyterm for value
     */
    public void setOntologyTermOfValue(OntologyAnnotation ontologyTermOfValue) {

        if (ontologyTermOfValue != null && getValue() != null && !ontologyTermOfValue.getTerm().equals(getValue())) {
            LoggerUtil.logger.error("The valueOntologyTerm of Parameter is not right! NonNumericParameter: '" + getTerm() + "' value: '" + getValue() + "' "
                    + "ontologyTerm of value: '" + ontologyTermOfValue.getTerm() + "'");
            return;
        }

        if (getValue() == null && ontologyTermOfValue != null) {
            this.setValueOfParameter(ontologyTermOfValue.getTerm());
        }
        this.ontologyTermOfValue = ontologyTermOfValue;
    }

    /**
     * set value of parameter as ontology, the name of ontology is the value of
     * parameter.
     *
     * @param ontologyTermOfValue ontology of value
     */
    public void setValueOfParameter(OntologyAnnotation ontologyTermOfValue) {
        if (ontologyTermOfValue == null) {
            LoggerUtil.logger.error("The ontologyTerm of value in NonNumericParameter can't be null: " + getTerm());
            return;
        }
        if (super.getValue() != null && !ontologyTermOfValue.getTerm().equals(super.getValue())) {
            LoggerUtil.logger.error("The ontologyTerm is not same with value in NonNumericParameter: " + getTerm());
            return;
        }
        this.ontologyTermOfValue = ontologyTermOfValue;
        super.setValue(ontologyTermOfValue.getTerm());
//        checkOntologyOfValue(studyOrAssay);
    }

    /**
     * easy set function for value
     *
     * @param value value of parameter
     */
    public void setValueOfParameter(String value) {
        super.setValue(value);
    }

}
