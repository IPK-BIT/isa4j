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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.ipk_gatersleben.bit.bi.isa.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyTerm;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Props;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

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
	private ArrayList<Comment> comments = new ArrayList<>();

	/**
	 * Connected {@link Publication}s of this {@link Investigation}
	 */
	private ArrayList<Publication> publications = new ArrayList<>();

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
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	/**
	 * Add a publication to list
	 *
	 * @param publication publication
	 */
	public void addPublication(Publication publication) {
		publications.add(publication);
	}

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
	public ArrayList<Comment> getComments() {
		return comments;
	}

	/**
	 * Set comments of investigation
	 *
	 * @param comments comment of investigation
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * Get Publication of investigation
	 *
	 * @return Publication of investigation
	 */
	public ArrayList<Publication> getPublications() {
		return publications;
	}

	/**
	 * Set Publication of investigation
	 *
	 * @param publications Publication of investigation
	 */
	public void setPublications(ArrayList<Publication> publications) {
		this.publications = publications;
	}

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
	 * This handy function creates a line of the investigation file from a list of Objects (Ontologies, Publications,
	 * Contacts etc.) and a function to execute on each element of that list (each Ontology, Publication etc.) in order
	 * to extract the content from that object that should go into the line. So, for example, if you want to create a line
	 * containing all the names of the ontologies, your ontologies would be the list that's passed, and the function to get the
	 * name of each ontology would be { ontology -> ontology.getName(); }
	 * That function is then executed for each ontology in the list and the results are joined with TABs (one column for each ontology).
	 * In the beginning, the name of the line is printed and finally the line is finished with an ENTER.
	 * @param <C> Type of Line Name (InvestigationAttribute, String...)
	 * @param <T> Type of objects to iterate over (Ontology, Contact, Publication...)
	 * @param lineName Name of the line
	 * @param list List of objects to iterate over
	 * @param lambda Function to execute on each object in the list
	 * @return The complete line
	 */
	private static <C, T> String lineFromList(C lineName, List<T> list, Function<T, String> lambda) {
		StringBuilder sb = new StringBuilder();
		sb.append(lineName.toString()); // Append the Line name
		sb.append(list.stream()
				.map(obj -> { // on each object in the list:
					String result = lambda.apply(obj); // execute the function that was passed
					return result == null ? Symbol.EMPTY.toString() : result; // if the result was null, replace with an empty string
				})
				.collect(Collectors.joining(Symbol.TAB.toString()))); // join all the strings together by TABs
		sb.append(Symbol.ENTER.toString()); // Close the line with ENTER
		return sb.toString();
	}

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
			
			
			// Build a HashMap with one entry for each line. The key is the line name and the value is the
			// function used to extract the corresponding value from an Ontology.
			LinkedHashMap<InvestigationAttribute,Function<Ontology, String>> ontology_attributes = new LinkedHashMap<InvestigationAttribute,Function<Ontology, String>>();
			ontology_attributes.put(InvestigationAttribute.TERM_SOURCE_NAME, (o) -> o.getName());
			ontology_attributes.put(InvestigationAttribute.TERM_SOURCE_FILE, (o) -> o.getURL().toString());
			ontology_attributes.put(InvestigationAttribute.TERM_SOURCE_VERSION, (o) -> o.getVersion());
			ontology_attributes.put(InvestigationAttribute.TERM_SOURCE_DESCRIPTION, (o) -> o.getDescription());
	
			// Use our lineFromList function on each of the lines defined above; the line name is the key of the 
			// HashMap, the list are the ontologies, and the method to extract the value from the Ontology is
			// the value of the HashMap.
			for(InvestigationAttribute lineName : ontology_attributes.keySet()) {
				writer.write(lineFromList(lineName, this.ontologies, ontology_attributes.get(lineName)));
			}
		
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
			
			// Investigation Comments
			StringBuilder sb = new StringBuilder();
			for (Comment c : this.comments) {
				sb.append(InvestigationAttribute.COMMENT);
				sb = StringUtil.putParameterInStringBuilder(sb, c.getType());
				sb.append(c.getContent()).append(Symbol.ENTER);
			}
			writer.write(sb.toString());
			
		// INVESTIGATION PUBLICATIONS
			
			writer.write(InvestigationAttribute.INVESTIGATION_PUBLICATIONS.toString());
			
			LinkedHashMap<InvestigationAttribute,Function<Publication, String>> publication_attributes = new LinkedHashMap<InvestigationAttribute,Function<Publication, String>>();
			publication_attributes.put(InvestigationAttribute.INVESTIGATION_PUBMED_ID, (o) -> o.getPubmedID());
			publication_attributes.put(InvestigationAttribute.INVESTIGATION_PUBLICATION_DOI, (o) -> o.getDOI());
			publication_attributes.put(InvestigationAttribute.INVESTIGATION_PUBLICATION_AUTHOR_LIST, 
					// This method is a bit more complicated than the previous ones because we have to combine
					// multiple fields in a certain way, but in principle it still works like before.
					(o) -> {
						return o.getAuthorList().stream()
								.map(author -> (author.getLastName() + ", " + author.getFirstName().charAt(0)))
								.collect(Collectors.joining(Symbol.SEMICOLON.toString() + " "));
					});
			publication_attributes.put(InvestigationAttribute.INVESTIGATION_PUBLICATION_TITLE, (o) -> o.getTitle());
			publication_attributes.put(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS, 
					(o) -> {
						return o.getStatusOntology() == null ? Symbol.EMPTY.toString() : o.getStatusOntology().getName();
					});
	
			for(InvestigationAttribute lineName : publication_attributes.keySet()) {
				writer.write(lineFromList(lineName, this.publications, publication_attributes.get(lineName)));
			}
			
			// For the next ones we can't put them into the above HashMap because the key here is a String and not a InvestigationAttribute.
			// AFAIK there is no method of joining InvestigationAttributes without converting them into a String.
			// Publication Status Accession Number
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS.toString(),
					InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), this.publications,
					obj -> { return obj.getStatusOntology() == null ? Symbol.EMPTY.toString() : obj.getStatusOntology().getTermAccessionNumber(); }
			));
			
			// Publication Status Source REF
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS.toString(),
					InvestigationAttribute.TERM_SOURCE_REF.toString()), this.publications,
					obj -> { return obj.getStatusOntology() == null ? Symbol.EMPTY.toString() : obj.getStatusOntology().getSourceREF().getName(); }
			));
		
		writer.close();
		return true;
	}

}
