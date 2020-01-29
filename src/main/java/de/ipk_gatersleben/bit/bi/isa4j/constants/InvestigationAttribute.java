/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.constants;

import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;

/**
 * List of constant attributes for the {@link Investigation} files.
 * 
 * @author liufe, arendd, psaroudakis
 */
public enum InvestigationAttribute {

	ONTOLOGY_SOURCE_REFERENCE("ONTOLOGY SOURCE REFERENCE"),
	TERM_SOURCE_NAME("Term Source Name"), TERM_SOURCE_FILE("Term Source File"),
	TERM_SOURCE_VERSION("Term Source Version"),
	TERM_SOURCE_DESCRIPTION("Term Source Description"),

	INVESTIGATION("INVESTIGATION"), INVESTIGATION_IDENTIFIER("Investigation Identifier"),
	INVESTIGATION_TITLE("Investigation Title"),
	INVESTIGATION_DESCRIPTION("Investigation Description"),
	INVESTIGATION_SUBMISSION_DATE("Investigation Submission Date"),
	INVESTIGATION_PUBLIC_RELEASE_DATE("Investigation Public Release Date"),
	COMMENT("Comment[?]"),

	INVESTIGATION_PUBLICATIONS("INVESTIGATION PUBLICATIONS"),
	INVESTIGATION_PUBMED_ID("Investigation PubMed ID"),
	INVESTIGATION_PUBLICATION_DOI("Investigation Publication DOI"),
	INVESTIGATION_PUBLICATION_AUTHOR_LIST("Investigation Publication Author List"),
	INVESTIGATION_PUBLICATION_TITLE("Investigation Publication Title"),
	INVESTIGATION_PUBLICATION_STATUS("Investigation Publication Status"),
	TERM_SOURCE_REF("Term Source REF"), TERM_ACCESSION_NUMBER("Term Accession Number"),

	INVESTIGATION_CONTACTS("INVESTIGATION CONTACTS"),
	INVESTIGATION_PERSON_LAST_NAME("Investigation Person Last Name"),
	INVESTIGATION_PERSON_FIRST_NAME("Investigation Person First Name"),
	INVESTIGATION_PERSON_MID_INITIALS("Investigation Person Mid Initials"),
	INVESTIGATION_PERSON_EMAIL("Investigation Person Email"),
	INVESTIGATION_PERSON_PHONE("Investigation Person Phone"),
	INVESTIGATION_PERSON_FAX("Investigation Person Fax"),
	INVESTIGATION_PERSON_ADDRESS("Investigation Person Address"),
	INVESTIGATION_PERSON_AFFILIATION("Investigation Person Affiliation"),
	INVESTIGATION_PERSON_ROLES("Investigation Person Roles"),

	STUDY("STUDY"), STUDY_IDENTIFIER("Study Identifier"),
	STUDY_FILE_NAME("Study File Name"), STUDY_TITLE("Study Title"),
	STUDY_DESCRIPTION("Study Description"), STUDY_SUBMISSION_DATE("Study Submission Date"),
	STUDY_PUBLIC_RELEASE_DATE("Study Public Release Date"),

	STUDY_DESIGN_DESCRIPTORS("STUDY DESIGN DESCRIPTORS"),
	STUDY_DESIGN_TYPE("Study Design Type"),

	STUDY_PUBLICATIONS("STUDY PUBLICATIONS"),
	STUDY_PUBMED_ID("Study PubMed ID"),
	STUDY_PUBLICATION_DOI("Study Publication DOI"),
	STUDY_PUBLICATION_AUTHOR_LIST("Study Publication Author List"),
	STUDY_PUBLICATION_TITLE("Study Publication Title"),
	STUDY_PUBLICATION_STATUS("Study Publication Status"),

	STUDY_FACTORS("STUDY FACTORS"), STUDY_FACTOR_NAME("Study Factor Name"),
	STUDY_FACTOR_TYPE("Study Factor Type"),

	STUDY_ASSAYS("STUDY ASSAYS"), STUDY_ASSAY_FILE_NAME("Study Assay File Name"),
	STUDY_ASSAY_MEASUREMENT_TYPE("Study Assay Measurement Type"),
	STUDY_ASSAY_TECHNOLOGY_TYPE("Study Assay Technology Type"),
	STUDY_ASSAY_TECHNOLOGY_PLATFORM("Study Assay Technology Platform"),

	STUDY_PROTOCOLS("STUDY PROTOCOLS"), STUDY_PROTOCOL_NAME("Study Protocol Name"),
	STUDY_PROTOCOL_TYPE("Study Protocol Type"),
	STUDY_PROTOCOL_DESCRIPTION("Study Protocol Description"),
	STUDY_PROTOCOL_URI("Study Protocol URI"),
	STUDY_PROTOCOL_VERSION("Study Protocol Version"),
	STUDY_PROTOCOL_PARAMETERS_NAME("Study Protocol Parameters Name"),
	STUDY_PROTOCOL_COMPONENTS_NAME("Study Protocol Components Name"),
	STUDY_PROTOCOL_COMPONENTS_TYPE("Study Protocol Components Type"),

	STUDY_CONTACTS("STUDY CONTACTS"), STUDY_PERSON_LAST_NAME("Study Person Last Name"),
	STUDY_PERSON_FIRST_NAME("Study Person First Name"),
	STUDY_PERSON_MID_INITIALS("Study Person Mid Initials"),
	STUDY_PERSON_EMAIL("Study Person Email"), STUDY_PERSON_PHONE("Study Person Phone"),
	STUDY_PERSON_FAX("Study Person Fax"), STUDY_PERSON_ADDRESS("Study Person Address"),
	STUDY_PERSON_AFFILIATION("Study Person Affiliation"),
	STUDY_PERSON_ROLES("Study Person Roles");

	
	private String value;

	private InvestigationAttribute(String val) {
		this.value = val;
	}

	@Override
	public String toString() {
		return value;
	}
}
