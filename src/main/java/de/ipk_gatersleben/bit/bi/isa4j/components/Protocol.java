/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class to represent a protocol/process for a {@link Study}, which contains
 * several {@link Parameter}
 *
 * @author liufe, arendd
 */
public class Protocol implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	private List<ProtocolComponent> components = new ArrayList<ProtocolComponent>();

    /**
     * A description of the {@link Protocol}
     */
    private String description;

    /**
     * The name of the {@link Protocol}
     */
    private String name;

    /**
     * The {@link Parameter} list of this {@link Protocol}
     */
    private List<ProtocolParameter> parameters = new ArrayList<ProtocolParameter>(3);

    /**
     * The {@link OntologyAnnotation} to describe the type of the {@link Protocol}
     */
    private OntologyAnnotation type;

    /**
     * A URI to linked with the {@link Protocol}
     */
    private String URI;

    /**
     * The used version of the {@link Protocol}.
     */
    private String version;
    
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
     * @param type type of the {@link Protocol}
     */
    public Protocol(String name, OntologyAnnotation typeOntology) {
        this.name = name;
        this.type = typeOntology;
    }

	public void addComponent(ProtocolComponent component) {
		Objects.requireNonNull(component);
		this.components.add(component);
	}
	
	/**
     * Add a {@link Parameter} to {@link Protocol}
     *
     * @param parameter parameter, that you want to add
     */
    public void addParameter(ProtocolParameter parameter) {
    	Objects.requireNonNull(parameter);
    	if(this.parameters.stream().map(ProtocolParameter::getName).anyMatch(parameter.getName()::equals))
    		throw new RedundantItemException("Parameter not unique: " + parameter.getName());

        this.parameters.add(parameter);
    }

	public CommentCollection comments() {
		return this.comments;
	}

    /**
	 * @return the components
	 */
	public List<ProtocolComponent> getComponents() {
		return components;
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
    public List<ProtocolParameter> getParameters() {
        return parameters;
    }

    /**
     * Get the type of the {@link Protocol}
     *
     * @return type of protocol
     */
    public OntologyAnnotation getType() {
        return type;
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
	 * @param components the components to set
	 */
	public void setComponents(List<ProtocolComponent> components) {
		components.stream().forEach(Objects::requireNonNull);
		this.components = components;
	}

    /**
     * Set the description of the {@link Protocol}
     *
     * @param description description of protocol
     */
    public void setDescription(String description) {
        this.description = StringUtil.sanitize(description);
    }

    /**
     * Set the name of the {@link Protocol}
     *
     * @param name name of protocol
     */
    public void setName(String name) {
        this.name = StringUtil.sanitize(Objects.requireNonNull(name, "Protocol Name cannot be null"));
    }

    /**
     * Set the {@link java.net.URI} of the {@link Protocol}
     *
     * @param parameters parameters of protocol
     */
    public void setParameters(List<ProtocolParameter> parameters) {
    	parameters.stream().forEach(Objects::requireNonNull);
        this.parameters = parameters;
    }

    /**
     * Set the type of the {@link Protocol}
     *
     * @param type type of protocol
     */
    public void setType(OntologyAnnotation typeOntology) {
        this.type = typeOntology;
    }

    /**
     * Get the {@link java.net.URI} of the {@link Protocol}
     *
     * @param uri of protocol
     */
    public void setURI(String uri) {
        URI = StringUtil.sanitize(uri);
    }

    /**
     * Get the version of the {@link Protocol}
     *
     * @param version version of protocol
     */
    public void setVersion(String version) {
        this.version = StringUtil.sanitize(version);
    }

}
