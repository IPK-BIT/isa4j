package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import de.ipk_gatersleben.bit.bi.isa4j.components.CommentCollection;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.MissingFieldException;

/**
 * Corresponds to https://github.com/MIAPPE/ISA-Tab-for-plant-phenotyping commit 0e1a29d
 * 
 * @author psaroudakis, arendd
 *
 */
public class MIAPPEv1x1 {
	public enum InvestigationFile implements InvestigationConfigEnum {

	    /**
	     * (MIAPPE: License) License for the reuse of the data associated with this investigation. The Creative Commons licenses cover most use cases and are recommended.             e.g. CC BY-SA 4.0         
	     */
	    INVESTIGATION_LICENSE("Investigation License", InvestigationAttribute.INVESTIGATION, false),

	    /**
	     * [required](MIAPPE: MIAPPE version) The version of MIAPPE used.             1.1         
	     */
	    MIAPPE_VERSION("MIAPPE Version", InvestigationAttribute.INVESTIGATION, true),

	    /**
	     * Comment[Created with configuration]                      
	     */
	    CREATED_WITH_CONFIGURATION("Created With Configuration", InvestigationAttribute.INVESTIGATION, false),

	    /**
	     * Comment[Last Opened With Configuration]                      
	     */
	    LAST_OPENED_WITH_CONFIGURATION("Last Opened With Configuration", InvestigationAttribute.INVESTIGATION, false),

	    /**
	     * (MIAPPE: Person ID) An identifier for the data submitter. If that submitter is an individual, ORCID identifiers are recommended.             placeholder         
	     */
	    INVESTIGATION_PERSON_ID("Investigation Person ID", InvestigationAttribute.INVESTIGATION_CONTACTS, false),

	    /**
	     * [required](MIAPPE: Start date of study) Date and, if relevant, time when the experiment started             e.g. 2002-04-04         
	     */
	    STUDY_START_DATE("Study Start Date", InvestigationAttribute.STUDY, true),

	    /**
	     * (MIAPPE: End date of study) Date and, if relevant, time when the experiment ende             e.g. 2002-11-27         
	     */
	    STUDY_END_DATE("Study End Date", InvestigationAttribute.STUDY, false),

	    /**
	     * [required](MIAPPE: Contact institution) Name and address of the institution responsible for the study.             e.g. UMR de Génétique Végétale, INRA – Université Paris-Sud – CNRS, Gif-sur-Yvette, France         
	     */
	    STUDY_CONTACT_INSTITUTION("Study Contact Institution", InvestigationAttribute.STUDY, true),

	    /**
	     * [required](MIAPPE: Geographic location (country)) The country where the experiment took place, either as a full name or preferably as a 2-letter code.             e.g. FR         
	     */
	    STUDY_COUNTRY("Study Country", InvestigationAttribute.STUDY, true),

	    /**
	     * [required](MIAPPE: Experimental site name) The name of the natural site, experimental field, greenhouse, phenotyping facility, etc. where the experiment took place.             e.g. INRA, UE Diascope - Chemin de Mezouls - Domaine expérimental de Melgueil - 34130 Mauguio - France         
	     */
	    STUDY_EXPERIMENTAL_SITE_NAME("Study Experimental Site Name", InvestigationAttribute.STUDY, true),

	    /**
	     * (MIAPPE: Geographic location (latitude)) Latitude of the experimental site in degrees, in decimal format.             e.g. +43.619264         
	     */
	    STUDY_LATITUDE("Study Latitude", InvestigationAttribute.STUDY, false),

	    /**
	     * (MIAPPE: Geographic location (longitude)) Longititute of the experimental site in degrees, in decimal format.             e.g. +3.967454         
	     */
	    STUDY_LONGITUDE("Study Longitude", InvestigationAttribute.STUDY, false),

	    /**
	     * (MIAPPE: Geographic location (altitude)) Altitude of the experimental site, provided in metres (m).             e.g. 100 m         
	     */
	    STUDY_ALTITUDE("Study Altitude", InvestigationAttribute.STUDY, false),

	    /**
	     * [required](MIAPPE: Description of growth facility) Short description of the facility in which the study was carried out.             Field environment condition         
	     */
	    DESCRIPTION_OF_GROWTH_FACILITY("Description of Growth Facility", InvestigationAttribute.STUDY, true),

	    /**
	     * (MIAPPE: Type of growth facility) Type of growth facility in which the study was carried out, in the form of an accession number from the Crop Ontology (subclass of CO_715:0000005).             CO_715:0000162         
	     */
	    TYPE_OF_GROWTH_FACILITY("Type of Growth Facility", InvestigationAttribute.STUDY, false),

	    /**
	     * (MIAPPE: Data file link) Link to the data file (or digital object) in a public database or in a persistant institutional repository; or identifier of the data file when submitted together with the MIAPPE submission.             e.g. http://www.ebi.ac.uk/arrayexpress/experiments/E-GEOD-32551/         
	     */
	    STUDY_DATA_FILE_LINK("Study Data File Link", InvestigationAttribute.STUDY, false),

	    /**
	     * (MIAPPE: Data file description) Description of the format of the data file. May be a standard file format name, or a description of organization of the data in a tabular file.             e.g. FASTA, MAGE-Tab         
	     */
	    STUDY_DATA_FILE_DESCRIPTION("Study Data File Description", InvestigationAttribute.STUDY, false),

	    /**
	     * (MIAPPE: Data file version) The version of the dataset (the actual data).             e.g. 1.0         
	     */
	    STUDY_DATA_FILE_VERSION("Study Data File Version", InvestigationAttribute.STUDY, false),

	    /**
	     * [required](MIAPPE: name and url to Trait definition file.                      
	     */
	    TRAIT_DEFINITION_FILE("Trait Definition File", InvestigationAttribute.STUDY, true),

	    /**
	     * (MIAPPE: Person ID) An identifier for the data submitter. If that submitter is an individual, ORCID identifiers are recommended.                      
	     */
	    STUDY_PERSON_ID("Study Person ID", InvestigationAttribute.STUDY_CONTACTS, false),

	    /**
	     * [required](MIAPPE: Description of the experimental design) Short description of the experimental design, possibly including statistical design. In specific cases, e.g. legacy datasets or data computed from several studies, the experimental design can be unknown/NA, aggregated/reduced data, or simply 'none'.                      
	     */
	    STUDY_DESIGN_DESCRIPTION("Study Design Description", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, true),

	    /**
	     * (MIAPPE: Observation unit level hierarchy) Hierarchy of the different levels of repetitions between each others                      
	     */
	    OBSERVATION_UNIT_LEVEL_HIERARCHY("Observation Unit Level Hierarchy", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, false),

	    /**
	     * [required](MIAPPE: Observation unit description) General description of the observation units in the study.                      
	     */
	    OBSERVATION_UNIT_DESCRIPTION("Observation Unit Description", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, true),

	    /**
	     * (MIAPPE: Map of experimental design) Representation of the experimental design.                      
	     */
	    MAP_OF_EXPERIMENTAL_DESIGN("Map Of Experimental Design", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, false),

	    /**
	     * (MIAPPE: Experimental Factor description) Free text description of the experimental factor. This include all relevant treatments planification and protocol planed for all the plant targeted by a given experimental factor.                      
	     */
	    STUDY_FACTOR_DESCRIPTION("Study Factor Description", InvestigationAttribute.STUDY_FACTORS, false),

	    /**
	     * (MIAPPE: Experimental Factor values) List of possible values for the factor.                      
	     */
	    STUDY_FACTOR_VALUES("Study Factor Values", InvestigationAttribute.STUDY_FACTORS, false);


		private String fieldName;
		private InvestigationAttribute section;
		private boolean required;

		private InvestigationFile(String fieldName, InvestigationAttribute section, boolean required) {
			this.fieldName = fieldName;
			this.section = section;
			this.required = required;
		}
		
		public String getFieldName() {
			return this.fieldName;
		}
		
		public boolean isRequired() {
			return this.required;
		}
		
		public InvestigationAttribute getSection() {
			return this.section;
		}
		
		private static void validateInvestigationBlockComments(List<? extends Commentable> commentable, InvestigationAttribute block) {
			commentable.stream().forEach( unit -> {
				CommentCollection comments = unit.comments();
				Stream.of(InvestigationFile.values())
				.filter(c -> c.isRequired() && c.getSection() == block)
				.forEach(c -> {
					if(comments.getByName(c.getFieldName()).isEmpty())
						throw new MissingFieldException("Missing comment in block " + block.toString() + " for " + unit.toString() + ": " + c.getFieldName());
				});
			});
		}
		
		public static boolean validate(Investigation investigation) {
			// Check if all required investigation comments are present
			CommentCollection comments = investigation.comments();
			Stream.of(InvestigationFile.values())
				.filter(c -> c.isRequired() && c.getSection() == InvestigationAttribute.INVESTIGATION)
				.forEach(c -> {
					if(comments.getByName(c.getFieldName()).isEmpty())
						throw new MissingFieldException("Missing comment in block " + InvestigationAttribute.INVESTIGATION.toString() + ": " + c.getFieldName());
				});
			
			validateInvestigationBlockComments(investigation.getPublications(), InvestigationAttribute.INVESTIGATION_PUBLICATIONS);
			validateInvestigationBlockComments(investigation.getContacts(), InvestigationAttribute.INVESTIGATION_CONTACTS);
			
			validateInvestigationBlockComments(investigation.getStudies(), InvestigationAttribute.STUDY);
			for(Study s : investigation.getStudies()) {
				validateInvestigationBlockComments(s.getPublications(), InvestigationAttribute.STUDY_PUBLICATIONS);
				validateInvestigationBlockComments(s.getContacts(), InvestigationAttribute.STUDY_CONTACTS);
				validateInvestigationBlockComments(s.getDesignDescriptors(), InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS);
				validateInvestigationBlockComments(s.getFactors(), InvestigationAttribute.STUDY_FACTORS);
				validateInvestigationBlockComments(s.getAssays(), InvestigationAttribute.STUDY_ASSAYS);
				validateInvestigationBlockComments(s.getProtocols(), InvestigationAttribute.STUDY_PROTOCOLS);
			}
				
			return true;
		}
	}
	
	public enum StudyFile implements WideTableConfigEnum {
		ORGANISM("Organism", true, 0),
		GENUS("Genus", false, 0);
		
		private String fieldName;
		private boolean required;
		private int groupIndex; // the how many n-th object does this characteristic belong to? (i.e the first group is usually the source, second the process, third the sample)
		
		private StudyFile(String fieldName, boolean required, int groupIndex) {
			this.fieldName = fieldName;
			this.required = required;
			this.groupIndex = groupIndex;
		}
		
		public String getFieldName() {
			return this.fieldName;
		}
		
		public boolean isRequired() {
			return this.required;
		}
		
		public int getGroupIndex() {
			return this.groupIndex;
		}
		
		public static boolean validate(Study study) {
			ArrayList<LinkedHashMap<String, String[]>> headers = study.getHeaders();
			Stream.of(StudyFile.values())
				.filter(c -> c.isRequired())
				.forEach(c -> {
					if(!headers.get(c.getGroupIndex()).containsKey("Characteristics[" + c.getFieldName() + "]"))
						throw new MissingFieldException("Missing Characteristic header in Study file: " + c.getFieldName());
				});
			return true;
		}
	}
}
