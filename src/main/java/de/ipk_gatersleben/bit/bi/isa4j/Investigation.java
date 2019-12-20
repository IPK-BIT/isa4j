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

import de.ipk_gatersleben.bit.bi.isa.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;
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
		StringBuilder sb = new StringBuilder();
		sb.append(lineName.toString());
		sb.append(value == null ? Symbol.EMPTY.toString() : value);
		sb.append(Symbol.ENTER.toString());
		return sb.toString();		
	}
	
	// This is meant for unique comments (e.g. study-wide or investigation-wide) that don't have multiple columns.
	private static String formatSimpleComments(List<Comment> comments) {
		StringBuilder sb = new StringBuilder();
		for (Comment c : comments) {
			sb.append(InvestigationAttribute.COMMENT);
      // TODO This is not nice, because what if a comment contains a ? ? Or does it only replace the last occurence?
			sb = StringUtil.putParameterInStringBuilder(sb, c.getName());
			sb.append(c.getValue()).append(Symbol.ENTER);
		}
		return sb.toString();
	}
	
	// @ TODO actually make this function accept a list of commentables and extract comments myself
	private static String formatComments(List<List<Comment>> commentables) {
		StringBuilder sb = new StringBuilder();
		// Get a List of all Comment types present in any of the Commentables (Person, Study..., anything with comments)
		List<String> commentLevels = commentables.stream()
				.flatMap(bucket -> bucket.stream()) // flatten
				.map(comment -> comment.getName())
				.distinct() // remove duplicates
				.collect(Collectors.toList());
		// Now loop through all comment types and create a line for each
		for(String commentType : commentLevels) {
			// @ TODO Use the Investigation Attribute Comment thingy instead of the plain string
			sb.append("Comment[" + commentType + "]" + Symbol.TAB.toString());
			// Now loop through all the buckets and see if that comment type is present
			String commentLine = commentables.stream()
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
		System.out.println(commentLevels);
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

	/**
	 * Collection of units, those by this investigation are used
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
//	public static final Map<String, OntologyAnnotation> unitMap = new ConcurrentHashMap<>();
	
	public boolean writeToFile(String filepath) throws IOException {
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
			writer.write(InvestigationAttribute.INVESTIGATION.toString());
			writer.write(formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_IDENTIFIER, this.identifier));
			writer.write(formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_TITLE, this.title));
			writer.write(formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_DESCRIPTION, this.description));
			writer.write(formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_SUBMISSION_DATE, 
					this.submissionDate == null ? null : this.submissionDate.toString()));
			writer.write(formatSimpleAttribute(InvestigationAttribute.INVESTIGATION_PUBLIC_RELEASE_DATE, 
					this.publicReleaseDate == null ? null : this.publicReleaseDate.toString()));
			
			// Investigation Comments
			writer.write(formatSimpleComments(this.getComments()));
			
			
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
						return o.getStatusOntology() == null ? Symbol.EMPTY.toString() : o.getStatusOntology().getTerm();
					});
	
			for(InvestigationAttribute lineName : publication_attributes.keySet()) {
				writer.write(lineFromList(lineName, this.publications, publication_attributes.get(lineName)));
			}
			
			// For the next ones we can't put them into the above HashMap because the key here is a String and not a InvestigationAttribute.
			// AFAIK there is no method of joining InvestigationAttributes without converting them into a String.
			// Publication Status Accession Number
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS.toString(),
					InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), this.publications,
					obj -> { return obj.getStatusOntology() == null ? Symbol.EMPTY.toString() : obj.getStatusOntology().getTermAccession(); }
			));
			
			// Publication Status Source REF
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS.toString(),
					InvestigationAttribute.TERM_SOURCE_REF.toString()), this.publications,
					obj -> { return obj.getStatusOntology() == null ? Symbol.EMPTY.toString() : obj.getStatusOntology().getSourceREF().getName(); }
			));
			
			
		// INVESTIGATION CONTACTS
			writer.write(InvestigationAttribute.INVESTIGATION_CONTACTS.toString());
			LinkedHashMap<InvestigationAttribute,Function<Person, String>> contact_attributes = new LinkedHashMap<InvestigationAttribute,Function<Person, String>>();
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_LAST_NAME, (o) -> o.getLastName());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_FIRST_NAME, (o) -> o.getFirstName());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_MID_INITIALS, (o) -> o.getMidInitials());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_EMAIL, (o) -> o.getEmail());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_PHONE, (o) -> o.getPhone());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_FAX, (o) -> o.getFax());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_ADDRESS, (o) -> o.getAddress());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_AFFILIATION, (o) -> o.getAffiliation());
			contact_attributes.put(InvestigationAttribute.INVESTIGATION_PERSON_ROLES, 
					(o) -> o.getRoles().stream()
						.map(c -> c.getTerm()).collect(Collectors.joining(Symbol.SEMICOLON.toString())));
	
			for(InvestigationAttribute lineName : contact_attributes.keySet()) {
				writer.write(lineFromList(lineName, this.contacts, contact_attributes.get(lineName)));
			}
			
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PERSON_ROLES.toString(),
					InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), this.contacts,
					obj -> { return obj.getRoles().stream().map(
								r -> r.getTermAccession() == null ? Symbol.EMPTY.toString() : r.getTermAccession()
							).collect(Collectors.joining(Symbol.SEMICOLON.toString())) ; }
			));
			
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PERSON_ROLES.toString(),
					InvestigationAttribute.TERM_SOURCE_REF.toString()), this.contacts,
					obj -> {
						return obj.getRoles().stream().map(
								// If there is no role or if there is but it doesn't have an Ontology connected: return empty string
								// Otherwise return the Ontology's name
								r -> r == null || r.getSourceREF() == null ? Symbol.EMPTY.toString() : r.getSourceREF().getName()
							).collect(Collectors.joining(Symbol.SEMICOLON.toString())); 
					}
			));
		
		// Publication Person Comments
		writer.write(formatComments(this.contacts.stream()
				.map(o -> o.getComments())
				.collect(Collectors.toList()))
		);
		
		// STUDIES
		for(Study study: this.studies) {
			writer.write(InvestigationAttribute.STUDY.toString());
			writer.write(formatSimpleAttribute(InvestigationAttribute.STUDY_IDENTIFIER, study.getIdentifier()));
			writer.write(formatSimpleAttribute(InvestigationAttribute.STUDY_FILE_NAME, study.getFileName()));
			writer.write(formatSimpleAttribute(InvestigationAttribute.STUDY_TITLE, study.getTitle()));
			writer.write(formatSimpleAttribute(InvestigationAttribute.STUDY_DESCRIPTION, study.getDescription()));
			writer.write(formatSimpleAttribute(InvestigationAttribute.STUDY_SUBMISSION_DATE, 
					study.getSubmissionDate() == null ? null : study.getSubmissionDate().toString()));
			writer.write(formatSimpleAttribute(InvestigationAttribute.STUDY_PUBLIC_RELEASE_DATE, 
					study.getPublicReleaseDate() == null ? null : study.getPublicReleaseDate().toString()));
			
			// Study Comments
			writer.write(formatSimpleComments(study.getComments()));
			
			// STUDY DESIGN DESCRIPTORS
			writer.write(InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS.toString());
			
			writer.write(lineFromList(InvestigationAttribute.STUDY_DESIGN_TYPE, study.getDesignDescriptors(), o -> o.getTerm()));
			
			writer.write(lineFromList(mergeAttributes(InvestigationAttribute.STUDY_DESIGN_TYPE.toString(), InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
					study.getDesignDescriptors(),
					descriptor -> {
						if(descriptor.getTermAccession() == null)
							return Symbol.EMPTY.toString();
						else
							return descriptor.getTermAccession();
					}
					));
			
			writer.write(lineFromList(mergeAttributes(InvestigationAttribute.STUDY_DESIGN_TYPE.toString(), InvestigationAttribute.TERM_SOURCE_REF.toString()),
					study.getDesignDescriptors(),
					descriptor -> {
						if(descriptor.getSourceREF() == null)
							return Symbol.EMPTY.toString();
						else
							return descriptor.getSourceREF().getName();
					}
					));
			
			// Study Design Comemnts
			writer.write(formatComments(study.getDesignDescriptors().stream()
					.map(o -> o.getComments())
					.collect(Collectors.toList()))
			);
			
			// STUDY PUBLICATIONS
			writer.write(InvestigationAttribute.STUDY_PUBLICATIONS.toString());
			
			writer.write(lineFromList(InvestigationAttribute.STUDY_PUBMED_ID, study.getPublications(), o -> o.getPubmedID()));
			writer.write(lineFromList(InvestigationAttribute.STUDY_PUBLICATION_DOI, study.getPublications(), o -> o.getDOI()));
			writer.write(lineFromList(InvestigationAttribute.STUDY_PUBLICATION_AUTHOR_LIST, study.getPublications(), 
					(o) -> {
						return o.getAuthorList().stream()
								.map(author -> (author.getLastName() + ", " + author.getFirstName().charAt(0)))
								.collect(Collectors.joining(Symbol.SEMICOLON.toString() + " "));
					}));
			writer.write(lineFromList(InvestigationAttribute.STUDY_PUBLICATION_TITLE, study.getPublications(), o -> o.getTitle()));
			writer.write(lineFromList(InvestigationAttribute.STUDY_PUBLICATION_STATUS, study.getPublications(), 
					(o) -> {
						return o.getStatusOntology() == null ? Symbol.EMPTY.toString() : o.getStatusOntology().getTerm();
					}));
			
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.STUDY_PUBLICATION_STATUS.toString(),
					InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()), study.getPublications(),
					obj -> { return obj.getStatusOntology() == null ? Symbol.EMPTY.toString() : obj.getStatusOntology().getTermAccession(); }
			));
			writer.write(lineFromList(StringUtil.mergeAttributes(InvestigationAttribute.STUDY_PUBLICATION_STATUS.toString(),
					InvestigationAttribute.TERM_SOURCE_REF.toString()), study.getPublications(),
					obj -> { return obj.getStatusOntology() == null ? Symbol.EMPTY.toString() : obj.getStatusOntology().getSourceREF().getName(); }
			));

		}
		
			
		writer.close();
		return true;
	}

}
