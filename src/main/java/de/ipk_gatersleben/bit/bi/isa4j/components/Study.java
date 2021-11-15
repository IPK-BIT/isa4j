/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class to represent a study in context of the ISA hierarchy. It is used to
 * describe the studied objects and belong to a {@link Investigation}
 * 
 * @author psaroudakis, arendd
 */

public class Study extends WideTableFile implements Commentable {

	/**
	 * {@link Assay} of Study
	 */
	private List<Assay> assays = new ArrayList<>(2);

	/**
	 * People, who take part to the Investigation {@link Person}
	 */
	private List<Person> contacts = new ArrayList<>(2);

	/**
	 * The brief description of the study aims.
	 */
	private String description;

	/**
	 * List of {@link DesignDescriptor}s to describe the {@link Study}
	 */
	private List<OntologyAnnotation> designDescriptors = new ArrayList<OntologyAnnotation>();

	/**
	 * The list of {@link Factor} columns
	 */
	private List<Factor> factors = new ArrayList<>();

	/**
	 * A user defined identifier for the study.
	 */
	private String identifier;

	/**
	 * The investigation that has this study
	 */
	private Investigation investigation;
	
	private final Logger logger = LoggerFactory.getLogger(Study.class);

	/**
	 * The list of {@link Protocol} columns
	 */
	private List<Protocol> protocols = new ArrayList<>();

	/**
	 * The list of {@link Publication} associated with the study
	 */
	private List<Publication> publications = new ArrayList<>(2);

	/**
	 * The date the study was released publicly.
	 */
	private Date publicReleaseDate;

	/**
	 * The date the study was submitted.
	 */
	private Date submissionDate;

	/**
	 * The title for the Study.
	 */
	private String title;

	/**
	 * Constructor, give the identifier of study, filename is same with identifier
	 *
	 * @param identifier the id of the {@link Study}
	 */
	public Study(String identifier) {
		super("s_" + identifier + ".txt");
		this.setIdentifier(identifier);
	}

	/**
	 * Constructor, give the identifier and filename
	 *
	 * @param identifier the identifier of the {@link Study}
	 * @param fileName   the name of the {@link Study} which will be later linked in
	 *                   the {@link Investigation} file and used when writing the
	 *                   {@link Study} into a file
	 */
	public Study(String identifier, String fileName) {
		super(fileName);
		this.setIdentifier(identifier);
	}

	/**
	 * Add assay to assay list
	 *
	 * @param assay assay, that you want to add
	 */
	public void addAssay(Assay assay) {
		Objects.requireNonNull(assay);
		if (this.assays.stream().map(Assay::getFileName).anyMatch(assay.getFileName()::equals))
			throw new RedundantItemException("Assay Filename not unique: " + assay.getFileName());

		assay.setStudy(this);
		this.assays.add(assay);
	}

	/**
	 * Add new {@link Person} to the list
	 *
	 * @param person new {@link Person} to add
	 */
	public void addContact(Person person) {
		Objects.requireNonNull(person);
		this.contacts.add(person);
	}

	/**
	 * Add a new design descriptior to the list
	 *
	 * @param designDescriptor new design descriptor to add
	 */
	public void addDesignDescriptor(OntologyAnnotation designDescriptor) {
		Objects.requireNonNull(designDescriptor);
		this.designDescriptors.add(designDescriptor);
	}

	public void addFactor(Factor factor) {
		Objects.requireNonNull(factor);
		this.factors.add(factor);
	}

	public void addProtocol(Protocol protocol) {
		Objects.requireNonNull(protocol);
		this.protocols.add(protocol);
	}

	/**
	 * Add new publication associated with this study
	 *
	 * @param publication the new publication to add
	 */
	public void addPublication(Publication publication) {
		Objects.requireNonNull(publication);
		this.publications.add(publication);
	}

	/**
	 * Get associated assays of this study
	 *
	 * @return assays of this study
	 */
	public List<Assay> getAssays() {
		return assays;
	}

	/**
	 * Get contacts of study
	 *
	 * @return contacts of study
	 */
	public List<Person> getContacts() {
		return contacts;
	}

	/**
	 * Get description of study
	 *
	 * @return description of study
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get descriptor of design for study
	 *
	 * @return descriptor of design for study
	 */
	public List<OntologyAnnotation> getDesignDescriptors() {
		return designDescriptors;
	}

	/**
	 * @return the factors
	 */
	public List<Factor> getFactors() {
		return factors;
	}

	/**
	 * Get id of study
	 *
	 * @return id of study
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Get {@link Investigation}, that this study belongs to
	 *
	 * @return the investigation that this study belongs to
	 */
	public Investigation getInvestigation() {
		return investigation;
	}

	/**
	 * @return the protocols
	 */
	public List<Protocol> getProtocols() {
		return protocols;
	}

	/**
	 * Get publications of study
	 *
	 * @return publications of study
	 */
	public List<Publication> getPublications() {
		return publications;
	}

	/**
	 * Get public releaseDate of study
	 *
	 * @return public releaseDate of study
	 */
	public Date getPublicReleaseDate() {
		return publicReleaseDate;
	}

	/**
	 * Get submissionDate of study
	 *
	 * @return submissionDate of study
	 */
	public Date getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * Get title of study
	 *
	 * @return title of study
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set assays of study
	 *
	 * @param assays assays of study
	 */
	public void setAssays(List<Assay> assays) {
		assays.stream().forEach(Objects::requireNonNull);
		this.assays = assays;
		for (Assay assay : assays) {
			assay.setStudy(this);
		}
	}

	/**
	 * Set contacts of study
	 *
	 * @param contacts contacts of study
	 */
	public void setContacts(List<Person> contacts) {
		contacts.stream().forEach(Objects::requireNonNull);
		this.contacts = contacts;
	}

	/**
	 * Set description of study
	 *
	 * @param description description of study
	 */
	public void setDescription(String description) {
		this.description = StringUtil.sanitize(description);
	}

	/**
	 * Set descriptor of design for study
	 *
	 * @param designDescriptors descriptor of design for study
	 */
	public void setDesignDescriptors(List<OntologyAnnotation> designDescriptors) {
		designDescriptors.stream().forEach(Objects::requireNonNull);
		this.designDescriptors = designDescriptors;
	}

	/**
	 * @param factors the factors to set
	 */
	public void setFactors(List<Factor> factors) {
		this.factors = factors;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = StringUtil.sanitize(Objects.requireNonNull(identifier, "Study identifier cannot be null"));
	}

	/**
	 * Set {@link Investigation}, that this study belongs to But this function can't
	 * be used by user
	 *
	 * @param investigation the investigation that study belongs to
	 */
	protected void setInvestigation(Investigation investigation) {
		if(investigation != null && this.investigation != null) {
			logger.warn("Attaching " + this.toString() + " to " + investigation.toString() + ", was previously already attached " +
						"to " + this.investigation.toString(), this);
		}

		this.investigation = investigation;
	}

	/**
	 * @param protocols the protocols to set
	 */
	public void setProtocols(List<Protocol> protocols) {
		protocols.stream().forEach(Objects::requireNonNull);
		this.protocols = protocols;
	}

	/**
	 * Set publications of study
	 *
	 * @param publications publications of study
	 */
	public void setPublications(ArrayList<Publication> publications) {
		publications.stream().forEach(Objects::requireNonNull);
		this.publications = publications;
	}

	/**
	 * @param publications the publications to set
	 */
	public void setPublications(List<Publication> publications) {
		publications.stream().forEach(Objects::requireNonNull);
		this.publications = publications;
	}

	/**
	 * Set public releaseDate of study
	 *
	 * @param publicReleaseDate public releaseDate of study
	 */
	public void setPublicReleaseDate(Date publicReleaseDate) {
		this.publicReleaseDate = publicReleaseDate;
	}

	/**
	 * Set submissionDate of study
	 *
	 * @param submissionDate submissionDate of study
	 */
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	/**
	 * Set title of study
	 *
	 * @param title title of study
	 */
	public void setTitle(String title) {
		this.title = StringUtil.sanitize(title);
	}

	@Override
	public String toString() {
		return "<Study> '" + this.getFileName() + "' (ID: '" + this.identifier + "')";
	}

}
