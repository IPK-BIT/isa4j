/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.net.URL;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class to represent an ontology
 *
 * @author liufe, arendd
 */
public class Ontology {

    /**
     * description of the {@link Ontology}
     */
    private String description;

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
     * Full constructor for an {@link Ontology}.
     *
     * @param name        name of {@link Ontology}
     * @param url         URI or path of the {@link Ontology}
     * @param version     version of the {@link Ontology}
     * @param description description of the {@link Ontology}
     */
    public Ontology(String name, URL url, String version, String description) {
        this.setName(name);
        this.setURL(url);
        this.setVersion(version);
        this.setDescription(description);
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
     * get the name of the {@link Ontology}
     *
     * @return name of the {@link Ontology}
     */
    public String getName() {
        return name;
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
     * get the version of the {@link Ontology}
     *
     * @return the version of the {@link Ontology}
     */
    public String getVersion() {
        return version;
    }

    /**
     * set the description of the {@link Ontology}
     *
     * @param description description of the {@link Ontology}
     */
    public void setDescription(String description) {
        this.description = StringUtil.sanitize(description);
    }

    /**
     * set the name of the {@link Ontology}
     *
     * @param name name of the {@link Ontology}
     */
    public void setName(String name) {
        this.name = StringUtil.sanitize(Objects.requireNonNull(name, "Ontology Name cannot be null"));
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
     * set the version of the {@link Ontology}
     *
     * @param version version of the {@link Ontology}
     */
    public void setVersion(String version) {
        this.version = StringUtil.sanitize(version);
    }

}
