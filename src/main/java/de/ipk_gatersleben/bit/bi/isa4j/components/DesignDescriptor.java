/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Class, that record, how the study was designed.
 */
public class DesignDescriptor {

    /**
     * Type of design
     */
    private String type;

    /**
     * ontology term of type
     */
    private OntologyTerm typeOntologyTerm;

    /**
     * Comments of design
     */
    private List<Comment> comments = new ArrayList<Comment>();

    /**
     * Constructor, only has type of design
     *
     * @param type type of design
     */
    public DesignDescriptor(String type) {
        this.type = type;
    }

    /**
     * attributes for Class DesignDescriptor,
     */
    public enum DesignDescriptorAttributes {

        TYPE, SOURCE_REF, TERM_ACCESSION_NUMBER;
    }

    /**
     * Constructor, the design has ontologyTerm
     *
     * @param ontologyTerm ontology term of design
     */
    public DesignDescriptor(OntologyTerm ontologyTerm) {
        this.typeOntologyTerm = ontologyTerm;
        this.type = ontologyTerm.getName();
    }

    /**
     * add the comment to design
     *
     * @param comment comment for design
     */
    public boolean addComment(Comment comment) {
        if (comment == null) {
//            LoggerUtil.logger.error("Null can't add to commentlist of design descriptor!");
            return true;
        }
        if (comments == null) {
            comments = new ArrayList<>(2);
        }
        if (!comments.contains(comment)) {
            this.comments.add(comment);
            return true;
        } else {
//            LoggerUtil.logger.error("The commentlist of design contains already a comment '" + comment.getType() + "'");
            return false;
        }
    }

    /**
     * get type of design
     *
     * @return type of design
     */
    public String getType() {
        return type;
    }

    /**
     * set type of design
     *
     * @param type type of design
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get ontology Term of type
     *
     * @return ontology Term of type
     */
    public OntologyTerm getTypeOntologyTerm() {
        return typeOntologyTerm;
    }

    /**
     * set ontology Term of type
     *
     * @param typeOntologyTerm ontology Term of type
     */
    public void setTypeOntologyTerm(OntologyTerm typeOntologyTerm) {
        this.typeOntologyTerm = typeOntologyTerm;
    }

    /**
     * get comments of design
     *
     * @return comments of design
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * set comments of design
     *
     * @param comments comments of design
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
