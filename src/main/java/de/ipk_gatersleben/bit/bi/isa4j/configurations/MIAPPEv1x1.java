package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.CommentCollection;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.MissingFieldException;

/**
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
    
    /**
     * [required](MIAPPE: Organism) An identifier for the organism at the species level. Use of the NCBI taxon ID is recommended.             e.g. NCBITAXON:4577         
     */
    ORGANISM("Organism", true, 0),
    /**
     * (MIAPPE: Genus) Genus name for the organism under study, according to standard scientific nomenclature.             e.g. Zea         
     */
    GENUS("Genus", false, 0),
    /**
     * (MIAPPE: Species) Species name (formally: specific epithet) for the organism under study, according to standard scientific nomenclature.             e.g. mays         
     */
    SPECIES("Species", false, 0),
    /**
     * (MIAPPE: Infraspecific name) Name of any subtaxa level, including variety, crossing name, etc. It can be used to store any additional taxonomic identifier. Either free text description or key-value pair list format (the key is the name of the rank and the value is the value of the rank). Ranks can be among the following terms: subspecies, cultivar, variety, subvariety, convariety, group, subgroup, hybrid, line, form, subform. For MCPD compliance, the following abbreviations are allowed: ‘subsp.’ (subspecies); ‘convar.’ (convariety); ‘var.’ (variety); ‘f.’ (form); ‘Group’ (cultivar group).             e.g. var:B73         
     */
    INFRASPECIFIC_NAME("Infraspecific Name", false, 0),
    /**
     * (MIAPPE: Biological material latitude) Latitude of the studied biological material. [Alternative identifier for in situ material]             e.g. +39.067         
     */
    BIOLOGICAL_MATERIAL_LATITUDE("Biological Material Latitude", false, 0),
    /**
     * (MIAPPE: Biological material longitude) Latitude of the studied biological material. [Alternative identifier for in situ material]             e.g. -8.73         
     */
    BIOLOGICAL_MATERIAL_LONGITUDE("Biological Material Longitude", false, 0),
    /**
     * (MIAPPE: Biological Material altitude) Altitude of the studied biological material, provided in meters (m). [Alternative identifier for in situ material]             e.g. 10         
     */
    BIOLOGICAL_MATERIAL_ALTITUDE("Biological Material Altitude", false, 0),
    /**
     * (MIAPPE: Biological material coordinates uncertainty) Circular uncertainty of the coordinates, preferably provided in meters (m). [Alternative identifier for in situ material]             e.g. 200         
     */
    BIOLOGICAL_MATERIAL_COORDINATES_UNCERTAINTY("Biological Material Coordinates Uncertainty", false, 0),
    /**
     * (MIAPPE: Biological material preprocessing) Description of any process or treatment applied uniformly to the biological material, prior to the study itself. Can be provided as free text or as an accession number from a suitable controlled vocabulary.             e.g. EO:0007210 - PVY(NTN); transplanted from study http://phenome-fppn.fr/maugio/2013/t2351 observation unit ID: pot:894         
     */
    BIOLOGICAL_MATERIAL_PREPROCESSING("Biological Material Preprocessing", false, 0),
    /**
     * (MIAPPE: Material source ID (Holding institute/stock centre, accession) An identifier for the source of the biological material, in the form of a key-value pair comprising the name/identifier of the repository from which the material was sourced plus the accession number of the repository for that material. Where an accession number has not been assigned, but the material has been derived from the crossing of known accessions, the material can be defined as follows: mother_accession X father_accession, or, if father is unknown, as mother_accession X UNKNOWN. For in situ material, the region of provenance may be used when an accession is not available.             e.g. ICNF:PNB-RPI         
     */
    MATERIAL_SOURCE_ID("Material Source ID", false, 0),
    /**
     * (MIAPPE: Material source DOI) Digital Object Identifier (DOI) of the material source             e.g. doi:10.15454/1.4658436467893904E12         
     */
    MATERIAL_SOURCE_DOI("Material Source DOI", false, 0),
    /**
     * (MIAPPE: Material source latitude) Latitude of the material source. [Alternative identifier for in situ material]             e.g. +39.067         
     */
    MATERIAL_SOURCE_LATITUDE("Material Source Latitude", false, 0),
    /**
     * (MIAPPE: Material source longitude) Longitude of the material source. [Alternative identifier for in situ material]             e.g. -8.73         
     */
    MATERIAL_SOURCE_LONGITUDE("Material Source Longitude", false, 0),
    /**
     * (MIAPPE: Material source altitude) Altitude of the material source, provided in metres (m). [Alternative identifier for in situ material]             e.g. 10         
     */
    MATERIAL_SOURCE_ALTITUDE("Material Source Altitude", false, 0),
    /**
     * (MIAPPE: Material source coordinates uncertainty) Circular uncertainty of the coordinates, provided in meters (m). [Alternative identifier for in situ material]             e.g. 200         
     */
    MATERIAL_SOURCE_COORDINATES_UNCERTAINTY("Material Source Coordinates Uncertainty", false, 0),
    /**
     * (MIAPPE: Material source description) Description of the material source             e.g. Branches were collected from a 10-year-old tree growing in a progeny trial established in a loamy brown earth soil.         
     */
    MATERIAL_SOURCE_DESCRIPTION("Material Source Description", false, 0),
    /**
     * [required](MIAPPE: Observation unit type) Type of observation unit in textual form, usually one of the following: study, block, sub-block, plot, sub-plot, pot, plant             plant             study,block,sub-block,plot,sub-plot,pot,plant         
     */
    OBSERVATION_UNIT_TYPE("Observation Unit Type", true, 0),
    /**
     * (MIAPPE: External ID) Identifier for the observation unit in a persistent repository, comprises the name of the repository and the identifier of the observation unit therein. The EBI Biosamples repository can be used. URI are recommended when possible.             e.g. Biosamples:SAMEA4202911         
     */
    EXTERNAL_ID("External ID", false, 0),
    /**
     * (MIAPPE: Spatial distribution) Type and value of a spatial coordinate (georeference or relative) or level of observation (plot 45, subblock 7, block 2) provided as a key-value pair of the form type:value. Levels of observation must be consistent with those listed in the Study section.             e.g. latitude:+2.341; row:4 ; X:3; Y:6; Xm:35; Ym:65; block:1; plot:894; replicate:1         
     */
    SPATIAL_DISTRIBUTION("Spatial Distribution", false, 0);
		
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
		
		private static void validateCustomProperties(Study study) {
		}
		
		public static boolean validate(Study study) {
			if(!study.hasWrittenHeaders()) {
				throw new IllegalStateException("Study file for " + study.toString() + "can only be validated after headers are written." +
						"Please write headers with .writeHeadersFromExample or call validate after at least one line has been written.");
			}
			ArrayList<LinkedHashMap<String, String[]>> headers = study.getHeaders();
			Stream.of(StudyFile.values())
				.filter(c -> c.isRequired())
				.forEach(c -> {
					if(!headers.get(c.getGroupIndex()).containsKey("Characteristics[" + c.getFieldName() + "]"))
						throw new MissingFieldException("Missing Characteristic header in Study file: " + c.getFieldName());
				});
			validateCustomProperties(study);
			return true;
		}
	}

	public enum AssayFile implements WideTableConfigEnum {
    
    /**
     * (MIAPPE: Experimental unit type) Type of observation unit in textual form, usually one of the following: study, block, sub-block, plot, sub-plot, pot, plant             study,block,sub-block,plot,sub-plot,pot,plant             placeholder         
     */
    OBSERVATION_UNIT_TYPE("Observation Unit Type", false, 0);

		private String fieldName;
		private boolean required;
		private int groupIndex; // the how many n-th object does this characteristic belong to? (i.e the first group is usually the source, second the process, third the sample)
		
		private AssayFile(String fieldName, boolean required, int groupIndex) {
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
		
		private static void validateCustomProperties(Assay assay) {
			//@TODO 
		}
		
		public static boolean validate(Assay assay) {
			if(!assay.hasWrittenHeaders()) {
				throw new IllegalStateException("Assay file for " + assay.toString() + "can only be validated after headers are written." +
						"Please write headers with .writeHeadersFromExample or call validate after at least one line has been written.");
			}
			ArrayList<LinkedHashMap<String, String[]>> headers = assay.getHeaders();
			Stream.of(AssayFile.values())
				.filter(c -> c.isRequired())
				.forEach(c -> {
					if(!headers.get(c.getGroupIndex()).containsKey("Characteristics[" + c.getFieldName() + "]"))
						throw new MissingFieldException("Missing Characteristic header in Assay file: " + c.getFieldName());
				});
			validateCustomProperties(assay);
			return true;
		}
		
	}
}
