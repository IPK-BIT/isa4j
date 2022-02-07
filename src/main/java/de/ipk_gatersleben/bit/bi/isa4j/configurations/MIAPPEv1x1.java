/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.CommentCollection;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.MissingFieldException;

/**
 * MIAPPEv1.1 validation class, modeled after https://github.com/MIAPPE/ISA-Tab-for-plant-phenotyping at commit 0e1a29d from October 2019
 * @author psaroudakis, arendd
 *
 */
public class MIAPPEv1x1 {
	public enum InvestigationFile implements InvestigationConfigEnum {
    
    
    /**
     * License for the reuse of the data associated with this investigation. The Creative Commons licenses cover most use cases and are recommended.
     * <br>
     * e.g. CC BY-SA 4.0
     * <br>
     * [optional]
     * <br>
     */
    INVESTIGATION_LICENSE("Investigation License", InvestigationAttribute.INVESTIGATION, false),
    
    /**
     * The version of MIAPPE used.
     * <br>
     * 1.1
     * <br>
     * <b>[required]</b>
     * <br>
     */
    MIAPPE_VERSION("MIAPPE Version", InvestigationAttribute.INVESTIGATION, true),
    
    /**
     * Comment[Created with configuration]
     * <br>
     * [optional]
     * <br>
     */
    CREATED_WITH_CONFIGURATION("Created With Configuration", InvestigationAttribute.INVESTIGATION, false),
    
    /**
     * Comment[Last Opened With Configuration]
     * <br>
     * [optional]
     * <br>
     */
    LAST_OPENED_WITH_CONFIGURATION("Last Opened With Configuration", InvestigationAttribute.INVESTIGATION, false),
    
    /**
     * An identifier for the data submitter. If that submitter is an individual, ORCID identifiers are recommended.
     * <br>
     * placeholder
     * <br>
     * [optional]
     * <br>
     */
    INVESTIGATION_PERSON_ID("Investigation Person ID", InvestigationAttribute.INVESTIGATION_CONTACTS, false),
    
    /**
     * Date and, if relevant, time when the experiment started
     * <br>
     * e.g. 2002-04-04
     * <br>
     * <b>[required]</b>
     * <br>
     */
    STUDY_START_DATE("Study Start Date", InvestigationAttribute.STUDY, true),
    
    /**
     * Date and, if relevant, time when the experiment ende
     * <br>
     * e.g. 2002-11-27
     * <br>
     * [optional]
     * <br>
     */
    STUDY_END_DATE("Study End Date", InvestigationAttribute.STUDY, false),
    
    /**
     * Name and address of the institution responsible for the study.
     * <br>
     * e.g. UMR de Génétique Végétale, INRA – Université Paris-Sud – CNRS, Gif-sur-Yvette, France
     * <br>
     * <b>[required]</b>
     * <br>
     */
    STUDY_CONTACT_INSTITUTION("Study Contact Institution", InvestigationAttribute.STUDY, true),
    
    /**
     * The country where the experiment took place, either as a full name or preferably as a 2-letter code.
     * <br>
     * e.g. FR
     * <br>
     * <b>[required]</b>
     * <br>
     */
    STUDY_COUNTRY("Study Country", InvestigationAttribute.STUDY, true),
    
    /**
     * The name of the natural site, experimental field, greenhouse, phenotyping facility, etc. where the experiment took place.
     * <br>
     * e.g. INRA, UE Diascope - Chemin de Mezouls - Domaine expérimental de Melgueil - 34130 Mauguio - France
     * <br>
     * <b>[required]</b>
     * <br>
     */
    STUDY_EXPERIMENTAL_SITE_NAME("Study Experimental Site Name", InvestigationAttribute.STUDY, true),
    
    /**
     * Latitude of the experimental site in degrees, in decimal format.
     * <br>
     * e.g. +43.619264
     * <br>
     * [optional]
     * <br>
     */
    STUDY_LATITUDE("Study Latitude", InvestigationAttribute.STUDY, false),
    
    /**
     * Longititute of the experimental site in degrees, in decimal format.
     * <br>
     * e.g. +3.967454
     * <br>
     * [optional]
     * <br>
     */
    STUDY_LONGITUDE("Study Longitude", InvestigationAttribute.STUDY, false),
    
    /**
     * Altitude of the experimental site, provided in metres (m).
     * <br>
     * e.g. 100 m
     * <br>
     * [optional]
     * <br>
     */
    STUDY_ALTITUDE("Study Altitude", InvestigationAttribute.STUDY, false),
    
    /**
     * Short description of the facility in which the study was carried out.
     * <br>
     * Field environment condition
     * <br>
     * <b>[required]</b>
     * <br>
     */
    DESCRIPTION_OF_GROWTH_FACILITY("Description of Growth Facility", InvestigationAttribute.STUDY, true),
    
    /**
     * Type of growth facility in which the study was carried out, in the form of an accession number from the Crop Ontology (subclass of CO_715:0000005).
     * <br>
     * CO_715:0000162
     * <br>
     * [optional]
     * <br>
     */
    TYPE_OF_GROWTH_FACILITY("Type of Growth Facility", InvestigationAttribute.STUDY, false),
    
    /**
     * in a public database or in a persistant institutional repository; or identifier of the data file when submitted together with the MIAPPE submission.
     * <br>
     * e.g. http://www.ebi.ac.uk/arrayexpress/experiments/E-GEOD-32551/
     * <br>
     * [optional]
     * <br>
     */
    STUDY_DATA_FILE_LINK("Study Data File Link", InvestigationAttribute.STUDY, false),
    
    /**
     * Description of the format of the data file. May be a standard file format name, or a description of organization of the data in a tabular file.
     * <br>
     * e.g. FASTA, MAGE-Tab
     * <br>
     * [optional]
     * <br>
     */
    STUDY_DATA_FILE_DESCRIPTION("Study Data File Description", InvestigationAttribute.STUDY, false),
    
    /**
     * The version of the dataset (the actual data).
     * <br>
     * e.g. 1.0
     * <br>
     * [optional]
     * <br>
     */
    STUDY_DATA_FILE_VERSION("Study Data File Version", InvestigationAttribute.STUDY, false),
    
    /**
     * (MIAPPE: name and url to Trait definition file.
     * <br>
     * <b>[required]</b>
     * <br>
     */
    TRAIT_DEFINITION_FILE("Trait Definition File", InvestigationAttribute.STUDY, true),
    
    /**
     * An identifier for the data submitter. If that submitter is an individual, ORCID identifiers are recommended.
     * <br>
     * [optional]
     * <br>
     */
    STUDY_PERSON_ID("Study Person ID", InvestigationAttribute.STUDY_CONTACTS, false),
    
    /**
     * Short description of the experimental design, possibly including statistical design. In specific cases, e.g. legacy datasets or data computed from several studies, the experimental design can be unknown/NA, aggregated/reduced data, or simply 'none'.
     * <br>
     * <b>[required]</b>
     * <br>
     */
    STUDY_DESIGN_DESCRIPTION("Study Design Description", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, true),
    
    /**
     * Hierarchy of the different levels of repetitions between each others
     * <br>
     * [optional]
     * <br>
     */
    OBSERVATION_UNIT_LEVEL_HIERARCHY("Observation Unit Level Hierarchy", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, false),
    
    /**
     * General description of the observation units in the study.
     * <br>
     * <b>[required]</b>
     * <br>
     */
    OBSERVATION_UNIT_DESCRIPTION("Observation Unit Description", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, true),
    
    /**
     * Representation of the experimental design.
     * <br>
     * [optional]
     * <br>
     */
    MAP_OF_EXPERIMENTAL_DESIGN("Map Of Experimental Design", InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS, false),
    
    /**
     * Free text description of the experimental factor. This include all relevant treatments planification and protocol planed for all the plant targeted by a given experimental factor.
     * <br>
     * [optional]
     * <br>
     */
    STUDY_FACTOR_DESCRIPTION("Study Factor Description", InvestigationAttribute.STUDY_FACTORS, false),
    
    /**
     * List of possible values for the factor.
     * <br>
     * [optional]
     * <br>
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

		private static void validateCustomProperties(Investigation investigation) {
		}
		
		public static boolean validate(Investigation investigation) {
			GeneralValidation.validateInvestigationFile(investigation);
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

 			validateCustomProperties(investigation);
				
			return true;
		}
	}
	
	public enum StudyFile implements WideTableConfigEnum {
    
    
    /**
     * An identifier for the organism at the species level. Use of the NCBI taxon ID is recommended.
     * <br>
     * e.g. NCBITAXON:4577
     * <br>
     * <b>[required]</b>
     * <br>
     */
    ORGANISM("Organism", true, 0),
    
    /**
     * Genus name for the organism under study, according to standard scientific nomenclature.
     * <br>
     * e.g. Zea
     * <br>
     * [optional]
     * <br>
     */
    GENUS("Genus", false, 0),
    
    /**
     * for the organism under study, according to standard scientific nomenclature.
     * <br>
     * e.g. mays
     * <br>
     * [optional]
     * <br>
     */
    SPECIES("Species", false, 0),
    
    /**
     * Name of any subtaxa level, including variety, crossing name, etc. It can be used to store any additional taxonomic identifier. Either free text description or key-value pair list format (the key is the name of the rank and the value is the value of the rank). Ranks can be among the following terms: subspecies, cultivar, variety, subvariety, convariety, group, subgroup, hybrid, line, form, subform. For MCPD compliance, the following abbreviations are allowed: ‘subsp.’ (subspecies); ‘convar.’ (convariety); ‘var.’ (variety); ‘f.’ (form); ‘Group’ (cultivar group).
     * <br>
     * e.g. var:B73
     * <br>
     * [optional]
     * <br>
     */
    INFRASPECIFIC_NAME("Infraspecific Name", false, 0),
    
    /**
     * Latitude of the studied biological material. [Alternative identifier for in situ material]
     * <br>
     * e.g. +39.067
     * <br>
     * [optional]
     * <br>
     */
    BIOLOGICAL_MATERIAL_LATITUDE("Biological Material Latitude", false, 0),
    
    /**
     * Latitude of the studied biological material. [Alternative identifier for in situ material]
     * <br>
     * e.g. -8.73
     * <br>
     * [optional]
     * <br>
     */
    BIOLOGICAL_MATERIAL_LONGITUDE("Biological Material Longitude", false, 0),
    
    /**
     * Altitude of the studied biological material, provided in meters (m). [Alternative identifier for in situ material]
     * <br>
     * e.g. 10
     * <br>
     * [optional]
     * <br>
     */
    BIOLOGICAL_MATERIAL_ALTITUDE("Biological Material Altitude", false, 0),
    
    /**
     * Circular uncertainty of the coordinates, preferably provided in meters (m). [Alternative identifier for in situ material]
     * <br>
     * e.g. 200
     * <br>
     * [optional]
     * <br>
     */
    BIOLOGICAL_MATERIAL_COORDINATES_UNCERTAINTY("Biological Material Coordinates Uncertainty", false, 0),
    
    /**
     * Description of any process or treatment applied uniformly to the biological material, prior to the study itself. Can be provided as free text or as an accession number from a suitable controlled vocabulary.
     * <br>
     * e.g. EO:0007210 - PVY(NTN); transplanted from study http://phenome-fppn.fr/maugio/2013/t2351 observation unit ID: pot:894
     * <br>
     * [optional]
     * <br>
     */
    BIOLOGICAL_MATERIAL_PREPROCESSING("Biological Material Preprocessing", false, 0),
    
    /**
     * An identifier for the source of the biological material, in the form of a key-value pair comprising the name/identifier of the repository from which the material was sourced plus the accession number of the repository for that material. Where an accession number has not been assigned, but the material has been derived from the crossing of known accessions, the material can be defined as follows: mother_accession X father_accession, or, if father is unknown, as mother_accession X UNKNOWN. For in situ material, the region of provenance may be used when an accession is not available.
     * <br>
     * e.g. ICNF:PNB-RPI
     * <br>
     * [optional]
     * <br>
     */
    MATERIAL_SOURCE_ID("Material Source ID", false, 0),
    
    /**
     * of the material source
     * <br>
     * e.g. doi:10.15454/1.4658436467893904E12
     * <br>
     * [optional]
     * <br>
     */
    MATERIAL_SOURCE_DOI("Material Source DOI", false, 0),
    
    /**
     * Latitude of the material source. [Alternative identifier for in situ material]
     * <br>
     * e.g. +39.067
     * <br>
     * [optional]
     * <br>
     */
    MATERIAL_SOURCE_LATITUDE("Material Source Latitude", false, 0),
    
    /**
     * Longitude of the material source. [Alternative identifier for in situ material]
     * <br>
     * e.g. -8.73
     * <br>
     * [optional]
     * <br>
     */
    MATERIAL_SOURCE_LONGITUDE("Material Source Longitude", false, 0),
    
    /**
     * Altitude of the material source, provided in metres (m). [Alternative identifier for in situ material]
     * <br>
     * e.g. 10
     * <br>
     * [optional]
     * <br>
     */
    MATERIAL_SOURCE_ALTITUDE("Material Source Altitude", false, 0),
    
    /**
     * Circular uncertainty of the coordinates, provided in meters (m). [Alternative identifier for in situ material]
     * <br>
     * e.g. 200
     * <br>
     * [optional]
     * <br>
     */
    MATERIAL_SOURCE_COORDINATES_UNCERTAINTY("Material Source Coordinates Uncertainty", false, 0),
    
    /**
     * Description of the material source
     * <br>
     * e.g. Branches were collected from a 10-year-old tree growing in a progeny trial established in a loamy brown earth soil.
     * <br>
     * [optional]
     * <br>
     */
    MATERIAL_SOURCE_DESCRIPTION("Material Source Description", false, 0),
    
    /**
     * Type of observation unit in textual form, usually one of the following: study, block, sub-block, plot, sub-plot, pot, plant
     * <br>
     * plant
     * <br>
     * <b>[required]</b>
     * <br>
     */
    OBSERVATION_UNIT_TYPE("Observation Unit Type", true, 2),
    
    /**
     * Identifier for the observation unit in a persistent repository, comprises the name of the repository and the identifier of the observation unit therein. The EBI Biosamples repository can be used. URI are recommended when possible.
     * <br>
     * e.g. Biosamples:SAMEA4202911
     * <br>
     * [optional]
     * <br>
     */
    EXTERNAL_ID("External ID", false, 2),
    
    /**
     * provided as a key-value pair of the form type:value. Levels of observation must be consistent with those listed in the Study section.
     * <br>
     * e.g. latitude:+2.341; row:4 ; X:3; Y:6; Xm:35; Ym:65; block:1; plot:894; replicate:1
     * <br>
     * [optional]
     * <br>
     */
    SPATIAL_DISTRIBUTION("Spatial Distribution", false, 2);
		
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
			// Ensure there is a protocol called "Growth"
			Optional<Protocol> growth = study.getProtocols().stream().filter(p -> p.getName() == "Growth").findFirst();
			if(growth.isEmpty())
				throw new IllegalStateException(study.toString() + " has no Protocol called 'Growth'");
			
			// Ensure there is a protocol called "Phenotyping"
			Optional<Protocol> phenotyping = study.getProtocols().stream().filter(p -> p.getName() == "Phenotyping").findFirst();
			if(phenotyping.isEmpty())
				throw new IllegalStateException(study.toString() + " has no Protocol called 'Phenotyping'");
		}
		
		public static boolean validate(Study study) {
			GeneralValidation.validateStudyFile(study);
			if(!study.hasWrittenHeaders()) {
				throw new IllegalStateException("Study file for " + study.toString() + "can only be validated after headers are written. " +
						"Please write headers with '.writeHeadersFromExample' or call validate after at least one line has been written. " +
						"If that is confusing to you, perhaps you have closed the file/released the strem before validating? That resets the headers");
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
     * Type of observation unit in textual form, usually one of the following: study, block, sub-block, plot, sub-plot, pot, plant
     * <br>
     * placeholder
     * <br>
     * [optional]
     * <br>
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
		}
		
		public static boolean validate(Assay assay) {
			GeneralValidation.validateAssayFile(assay);
			if(!assay.hasWrittenHeaders()) {
				throw new IllegalStateException("Assay file for " + assay.toString() + "can only be validated after headers are written. " +
						"Please write headers with .writeHeadersFromExample or call validate after at least one line has been written. " +
						"If that is confusing to you, perhaps you have closed the file/released the strem before validating? That resets the headers");
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
