/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.ipk_gatersleben.bit.bi.isa.components.OntologyTerm;
import de.ipk_gatersleben.bit.bi.isa.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Props;

/**
 * Class to represent an Investigation in the ISA Context, which contains the
 * overall information about the goals and means of the experiment.
 *
 * @author liufe, arendd
 */
public class Investigation {

	/**
	 * The defined identifier for the {@link Investigation}.
	 */
	private String identifier;

	/**
	 * The list of the used ontologies for this investigation {@link Ontology}
	 */
	private ArrayList<Ontology> ontologies = new ArrayList<>();

	/**
	 * The title of the {@link Investigation}.
	 */
	private String title;

	/**
	 * A brief description of the aims of the {@link Investigation}
	 */
	private String description;

	/**
	 * The date the {@link Investigation} was submitted
	 */
	private Date submissionDate;

	/**
	 * The date the {@link Investigation} was released.
	 */
	private Date publicReleaseDate;

	/**
	 * A list of {@link Comment}s used to further describe the {@link Investigation}
	 */
	//private ArrayList<Comment> comments = new ArrayList<>();

	/**
	 * Connected {@link Publication}s of this {@link Investigation}
	 */
	//private ArrayList<Publication> publications = new ArrayList<>();

	/**
	 * People, who are associated with the {@link Investigation}
	 */
	//private ArrayList<Contact> contacts = new ArrayList<>();

	/**
	 * Studies of investigations {@link Study}
	 */
	private List<Study> studies = new ArrayList<>();

	/**
	 * Constructor, every Investigation should have a identifier.
	 *
	 * @param identifier identifier of investigation
	 */
	public Investigation(String identifier) {
		this.identifier = identifier;
	}

	
	/**
	 * Add an {@link Ontology} to the {@link Investigation}
	 *
	 * @param ontology the {@link Ontology} source reference, which you want to add
	 */
	public void addOntology(Ontology ontology) {
		if (ontologies == null)
			ontologies = new ArrayList<>();
		this.ontologies.add(ontology);
	}

	/**
	 * Add a contact to the {@link Investigation}
	 *
	 * @param contact the contact of a people,which will be add
	 */
//	public void addContact(Contact contact) {
//		contacts.add(contact);
//
//	}

	/**
	 * Add a comment to list
	 *
	 * @param comment comment
	 */
//	public void addComment(Comment comment) {
//		this.comments.add(comment);
//	}

	/**
	 * Add a publication to list
	 *
	 * @param publication publication
	 */
//	public void addPublication(Publication publication) {
//		publications.add(publication);
//	}

	/**
	 * Add a study to study list The identifier and filename must be alone, 2
	 * studies can't have same identifier or filename.
	 *
	 * @param study the study of investigation, which will be add
	 */
//	public boolean addStudy(Study study) {
//		for (Study studyInInvestigation : studies) {
//			if (study.getIdentifier().equals(studyInInvestigation.getIdentifier())) {
//				LoggerUtil.logger.error("The investigation " + identifier + " can't add the study. "
//						+ "There is a study in the investigation, that its identifier is " + study.getIdentifier()
//						+ ", please change that identifier!");
//				return false;
//			}
//			if (study.getFileName().equals(studyInInvestigation.getFileName())) {
//				LoggerUtil.logger.error("The investigation " + identifier + " can't add the study. "
//						+ "There is a study in the investigation, that its fileName is " + study.getFileName()
//						+ ", please change that identifier!");
//				return false;
//			}
//		}
//		study.setInvestigation(this);
//		this.studies.add(study);
//		return true;
//	}

	/**
	 * Aet id of invesigation
	 *
	 * @return id of invesigation
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Set id of invesigation
	 *
	 * @param iD id of invesigation
	 */
	public void setIdentifier(String iD) {
		identifier = iD;
	}

	/**
	 * Get all linked {@link Ontology}
	 *
	 * @return ontologies
	 */
	public ArrayList<Ontology> getOntologies() {
		return ontologies;
	}

	/**
	 * Set linked {@link Ontology}
	 *
	 * @param ontologies the library of {@link OntologyTerm}
	 */
	public void setOntologies(ArrayList<Ontology> ontologies) {
		this.ontologies = ontologies;
	}

	/**
	 * Get title
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 *
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get Description
	 *
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set Description
	 *
	 * @param description Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get SubmissionDate
	 *
	 * @return SubmissionDate
	 */
	public Date getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * Set SubmissionDate
	 *
	 * @param submissionDate SubmissionDate
	 */
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	/**
	 * Get PublicReleaseDate
	 *
	 * @return PublicReleaseDate
	 */
	public Date getPublicReleaseDate() {
		return publicReleaseDate;
	}

	/**
	 * Set PublicReleaseDate
	 *
	 * @param publicReleaseDate PublicReleaseDate
	 */
	public void setPublicReleaseDate(Date publicReleaseDate) {
		this.publicReleaseDate = publicReleaseDate;
	}

	/**
	 * Get comments of investigation
	 *
	 * @return comments of investigation
	 */
//	public ArrayList<Comment> getComments() {
//		return comments;
//	}

	/**
	 * Set comments of investigation
	 *
	 * @param comments comment of investigation
	 */
//	public void setComments(ArrayList<Comment> comments) {
//		this.comments = comments;
//	}

	/**
	 * Get Publication of investigation
	 *
	 * @return Publication of investigation
	 */
//	public ArrayList<Publication> getPublications() {
//		return publications;
//	}

	/**
	 * Set Publication of investigation
	 *
	 * @param publications Publication of investigation
	 */
//	public void setPublications(ArrayList<Publication> publications) {
//		this.publications = publications;
//	}

	/**
	 * Get contact of investigation
	 *
	 * @return contact of investigation
	 */
//	public ArrayList<Contact> getContacts() {
//		return contacts;
//	}

	/**
	 * Set contact of investigation
	 *
	 * @param contacts contact of investigation
	 */
//	public void setContacts(ArrayList<Contact> contacts) {
//
//		this.contacts = contacts;
//	}

	/**
	 * Get studies of investigation
	 *
	 * @return studies of investigation
	 */
	public List<Study> getStudies() {
		return studies;
	}

	/**
	 * Set studies of investigation
	 *
	 * @param studies studies of investigation
	 */
//	public void setStudies(ArrayList<Study> studies) {
//		this.studies = studies;
//		for (Study study : studies) {
//			study.setInvestigation(this);
//		}
//	}

	/**
	 * Collection of units, those by this investigation are used
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
//	public static final Map<String, OntologyTerm> unitMap = new ConcurrentHashMap<>();
	
	public boolean writeToFile(String filepath) throws IOException, NoSuchMethodException, SecurityException {
		OutputStream os = new FileOutputStream(filepath);
		OutputStreamWriter writer = new OutputStreamWriter(os, Props.DEFAULT_CHARSET);

		
		// ONTOLOGY SOURCE REFERENCE
		writer.write(InvestigationAttribute.ONTOLOGY_SOURCE_REFERENCE.toString());
		
			// Term Source Name
			writer.write(InvestigationAttribute.TERM_SOURCE_NAME.toString());
			writer.write(ontologies.stream()
					.map(obj -> obj.getName())
					.collect(Collectors.joining(Symbol.TAB.toString())));
			writer.write(Symbol.ENTER.toString());
			
			// Term Source File	
			writer.write(InvestigationAttribute.TERM_SOURCE_FILE.toString());
			writer.write(ontologies.stream()
					// In the case of a ternary operator we have to wrap it in a function return because otherwise
					// Eclipse complains that it can't infer the type... Annoying Java Things (TM)
					.map(obj -> { return obj.getURL() == null ? Symbol.EMPTY.toString() : obj.getURL().toString(); })
					.collect(Collectors.joining(Symbol.TAB.toString())));
			writer.write(Symbol.ENTER.toString());
			
			// Term Source Version
			writer.write(InvestigationAttribute.TERM_SOURCE_VERSION.toString());
			writer.write(ontologies.stream()
					.map(obj -> { return obj.getVersion() == null ? Symbol.EMPTY.toString() : obj.getVersion().toString(); })
					.collect(Collectors.joining(Symbol.TAB.toString())));
			writer.write(Symbol.ENTER.toString());
			
			// Term Source Description
			writer.write(InvestigationAttribute.TERM_SOURCE_DESCRIPTION.toString());
			writer.write(ontologies.stream()
					.map(obj -> { return obj.getDescription() == null ? Symbol.EMPTY.toString() : obj.getDescription().toString(); })
					.collect(Collectors.joining(Symbol.TAB.toString())));
			writer.write(Symbol.ENTER.toString());
		
			// INVESTIGATION
			
				// Identifier, Title, Description
				writer.write((InvestigationAttribute.INVESTIGATION.toString()
						+ InvestigationAttribute.INVESTIGATION_IDENTIFIER
						+ (this.identifier == null ? Symbol.EMPTY : this.identifier) + Symbol.ENTER
						+ InvestigationAttribute.INVESTIGATION_TITLE + (title == null ? Symbol.EMPTY : title)
						+ Symbol.ENTER + InvestigationAttribute.INVESTIGATION_DESCRIPTION
						+ (this.description == null ? Symbol.EMPTY : this.description) + Symbol.ENTER));

				// Submission Date
				writer.write(InvestigationAttribute.INVESTIGATION_SUBMISSION_DATE.toString());
				if(this.submissionDate != null)
					writer.write(this.submissionDate.toString());
				writer.write(Symbol.ENTER.toString());
				
				// Public Release Date
				writer.write(InvestigationAttribute.INVESTIGATION_PUBLIC_RELEASE_DATE.toString());
				if(this.publicReleaseDate != null)
					writer.write(this.publicReleaseDate.toString());
				writer.write(Symbol.ENTER.toString());

	    // This is how I'm going to go through all the Attributes (what he tried to do with getAllValueOfClassAttribute)
		Map<Integer,String> map = Map.of(1, "A", 2, "B", 3, "C"); // Investigation Attribute -> Method Name.
			
		Method method = Ontology.class.getMethod("getName");
		System.out.println();
		
		writer.close();
		return true;
	}

}
