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
import de.ipk_gatersleben.bit.bi.isa.components.Characteristic;
import de.ipk_gatersleben.bit.bi.isa.components.Parameter;
import de.ipk_gatersleben.bit.bi.isa.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.Study;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to represent a protocol/process for a {@link Study}, which contains
 * several {@link Parameter}
 *
 * @author liufe, arendd
 */
public class Protocol extends Commentable {

    /**
     * Mandatory attributes of a {@link Protocol} for the {@link Investigation} file
     */
    public enum Attributes {
        NAME, TYPE, TYPE_TERM_ACCESSION_NUMBER, TYPE_TERM_SOURCE_REF, DESCRIPTION, URI, VERSION, PARAMETERS_NAME,
        PARAMETERS_NAME_TERM_ACCESSION_NUMBER, PARAMETERS_NAME_TERM_SOURCE_REF, COMPONENTS_NAME, COMPONENTS_TYPE,
        COMPONENTS_TYPE_TERM_ACCESSION_NUMBER, COMPONENTS_TYPE_TERM_SOURCE_REF,
    }

    /**
     * The name of the {@link Protocol}
     */
    private String name;

    /**
     * The {@link OntologyAnnotation} to describe the type of the {@link Protocol}
     */
    private OntologyAnnotation typeOntology;

    /**
     * A description of the {@link Protocol}
     */
    private String description;

    /**
     * A URI to linked with the {@link Protocol}
     */
    private String URI;

    /**
     * The used version of the {@link Protocol}.
     */
    private String version;

    /**
     * The {@link Parameter} list of this {@link Protocol}
     */
    private ArrayList<Parameter> parameters = new ArrayList<Parameter>(3);

    /**
     * The {@link Characteristic} list of this {@link Protocol}
     */
    private List<Characteristic> characteristics = new ArrayList<>(3);

    private String extractName;

    /**
     * labeled extract name
     */
    private String labeledExtractName;

    private int position = -1;

    /**
     * Constructor the name of the {@link Protocol}
     *
     * @param name name of the {@link Protocol}
     */
    public Protocol(String name) {
        this.name = name;
    }

    /**
     * @param name         name of the {@link Protocol}
     * @param typeOntology type of the {@link Protocol}
     */
    public Protocol(String name, OntologyAnnotation typeOntology) {
        this.name = name;
        this.typeOntology = typeOntology;
    }

    /**
     * Constructor for name of the {@link Protocol} and type of the {@link Protocol}.
     * Give a type as String and it will make a new {@link OntologyAnnotation} with the type.
     *
     * @param name         name of the {@link Protocol}
     * @param typeOntology type of the {@link Protocol}
     */
    public Protocol(String name, String typeOntology) {
        this.name = name;
//        this.typeOntology = new OntologyAnnotation(typeOntology, Symbol.EMPTY.toString(), Symbol.EMPTY.toString());
    }

    /**
     * Add a {@link Parameter} to {@link Protocol}
     *
     * @param parameter parameter, that you want to add
     */
    public boolean addParameter(Parameter parameter) {

        if (!this.parameters.contains(parameter)) {
            this.parameters.add(parameter);
            return true;
        } else {
//            LoggerUtil.logger.error("The list of parameter in protocol '" + this.name + "' contains paramter '" + parameter.getName() + "'. " +
//                    "It can't add same characteristic again.");
            return false;
        }
    }


    /**
     * Get the description of the {@link Protocol}
     *
     * @return description of protocol
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the name of the{@link Protocol}
     *
     * @return name of protocol
     */
    public String getName() {
        return name;
    }

    /**
     * Get list of {@link Parameter} of the {@link Protocol}
     *
     * @return parameters of protocol
     */
    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Get the type of the {@link Protocol}
     *
     * @return type of protocol
     */
    public OntologyAnnotation getTypeOntology() {
        return typeOntology;
    }

    /**
     * Get the {@link java.net.URI} of the {@link Protocol}
     *
     * @return uri of protocol
     */
    public String getURI() {
        return URI;
    }

    /**
     * Get the version of the {@link Protocol}
     *
     * @return version of protocol
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the description of the {@link Protocol}
     *
     * @param description description of protocol
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the name of the {@link Protocol}
     *
     * @param name name of protocol
     */
    public void setName(String name) {
        if (name == null) {
//            LoggerUtil.logger.error("The name of protocol can't be null!");
            return;
        }
        this.name = name;
    }

    /**
     * Set the {@link java.net.URI} of the {@link Protocol}
     *
     * @param parameters parameters of protocol
     */
    public void setParameters(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Set the type of the {@link Protocol}
     *
     * @param typeOntology type of protocol
     */
    public void setTypeOntology(OntologyAnnotation typeOntology) {
        this.typeOntology = typeOntology;
    }

    /**
     * Get the {@link java.net.URI} of the {@link Protocol}
     *
     * @param uri of protocol
     */
    public void setURI(String uri) {
        URI = uri;
    }

    /**
     * Get the version of the {@link Protocol}
     *
     * @param version version of protocol
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    /**
     * get list of Characteristic
     *
     * @return list of Characteristic
     */
    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    /**
     * set list of Characteristic
     *
     * @param characteristics list of Characteristic
     */
    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    /**
     * get extract name
     *
     * @return extract name
     */
    public String getExtractName() {
        return extractName;
    }

    /**
     * set extract name
     *
     * @param extractName extract name
     */
    public void setExtractName(String extractName) {
        this.extractName = extractName;
    }

    /**
     * Add a new {@link Characteristic} to this {@link RowOfStudy}
     *
     * @param characteristic a new {@link Characteristic} to add
     */
    public boolean addCharacteristic(Characteristic characteristic) {
        if (!this.characteristics.contains(characteristic)) {
            this.characteristics.add(characteristic);
            return true;
        } else {
//            LoggerUtil.logger.error("The protocol already contains a characteristic '" + characteristic.getName()
//                    + "'");
            return false;
        }
    }

    /**
     * Get labeled extract name
     *
     * @return labeled extract name
     */
    public String getLabeledExtractName() {
        return labeledExtractName;
    }

    /**
     * Set labeled extract name
     *
     * @param labeledExtractName labeled extract name
     */
    public void setLabeledExtractName(String labeledExtractName) {
        this.labeledExtractName = labeledExtractName;
    }
}
