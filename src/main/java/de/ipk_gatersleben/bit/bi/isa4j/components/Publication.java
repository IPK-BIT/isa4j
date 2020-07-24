/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.List;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class representing a {@link Publication} connected to a {@link Investigation}
 * or {@link Study}
 *
 * @author liufe, arendd, psaroudakis
 */
public class Publication implements Commentable {
	
	/**
     * The list of authors who contributed to this {@link Publication}
     */
    private String authors;
	
	private CommentCollection comments = new CommentCollection();
	
	/**
     * DOI (Digital Object Identifier) for this {@link Publication}
     */
    private String doi;

    /**
     * The PubMed ID of this {@link Publication}
     */
    private String pubmedID;

    /**
     * The status of this {@link Publication}, e.g. Published, Submitted, etc.
     */
    private OntologyAnnotation status;

    /**
     * The title of the {@link Publication}
     */
    private String title;

    public Publication(String title, String authors) {
		this.setTitle(title);
		this.setAuthors(authors);
	}
    
    public Publication(String title, String authors, String doi, String pubmedID, OntologyAnnotation status) {
    	this(title, authors);
    	this.setDOI(doi);
    	this.setPubmedID(pubmedID);
    	this.setStatus(status);
    }

    public CommentCollection comments() {
		return this.comments;
	}

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
        this.authors = StringUtil.sanitize(authors);
    }

    /**
     * Set DOI of the {@link Publication}
     *
     * @param doi DOI of publication
     */
    public void setDOI(String doi) {
        this.doi = StringUtil.sanitize(doi);
    }

    /**
     * Set the pubmedID of the {@link Publication}
     *
     * @param pubmedID id of the publication
     */
    public void setPubmedID(String pubmedID) {
        this.pubmedID = StringUtil.sanitize(pubmedID);
    }

    /**
     * Set status of the {@link Publication}
     *
     * @param status status of publication
     */
    public void setStatus(OntologyAnnotation status) {
        this.status = status;
    }

    /**
     * Set title of the {@link Publication}
     *
     * @param title title of {@link Publication}
     */
    public void setTitle(String title) {
        this.title = StringUtil.sanitize(Objects.requireNonNull(title, "Publication title cannot be null"));
    }
    
	@Override
	public String toString() {
		return "<Publication> '" + this.title + "' (" + this.authors + ")";
	}

}