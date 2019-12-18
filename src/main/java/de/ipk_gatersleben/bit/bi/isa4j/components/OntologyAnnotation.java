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
 * Class representing a {@link Ontology} based term. Every {@link OntologyAnnotation} has
 * three Attributes: Name, AccessionNumber and SourceREF
 *
 * @author liufe, arendd
 */
public class OntologyAnnotation extends Commentable {

    /**
     * term of Ontology term
     */
    private String term;

    /**
     * accession number of ontology term
     */
    private String termAccession;

    /**
     * sourceREF of ontology term
     */
    private Ontology sourceREF;

    /**
     * members for Class OntologyAnnotation,
     * Constants, every item is a member of OntologyAnnotation
     *
     * @author liufe
     */
    public enum OntologyTermAttributes {
        NAME, SOURCE_REF, TERM_ACCESSION_NUMBER;
    }
    
    public OntologyAnnotation(String term, String termAccessionNumber, Ontology sourceREF) {
    	this.term = term;
    	this.termAccession = termAccessionNumber;
    	this.sourceREF = sourceREF;
    }
    
    public OntologyAnnotation(String term) {
    	this(term,  null, null);
    }
   

    /**
     * get term of ontology term
     *
     * @return term of ontology term
     */
    public String getTerm() {
        return term;
    }

    /**
     * set term of ontology term
     *
     * @param term term of ontology term
     */
    public void setTerm(String term) {
        if (term == null) {
//            LoggerUtil.logger.error("The term of ontologyTern can't be null!");
            return;
        }
        this.term = term;
    }

    /**
     * get accession number of ontology term
     *
     * @return accession number of ontology term
     */
    public String getTermAccession() {
        return termAccession;
    }

    /**
     * set accession number of ontology term
     *
     * @param termAccession accession number of ontology term
     */
    public void setTermAccession(String termAccession) {
        this.termAccession = termAccession;
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
     * @param sourceREF source REF
     */
    public void setSourceREF(Ontology sourceREF) {
        this.sourceREF = sourceREF;
    }

}
