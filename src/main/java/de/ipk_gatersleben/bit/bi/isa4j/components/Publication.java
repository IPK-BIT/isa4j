/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa4j.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.Study;

/**
 * Class representing a {@link Publication} connected to a {@link Investigation}
 * or {@link Study}
 *
 * @author liufe, arendd
 */
public class Publication implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	public CommentCollection comments() {
		return this.comments;
	}

    /**
     * The PubMed ID of this {@link Publication}
     */
    private String pubmedID;

    /**
     * DOI (Digital Object Identifier) for this {@link Publication}
     */
    private String doi;

    /**
     * The list of authors who contributed to this {@link Publication}
     */
    private String authors;

    /**
     * The title of the {@link Publication}
     */
    private String title;

    /**
     * The status of this {@link Publication}, e.g. Published, Submitted, etc.
     */
    private OntologyAnnotation status;

    /**
     * Get the list of authors of this {@link Publication}
     *
     * @return authors the {@link List} of the authors of this {@link Publication}
     */
    public String getAuthors() {
        return this.authors;
    }

    /**
     * Get DOI of the {@link Publication}
     *
     * @return DOI of the {@link Publication}
     */
    public String getDOI() {
        return this.doi;
    }

    /**
     * Get the ID of the {@link Publication}
     *
     * @return id of the {@link Publication}
     */
    public String getPubmedID() {
        return this.pubmedID;
    }

    /**
     * Get the status of the {@link Publication}
     *
     * @return status of the {@link Publication}
     */
    public OntologyAnnotation getStatus() {
        return this.status;
    }

    /**
     * Get title of {@link Publication}
     *
     * @return title of publication
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the list of authors of this {@link Publication}
     *
     * @param authors authors' name of publication
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * Set DOI of the {@link Publication}
     *
     * @param doi DOI of publication
     */
    public void setDOI(String doi) {
        this.doi = doi;
    }

    /**
     * Set the pubmedID of the {@link Publication}
     *
     * @param pubmedID id of the publication
     */
    public void setPubmedID(String pubmedID) {
        this.pubmedID = pubmedID;
    }

    /**
     * Set status of the {@link Publication}
     *
     * @param status status of publication
     */
    public void setStatus(OntologyAnnotation statusOntology) {
        this.status = statusOntology;
    }

    /**
     * Set title of the {@link Publication}
     *
     * @param title title of {@link Publication}
     */
    public void setTitle(String title) {
        this.title = title;
    }

}