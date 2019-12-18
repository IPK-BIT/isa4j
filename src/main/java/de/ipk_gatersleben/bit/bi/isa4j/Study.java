/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j;

import de.ipk_gatersleben.bit.bi.isa.Investigation;
import de.ipk_gatersleben.bit.bi.isa.components.*;
import de.ipk_gatersleben.bit.bi.isa.constants.PartOfEntity;
import de.ipk_gatersleben.bit.bi.isa.constants.Props;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa.io.ThreadPool;
import de.ipk_gatersleben.bit.bi.isa.io.Writer;
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Person;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Class to represent a study in context of the ISA hierarchy. It is used to
 * describe the studied objects and belong to a {@link Investigation}
 *
 * @author liufe, arendd
 */
public class Study extends Commentable {

	/**
	 * A user defined identifier for the study.
	 */
	private String identifier;

	/**
	 * The name of the study sample file linked to this Study.
	 */
	private String fileName = "s_study.txt";

	/**
	 * The title for the Study.
	 */
	private String title;

	/**
	 * The brief description of the study aims.
	 */
	private String description;

	/**
	 * The date the study was submitted.
	 */
	private Date submissionDate;

	/**
	 * The date the study was released publicly.
	 */
	private Date publicReleaseDate;

	/**
	 * List of {@link DesignDescriptor}s to describe the {@link Study}
	 */
	private ArrayList<OntologyAnnotation> designDescriptors = new ArrayList<OntologyAnnotation>();

	/**
	 * {@link Publication} of study
	 */
	private ArrayList<Publication> publications = new ArrayList<>(2);

	/**
	 * {@link Assay} of Study
	 */
	private List<Assay> assays = new ArrayList<>(2);

	/**
	 * People, who take part to the Investigation {@link Person}
	 */
	private ArrayList<Person> contacts = new ArrayList<>(2);

	/**
	 * Data in study file
	 */
	private List<RowOfStudy> rowsOfStudy = new Vector<>(1000, 1000);


	/**
	 * The investigation, that has this study
	 */
	private Investigation investigation;

	/**
	 * The list of {@link Characteristic} columns
	 */
	private List<Characteristic> listOfCharacteristics = new ArrayList<>();


	/**
	 * The list of {@link Protocol} columns
	 */
	private List<Protocol> listOfProtocols = new ArrayList<>();

	/**
	 * The map of {@link Parameter} within {@link Protocol} columns
	 */
	private Map<String, List<Parameter>> mapOfParametersWithinProtocols = new HashMap<>();

	/**
	 * The map of characteristic within protocol columns
	 */
	private Map<String, List<Characteristic>> mapOfCharacteristicsWithinProtocols = new HashMap<>();

	/**
	 * The list of {@link Factor} columns
	 */
	private List<Factor> listOfFactors = new ArrayList<>();

	/**
	 * The list of {@link Characteristic} columns for a sample
	 */
	private List<Characteristic> listCharacteristicsForSample = new ArrayList<>();

	/**
	 * Constructor, give the identifier of study, filename is same with identifier
	 *
	 * @param identifier
	 */
	public Study(String identifier) {
		this.identifier = identifier;
		this.fileName = "s_" + identifier + ".txt";
	}

	/**
	 * Constructor, give the identifier and filename
	 *
	 * @param identifier
	 * @param fileName
	 */
	public Study(String identifier, String fileName) {
		this.identifier = identifier;
		this.fileName = fileName;
	}

	/**
	 * Add assay to assay list
	 *
	 * @param assay assay, that you want to add
	 */
	public boolean addAssay(Assay assay) {
		for (Assay assayInStudy : assays) {
			if (assay.getFileName().equals(assayInStudy.getFileName())) {
//				LoggerUtil.logger.error("The Study " + identifier + " can't add the assay. "
//						+ "There is a assay in the study, that its fileName is " + assay.getFileName()
//						+ ", please change that identifier!");
				return false;
			}
		}
		assay.setStudy(this);
		this.assays.add(assay);
		return true;
	}

	/**
	 * Add new {@link Person} to the list
	 *
	 * @param person new {@link Person} to add
	 */
	public void addContact(Person person) {

		if (person == null) {
//			LoggerUtil.logger.error("Can not add an empty Person");
		}

		if (contacts == null) {
			contacts = new ArrayList<>(2);
		}
		this.contacts.add(person);
	}

	/**
	 * Add a new {@link DesignDescriptor} to the list
	 *
	 * @param designDescriptor new {@link DesignDescriptor} to add
	 */
	public boolean addDesignDescriptor(OntologyAnnotation designDescriptor) {
		if (designDescriptor == null) {
//			LoggerUtil.logger.error("Can not add an empty DesignDescriptor");
			return false;
		}
		if (!designDescriptors.contains(designDescriptor)) {
			this.designDescriptors.add(designDescriptor);
			return true;
		} else {
//			LoggerUtil.logger
//					.error("The Study contains already a DesignDescriptor '" + designDescriptor.getType() + "'");
			return false;
		}
	}

	/**
	 * Add new publication associated with this study
	 *
	 * @param publication the new publication to add
	 */
	public void addPublication(Publication publication) {
		if (publications == null) {
			publications = new ArrayList<>(2);
		}
		this.publications.add(publication);
	}

	/**
	 * Add new row to this {@link Study}
	 *
	 * @param rowOfStudy new row to add
	 */
	public void addRowOfStudy(RowOfStudy rowOfStudy) {
		this.rowsOfStudy.add(rowOfStudy);
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
	public ArrayList<Person> getContacts() {
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
	public ArrayList<OntologyAnnotation> getDesignDescriptors() {
		return designDescriptors;
	}

	/**
	 * Get filename of study
	 *
	 * @return filename of study
	 */
	public String getFileName() {
		return fileName;
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
	 * @return the investigation, that this study belongs to
	 */
	public Investigation getInvestigation() {
		return investigation;
	}

	/**
	 * Get publications of study
	 *
	 * @return publications of study
	 */
	public ArrayList<Publication> getPublications() {
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
	 * Get data of study
	 *
	 * @return data of study
	 */
	public List<RowOfStudy> getRowsOfStudy() {
		return rowsOfStudy;
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
	public void setAssays(ArrayList<Assay> assays) {
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
	public void setContacts(ArrayList<Person> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Set description of study
	 *
	 * @param description description of study
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set descriptor of design for study
	 *
	 * @param desigenDescriptors descriptor of design for study
	 */
	public void setDesignDescriptors(ArrayList<OntologyAnnotation> desigenDescriptors) {
		// TODO don't allow null
		this.designDescriptors = desigenDescriptors;
	}

	/**
	 * Set {@link Investigation}, that this study belongs to But this function can't
	 * be used by user
	 *
	 * @param investigation the investigation, that study belongs to
	 */
	protected void setInvestigation(Investigation investigation) {
		this.investigation = investigation;
	}

	/**
	 * Set publications of study
	 *
	 * @param publications publications of study
	 */
	public void setPublications(ArrayList<Publication> publications) {
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
	 * Set data of study
	 *
	 * @param data data of study
	 */
	public void setRowsOfStudy(List<RowOfStudy> data) {
		this.rowsOfStudy = data;
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
		this.title = title;
	}
}
