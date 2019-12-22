/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j;

import static de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil.mergeAttributes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Person;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Props;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class to represent an Investigation in the ISA Context, which contains the
 * overall information about the goals and means of the experiment.
 *
 * @author liufe, arendd
 */
public class Investigation extends Commentable {

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
	 * Connected {@link Publication}s of this {@link Investigation}
	 */
	private ArrayList<Publication> publications = new ArrayList<>();

	/**
	 * People, who are associated with the {@link Investigation}
	 */
	private ArrayList<Person> contacts = new ArrayList<>();

	/**
	 * Studies of investigations {@link Study}
	 */
	private List<Study> studies = new ArrayList<>();

	/**
	 * Constructor, every Investigation should have an identifier.
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
		this.ontologies.add(ontology);
	}

	/**
	 * Add a contact to the {@link Investigation}
	 *
	 * @param person the contact of a people,which will be add
	 */
	public void addContact(Person person) {
		contacts.add(person);

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
	public boolean addStudy(Study study) {
		// TODO Throw an error instead of just logging; we want to force the user to take notice
		for (Study studyInInvestigation : studies) {
			if (study.getIdentifier().equals(studyInInvestigation.getIdentifier())) {
//				LoggerUtil.logger.error("The investigation " + identifier + " can't add the study. "
//						+ "There is a study in the investigation, that its identifier is " + study.getIdentifier()
//						+ ", please change that identifier!");
				return false;
			}
			if (study.getFileName().equals(studyInInvestigation.getFileName())) {
//				LoggerUtil.logger.error("The investigation " + identifier + " can't add the study. "
//						+ "There is a study in the investigation, that its fileName is " + study.getFileName()
//						+ ", please change that identifier!");
				return false;
			}
		}
//		study.setInvestigation(this);
		this.studies.add(study);
		return true;
	}

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
	 * @param ontologies the library of {@link OntologyAnnotation}
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
	public ArrayList<Person> getContacts() {
		return contacts;
	}

	/**
	 * Set contact of investigation
	 *
	 * @param contacts contact of investigation
	 */
	public void setContacts(ArrayList<Person> contacts) {

		this.contacts = contacts;
	}

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
	public void setStudies(ArrayList<Study> studies) {
		this.studies = studies;
		for (Study study : studies) {
//			study.setInvestigation(this);
		}
	}
	
	// Meant for simple, one-value attributes like Investigation Title
	private static String formatSimpleAttribute(InvestigationAttribute lineName, String value) {
		return lineName.toString()
			+ (value == null ? Symbol.EMPTY.toString() : value)
			+ Symbol.ENTER.toString();
	}
	
	// This is meant for unique comments (e.g. study-wide or investigation-wide) that don't have multiple columns.
	private static String formatSimpleComments(List<Comment> comments) {
		StringBuilder sb = new StringBuilder();
		for (Comment c : comments) {
			sb.append(StringUtil.putNameInAttribute(InvestigationAttribute.COMMENT, c.getName()));
			sb.append(c.getValue()).append(Symbol.ENTER);
		}
		return sb.toString();
	}
	
	private static <T extends Commentable> String formatComments(List<T> commentables) {
		
		// Turn the commentables into a list of lists of comments
		List<List<Comment>> commentBuckets = commentables.stream()
				.map(c -> c.getComments())
				.collect(Collectors.toList());
		
		// Get a List of all Comment types present in any of the buckets
		List<String> commentLevels = commentBuckets.stream()
				.flatMap(bucket -> bucket.stream()) // flatten
				.map(comment -> comment.getName())
				.distinct() // remove duplicates
				.collect(Collectors.toList());
		
		// Now loop through all comment types and create a line for each
		StringBuilder sb = new StringBuilder();
		for(String commentType : commentLevels) {
			// @ TODO Use the Investigation Attribute Comment thingy instead of the plain string
			sb.append("Comment[" + commentType + "]" + Symbol.TAB.toString());
			// Now loop through all the buckets and see if that comment type is present
			String commentLine = commentBuckets.stream()
				// For each bucket -> go through each comment
				.map(bucket -> bucket.stream()
					// Filter out only comments of the current type
					.filter(comment -> comment.getName().equals(commentType))
					// Get the contents of these comments
					.map(comment -> comment.getValue())
					// Join them all together by semicolons (it could be the case that a person has two comments with the same type)
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
				// Join all the buckets by TABs
				.collect(Collectors.joining(Symbol.TAB.toString()));
			sb.append(commentLine);
			sb.append(Symbol.ENTER.toString());
		}
		return sb.toString();
	}
	
	/**
	 * This handy function creates a line of the investigation file from a list of Objects (Ontologies, Publications,
	 * Contacts etc.) and a function to execute on each element of that list (each Ontology, Publication etc.) in order
	 * to extract the content from that object that should go into the line. So, for example, if you want to create a line
	 * containing all the names of the ontologies, your ontologies would be the list that's passed, and the function to get the
	 * name of each ontology would be { ontology -> ontology.getName(); }
	 * That function is then executed for each ontology in the list and the results are joined with TABs (one column for each ontology).
	 * In the beginning, the name of the line is printed and finally the line is finished with an ENTER.
	 * @param <C> Type of Line Name (InvestigationAttribute, String...)
	 * @param <T> Type of objects to iterate over (Ontology, Person, Publication...)
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

	private static <C, T> String ontologyLinesFromList(C lineName, List<T> list, Function<T, OntologyAnnotation> lambda) {
		return lineFromList(lineName, list,
				a ->lambda.apply(a) == null ? Symbol.EMPTY.toString() : lambda.apply(a).getTerm())
		+ lineFromList(mergeAttributes(lineName.toString(),
				InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), list,
				a ->lambda.apply(a) == null ? Symbol.EMPTY.toString() : lambda.apply(a).getTermAccession())
		+ lineFromList(mergeAttributes(lineName.toString(),
				InvestigationAttribute.TERM_SOURCE_REF.toString()), list,
				a ->lambda.apply(a) == null || lambda.apply(a).getSourceREF() == null ? Symbol.EMPTY.toString() : lambda.apply(a).getSourceREF().getName());
	}
	
	/**
	 * Collection of units, those by this investigation are used
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
//	public static final Map<String, OntologyAnnotation> unitMap = new ConcurrentHashMap<>();
	
	private String formatOntologies() {	
		return InvestigationAttribute.ONTOLOGY_SOURCE_REFERENCE.toString()
			+  lineFromList(InvestigationAttribute.TERM_SOURCE_NAME, this.ontologies, o -> o.getName())
			+  lineFromList(InvestigationAttribute.TERM_SOURCE_FILE, this.ontologies, o -> o.getURL().toString())
			+  lineFromList(InvestigationAttribute.TERM_SOURCE_VERSION, this.ontologies, o -> o.getVersion())
			+  lineFromList(InvestigationAttribute.TERM_SOURCE_DESCRIPTION, this.ontologies, o -> o.getDescription());
	}
	
	private String formatInvestigationHeaders() {		
		return InvestigationAttribute.INVESTIGATION.toString()
			+  formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_IDENTIFIER, this.identifier)
			+  formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_TITLE, this.title)
			+  formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_DESCRIPTION, this.description)
			+  formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_SUBMISSION_DATE, 
				this.submissionDate == null ? null : this.submissionDate.toString())
			+  formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_PUBLIC_RELEASE_DATE, 
				this.publicReleaseDate == null ? null : this.publicReleaseDate.toString())
				
			+  formatSimpleComments(this.getComments());	
	}
	// TODO why do we need two methods, investigationPublications and studyPublications?
	private String formatInvestigationPublications() {	
		return InvestigationAttribute.INVESTIGATION_PUBLICATIONS.toString()
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PUBMED_ID, this.publications, (o) -> o.getPubmedID())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PUBLICATION_DOI, this.publications, (o) -> o.getDOI())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PUBLICATION_AUTHOR_LIST, this.publications, 
				// This method is a bit more complicated than the previous ones because we have to combine
				// multiple fields in a certain way, but in principle it still works like before.
				(o) -> {
					return o.getAuthorList().stream()
							.map(author -> (author.getLastName() + ", " + author.getFirstName().charAt(0)))
							.collect(Collectors.joining(Symbol.SEMICOLON.toString() + " "));
				})
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PUBLICATION_TITLE, this.publications, (o) -> o.getTitle())
			+ ontologyLinesFromList(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS, this.publications, p -> p.getStatusOntology())
		
			+ formatComments(this.publications);
		
	}

	private String formatInvestigationContacts() {
		return InvestigationAttribute.INVESTIGATION_CONTACTS.toString()
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_LAST_NAME, this.contacts, (o) -> o.getLastName())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_FIRST_NAME, this.contacts,(o) -> o.getFirstName())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_MID_INITIALS, this.contacts,(o) -> o.getMidInitials())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_EMAIL, this.contacts,(o) -> o.getEmail())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_PHONE, this.contacts,(o) -> o.getPhone())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_FAX, this.contacts,(o) -> o.getFax())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_ADDRESS, this.contacts,(o) -> o.getAddress())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_AFFILIATION, this.contacts,(o) -> o.getAffiliation())
			+ lineFromList(InvestigationAttribute.INVESTIGATION_PERSON_ROLES, this.contacts,
				(o) -> o.getRoles().stream().map(c -> c.getTerm()).collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			+ lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PERSON_ROLES.toString(),
				InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), this.contacts,
				obj -> { return obj.getRoles().stream().map(
							r -> r.getTermAccession() == null ? Symbol.EMPTY.toString() : r.getTermAccession()
						).collect(Collectors.joining(Symbol.SEMICOLON.toString())) ; }
				)
			+ lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PERSON_ROLES.toString(),
				InvestigationAttribute.TERM_SOURCE_REF.toString()), this.contacts,
				obj -> {
					return obj.getRoles().stream().map(
							// If there is no role or if there is but it doesn't have an Ontology connected: return empty string
							// Otherwise return the Ontology's name
							r -> r == null || r.getSourceREF() == null ? Symbol.EMPTY.toString() : r.getSourceREF().getName()
						).collect(Collectors.joining(Symbol.SEMICOLON.toString())); }
				)
			+ formatComments(this.contacts);
	}
	
	private String formatStudyHeaders(Study study) {
		return InvestigationAttribute.STUDY.toString()
			+ formatSimpleAttribute(InvestigationAttribute.STUDY_IDENTIFIER, study.getIdentifier())
			+ formatSimpleAttribute(InvestigationAttribute.STUDY_FILE_NAME, study.getFileName())
			+ formatSimpleAttribute(InvestigationAttribute.STUDY_TITLE, study.getTitle())
			+ formatSimpleAttribute(InvestigationAttribute.STUDY_DESCRIPTION, study.getDescription())
			+ formatSimpleAttribute(InvestigationAttribute.STUDY_SUBMISSION_DATE, 
				study.getSubmissionDate() == null ? null : study.getSubmissionDate().toString())
			+ formatSimpleAttribute(InvestigationAttribute.STUDY_PUBLIC_RELEASE_DATE, 
				study.getPublicReleaseDate() == null ? null : study.getPublicReleaseDate().toString())
			+ formatSimpleComments(study.getComments())
		// STUDY DESIGN DESCRIPTORS
			+ InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS.toString()
			+ ontologyLinesFromList(InvestigationAttribute.STUDY_DESIGN_TYPE, study.getDesignDescriptors(), o -> o)
			+ formatComments(study.getDesignDescriptors());
		
	}
	
	private String formatStudyPublications(Study study) {
		return InvestigationAttribute.STUDY_PUBLICATIONS.toString()		
			+ lineFromList(InvestigationAttribute.STUDY_PUBMED_ID, study.getPublications(), o -> o.getPubmedID())
			+ lineFromList(InvestigationAttribute.STUDY_PUBLICATION_DOI, study.getPublications(), o -> o.getDOI())
			+ lineFromList(InvestigationAttribute.STUDY_PUBLICATION_AUTHOR_LIST, study.getPublications(), 
				(o) -> {
					return o.getAuthorList().stream()
							.map(author -> (author.getLastName() + ", " + author.getFirstName().charAt(0)))
							.collect(Collectors.joining(Symbol.SEMICOLON.toString() + " "));
				})
			+ lineFromList(InvestigationAttribute.STUDY_PUBLICATION_TITLE, study.getPublications(), o -> o.getTitle())
			+ ontologyLinesFromList(InvestigationAttribute.STUDY_PUBLICATION_STATUS, study.getPublications(), p -> p.getStatusOntology())
			+ formatComments(study.getPublications());
	}
	
	private String formatStudyFactors(Study study) {
		return InvestigationAttribute.STUDY_FACTORS.toString()
			+ lineFromList(InvestigationAttribute.STUDY_FACTOR_NAME, study.getFactors(), o -> o.getName())
			+ ontologyLinesFromList(InvestigationAttribute.STUDY_FACTOR_TYPE, study.getFactors(), f -> f.getType())
			+ formatComments(study.getFactors());
	}
	
	private String formatStudyAssays(Study study) {
		return InvestigationAttribute.STUDY_ASSAYS.toString()
			+ lineFromList(InvestigationAttribute.STUDY_ASSAY_FILE_NAME, study.getAssays(), a -> a.getFileName())
			+ ontologyLinesFromList(InvestigationAttribute.STUDY_ASSAY_MEASUREMENT_TYPE, study.getAssays(), a -> a.getMeasurementType())
			+ ontologyLinesFromList(InvestigationAttribute.STUDY_ASSAY_TECHNOLOGY_TYPE, study.getAssays(), a -> a.getTechnologyType())
			+ lineFromList(InvestigationAttribute.STUDY_ASSAY_TECHNOLOGY_PLATFORM, study.getAssays(), a -> a.getTechnologyPlatform())
			+ formatComments(study.getAssays());
	}
	
	private String formatStudyProtocols(Study study) {
		return InvestigationAttribute.STUDY_PROTOCOLS.toString()
			+ lineFromList(InvestigationAttribute.STUDY_PROTOCOL_NAME, study.getProtocols(), p -> p.getName())
			+ ontologyLinesFromList(InvestigationAttribute.STUDY_PROTOCOL_TYPE, study.getProtocols(), p -> p.getType())
			+ lineFromList(InvestigationAttribute.STUDY_PROTOCOL_DESCRIPTION, study.getProtocols(), p -> p.getDescription())
			+ lineFromList(InvestigationAttribute.STUDY_PROTOCOL_URI, study.getProtocols(), p -> p.getURI())
			+ lineFromList(InvestigationAttribute.STUDY_PROTOCOL_VERSION, study.getProtocols(), p -> p.getVersion())
			
			// Protocol Parameters
			+ lineFromList(InvestigationAttribute.STUDY_PROTOCOL_PARAMETERS_NAME, study.getProtocols(),
					p -> p.getParameters().stream()
					.map(param -> param.getName().getTerm())
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			+ lineFromList(mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_PARAMETERS_NAME.toString(), InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), study.getProtocols(),
					p -> p.getParameters().stream()
					.map(param -> param.getName() == null || param.getName().getTermAccession() == null ? Symbol.EMPTY.toString() : param.getName().getTermAccession())
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			+ lineFromList(mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_PARAMETERS_NAME.toString(), InvestigationAttribute.TERM_SOURCE_REF.toString()), study.getProtocols(),
					p -> p.getParameters().stream()
					.map(param -> param.getName() == null || param.getName().getSourceREF() == null ? Symbol.EMPTY.toString() : param.getName().getSourceREF().getName())
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			
			// Protocol Components
			+ lineFromList(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_NAME, study.getProtocols(),
					p -> p.getComponents().stream()
					.map(component -> component.getName())
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			+ lineFromList(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_TYPE, study.getProtocols(),
					p -> p.getComponents().stream()
					.map(component -> component.getType() == null ? Symbol.EMPTY.toString() : component.getType().getTerm())
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			+ lineFromList(mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_TYPE.toString(), InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), study.getProtocols(),
					p -> p.getComponents().stream()
					.map(component -> component.getType() == null || component.getType().getTermAccession() == null ? Symbol.EMPTY.toString() : component.getType().getTermAccession())
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			+ lineFromList(mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_TYPE.toString(), InvestigationAttribute.TERM_SOURCE_REF.toString()), study.getProtocols(),
					p -> p.getComponents().stream()
					.map(component -> component.getType() == null || component.getType().getSourceREF() == null ? Symbol.EMPTY.toString() : component.getType().getSourceREF().getName())
					.collect(Collectors.joining(Symbol.SEMICOLON.toString())))
			
			+ formatComments(study.getProtocols());
	}
	
	private String formatStudyContacts(Study study) {
		// TODO
		return "";
	}
	
	public void writeToFile(String filepath) throws IOException {
		OutputStream os = new FileOutputStream(filepath);
		OutputStreamWriter writer = new OutputStreamWriter(os, Props.DEFAULT_CHARSET);
		
		writer.write(formatOntologies());
		writer.write(formatInvestigationHeaders());
		writer.write(formatInvestigationPublications());
		writer.write(formatInvestigationContacts());
			
		for(Study study: this.studies) {
			writer.write(formatStudyHeaders(study));
			writer.write(formatStudyPublications(study));
			writer.write(formatStudyFactors(study));
			writer.write(formatStudyAssays(study));
			writer.write(formatStudyProtocols(study));
			writer.write(formatStudyContacts(study));
		}
			
		writer.close();
	}

}
