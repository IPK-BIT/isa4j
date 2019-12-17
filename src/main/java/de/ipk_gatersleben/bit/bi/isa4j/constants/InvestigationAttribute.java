/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.constants;

/**
 * List of constant attributes for the {@link Investigation} files.
 * 
 * @author liufe, arendd
 */
public enum InvestigationAttribute {

	ONTOLOGY_SOURCE_REFERENCE("ONTOLOGY SOURCE REFERENCE" + Symbol.ENTER),
	TERM_SOURCE_NAME("Term Source Name" + Symbol.TAB), TERM_SOURCE_FILE("Term Source File" + Symbol.TAB),
	TERM_SOURCE_VERSION("Term Source Version" + Symbol.TAB),
	TERM_SOURCE_DESCRIPTION("Term Source Description" + Symbol.TAB),

	INVESTIGATION("INVESTIGATION" + Symbol.ENTER), INVESTIGATION_IDENTIFIER("Investigation Identifier" + Symbol.TAB),
	INVESTIGATION_TITLE("Investigation Title" + Symbol.TAB),
	INVESTIGATION_DESCRIPTION("Investigation Description" + Symbol.TAB),
	INVESTIGATION_SUBMISSION_DATE("Investigation Submission Date" + Symbol.TAB),
	INVESTIGATION_PUBLIC_RELEASE_DATE("Investigation Public Release Date" + Symbol.TAB),
	COMMENT("Comment[?]" + Symbol.TAB),

	INVESTIGATION_PUBLICATIONS("INVESTIGATION PUBLICATIONS" + Symbol.ENTER),
	INVESTIGATION_PUBMED_ID("Investigation PubMed ID" + Symbol.TAB),
	INVESTIGATION_PUBLICATION_DOI("Investigation Publication DOI" + Symbol.TAB),
	INVESTIGATION_PUBLICATION_AUTHOR_LIST("Investigation Publication Author List" + Symbol.TAB),
	INVESTIGATION_PUBLICATION_TITLE("Investigation Publication Title" + Symbol.TAB),
	INVESTIGATION_PUBLICATION_STATUS("Investigation Publication Status" + Symbol.TAB),
	TERM_SOURCE_REF("Term Source REF" + Symbol.TAB), TERM_ACCESSION_NUMBER("Term Accession Number" + Symbol.TAB),

	INVESTIGATION_CONTACTS("INVESTIGATION CONTACTS" + Symbol.ENTER),
	INVESTIGATION_PERSON_LAST_NAME("Investigation Person Last Name" + Symbol.TAB),
	INVESTIGATION_PERSON_FIRST_NAME("Investigation Person First Name" + Symbol.TAB),
	INVESTIGATION_PERSON_MID_INITIALS("Investigation Person Mid Initials" + Symbol.TAB),
	INVESTIGATION_PERSON_EMAIL("Investigation Person Email" + Symbol.TAB),
	INVESTIGATION_PERSON_PHONE("Investigation Person Phone" + Symbol.TAB),
	INVESTIGATION_PERSON_FAX("Investigation Person Fax" + Symbol.TAB),
	INVESTIGATION_PERSON_ADDRESS("Investigation Person Address" + Symbol.TAB),
	INVESTIGATION_PERSON_AFFILIATION("Investigation Person Affiliation" + Symbol.TAB),
	INVESTIGATION_PERSON_ROLES("Investigation Person Roles" + Symbol.TAB),

	STUDY("STUDY" + Symbol.ENTER), STUDY_IDENTIFIER("Study Identifier" + Symbol.TAB),
	STUDY_FILE_NAME("Study File Name" + Symbol.TAB), STUDY_TITLE("Study Title" + Symbol.TAB),
	STUDY_DESCRIPTION("Study Description" + Symbol.TAB), STUDY_SUBMISSION_DATE("Study Submission Date" + Symbol.TAB),
	STUDY_PUBLIC_RELEASE_DATE("Study Public Release Date" + Symbol.TAB),

	STUDY_DESIGN_DESCRIPTORS("STUDY DESIGN DESCRIPTORS" + Symbol.ENTER),
	STUDY_DESIGN_TYPE("Study Design Type" + Symbol.TAB),

	STUDY_PUBLICATIONS("STUDY PUBLICATIONS" + Symbol.ENTER),
	STUDY_PUBMED_ID("Study PubMed ID" + Symbol.TAB),
	STUDY_PUBLICATION_DOI("Study Publication DOI" + Symbol.TAB),
	STUDY_PUBLICATION_AUTHOR_LIST("Study Publication Author List" + Symbol.TAB),
	STUDY_PUBLICATION_TITLE("Study Publication Title" + Symbol.TAB),
	STUDY_PUBLICATION_STATUS("Study Publication Status" + Symbol.TAB),

	STUDY_FACTORS("STUDY FACTORS" + Symbol.ENTER), STUDY_FACTOR_NAME("Study Factor Name" + Symbol.TAB),
	STUDY_FACTOR_TYPE("Study Factor Type" + Symbol.TAB),

	STUDY_ASSAYS("STUDY ASSAYS" + Symbol.ENTER), STUDY_ASSAY_FILE_NAME("Study Assay File Name" + Symbol.TAB),
	STUDY_ASSAY_MEASUREMENT_TYPE("Study Assay Measurement Type" + Symbol.TAB),
	STUDY_ASSAY_TECHNOLOGY_TYPE("Study Assay Technology Type" + Symbol.TAB),
	STUDY_ASSAY_TECHNOLOGY_PLATFORM("Study Assay Technology Platform" + Symbol.TAB),

	STUDY_PROTOCOLS("STUDY PROTOCOLS" + Symbol.ENTER), STUDY_PROTOCOL_NAME("Study Protocol Name" + Symbol.TAB),
	STUDY_PROTOCOL_TYPE("Study Protocol Type" + Symbol.TAB),
	STUDY_PROTOCOL_DESCRIPTION("Study Protocol Description" + Symbol.TAB),
	STUDY_PROTOCOL_URI("Study Protocol URI" + Symbol.TAB),
	STUDY_PROTOCOL_VERSION("Study Protocol Version" + Symbol.TAB),
	STUDY_PROTOCOL_PARAMETERS_NAME("Study Protocol Parameters Name" + Symbol.TAB),
	STUDY_PROTOCOL_COMPONENTS_NAME("Study Protocol Components Name" + Symbol.TAB),
	STUDY_PROTOCOL_COMPONENTS_TYPE("Study Protocol Components Type" + Symbol.TAB),

	STUDY_CONTACTS("STUDY CONTACTS" + Symbol.ENTER), STUDY_PERSON_LAST_NAME("Study Person Last Name" + Symbol.TAB),
	STUDY_PERSON_FIRST_NAME("Study Person First Name" + Symbol.TAB),
	STUDY_PERSON_MID_INITIALS("Study Person Mid Initials" + Symbol.TAB),
	STUDY_PERSON_EMAIL("Study Person Email" + Symbol.TAB), STUDY_PERSON_PHONE("Study Person Phone" + Symbol.TAB),
	STUDY_PERSON_FAX("Study Person Fax" + Symbol.TAB), STUDY_PERSON_ADDRESS("Study Person Address" + Symbol.TAB),
	STUDY_PERSON_AFFILIATION("Study Person Affiliation" + Symbol.TAB),
	STUDY_PERSON_ROLES("Study Person Roles" + Symbol.TAB);

	
	private String value;

	private InvestigationAttribute(String val) {
		this.value = val;
	}

	@Override
	public String toString() {
		return value;
	}
}
