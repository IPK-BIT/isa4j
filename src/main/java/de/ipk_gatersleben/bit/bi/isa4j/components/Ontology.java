/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.net.URL;

/**
 * Class to represent an ontology
 *
 * @author liufe, arendd
 */
public class Ontology {

    /**
     * name of the {@link Ontology}
     */
    private String name;

    /**
     * URI or path of the {@link Ontology}
     */
    private URL url;

    /**
     * version of the {@link Ontology}
     */
    private String version;

    /**
     * description of the {@link Ontology}
     */
    private String description;


    /**
     * Full constructor for an {@link Ontology}.
     *
     * @param name        name of {@link Ontology}
     * @param url         URI or path of the {@link Ontology}
     * @param version     version of the {@link Ontology}
     * @param description description of the {@link Ontology}
     */
    public Ontology(String name, URL url, String version, String description) {
        this.name = name;
        this.url = url;
        this.version = version;
        this.description = description;
    }

    /**
     * get the name of the {@link Ontology}
     *
     * @return name of the {@link Ontology}
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of the {@link Ontology}
     *
     * @param name name of the {@link Ontology}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the url or path of the {@link Ontology}
     *
     * @return url of the {@link Ontology}
     */
    public URL getURL() {
        return url;
    }

    /**
     * set the url or path of the {@link Ontology}
     *
     * @param url the url or path of file
     */
    public void setURL(URL url) {
        this.url = url;
    }

    /**
     * get the version of the {@link Ontology}
     *
     * @return the version of the {@link Ontology}
     */
    public String getVersion() {
        return version;
    }

    /**
     * set the version of the {@link Ontology}
     *
     * @param version version of the {@link Ontology}
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * get the description of the {@link Ontology}
     *
     * @return description of the {@link Ontology}
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description of the {@link Ontology}
     *
     * @param description description of the {@link Ontology}
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
