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
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;

/**
 * Class representing an {@link Unit} for a {@link Parameter} or {@link Factor}
 * value.
 *
 * @author liufe, arendd
 */
public class Unit {

    /**
     * The name of {@link Unit}
     */
    private String name;

    /**
     * The {@link OntologyAnnotation} of the {@link Unit}
     */
    private OntologyAnnotation ontology;

    /**
     * Constructor with a given name for the {@link Unit}
     *
     * @param name name of {@link Unit}
     */
    @JsonCreator
    public Unit(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Constructor with an {@link OntologyAnnotation} for the {@link Unit}
     *
     * @param ontologyAnnotation the {@link OntologyAnnotation} of the {@link Unit}
     */
    public Unit(OntologyAnnotation ontologyAnnotation) {
        this.name = ontologyAnnotation.getTerm();
        this.ontology = ontologyAnnotation;
    }

    /**
     * Get the name of {@link Unit}
     *
     * @return name of {@link Unit}
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of {@link Unit}
     *
     * @param name name of {@link Unit}
     */
    public void setName(String name) {
        if (name == null) {
            LoggerUtil.logger.error("The name of unit can't be null!");
            return;
        }
        this.name = name;
    }

    /**
     * Get the {@link Ontology} of the {@link Unit}
     *
     * @return ontology of {@link Unit}
     */
    public OntologyAnnotation getOntology() {
        return this.ontology;
    }

    /**
     * Set the {@link Ontology} of the {@link Unit}
     *
     * @param ontology {@link Ontology} of the {@link Unit}
     */
    public void setOntology(OntologyAnnotation ontology) {
        if (ontology != null && this.name != null && !ontology.getTerm().equals(this.name)) {
            LoggerUtil.logger.error("The OntologyAnnotation '" + ontology.getTerm()
                    + "' of Unit do not fit to the already set value '" + this.name + "'");
            return;
        }
        this.ontology = ontology;
    }

}