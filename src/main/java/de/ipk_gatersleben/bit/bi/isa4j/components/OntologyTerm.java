/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

/**
 * Class representing a {@link Ontology} based term. Every {@link OntologyTerm} has
 * three Attributes: Name, AccessionNumber and SourceREF
 *
 * @author liufe, arendd
 */
public class OntologyTerm {

    /**
     * name of Ontology term
     */
    private String name;

    /**
     * accession number of ontology term
     */
    private String termAccessionNumber;

    /**
     * sourceREF of ontology term
     */
    private Ontology sourceREF;

    /**
     * members for Class OntologyTerm,
     * Constants, every item is a member of OntologyTerm
     *
     * @author liufe
     */
    public enum OntologyTermAttributes {
        NAME, SOURCE_REF, TERM_ACCESSION_NUMBER;
    }

    /**
     * get name of ontology term
     *
     * @return name of ontology term
     */
    public String getName() {
        return name;
    }

    /**
     * set name of ontology term
     *
     * @param name name of ontology term
     */
    public void setName(String name) {
        if (name == null) {
//            LoggerUtil.logger.error("The name of ontologyTern can't be null!");
            return;
        }
        this.name = name;
    }

    /**
     * get accession number of ontology term
     *
     * @return accession number of ontology term
     */
    public String getTermAccessionNumber() {
        return termAccessionNumber;
    }

    /**
     * set accession number of ontology term
     *
     * @param termAccessionNumber accession number of ontology term
     */
    public void setTermAccessionNumber(String termAccessionNumber) {
        this.termAccessionNumber = termAccessionNumber;
    }

    /**
     * get sourceREF
     *
     * @return sourceREF
     */
    public Ontology getSourceREF() {
        return sourceREF;
    }

    /**
     * set source REF
     *
     * @param sourceREF sourve REF
     */
    public void setSourceREF(Ontology sourceREF) {
        this.sourceREF = sourceREF;
    }

}
