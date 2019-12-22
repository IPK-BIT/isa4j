/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa4j.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a person as {@link Person} for the described
 * {@link Investigation} or {@link Study}
 *
 * @author liufe, arendd
 */
public class Person extends Commentable {

	/**
	 * The last name/surname of the {@link Person}
	 */
	private String lastName;

	/**
	 * The first name of the {@link Person}
	 */
	private String firstName;

	/**
	 * The mid initial of the {@link Person}
	 */
	private String midInitials;

	/**
	 * The email corresponding to the {@link Person}
	 */
	private String email;

	/**
	 * A phone number for the {@link Person}
	 */
	private String phone;

	/**
	 * A fax number for the {@link Person}
	 */
	private String fax;

	/**
	 * The address for the {@link Person}
	 */
	private String address;

	/**
	 * The affiliation for the {@link Person}
	 */
	private String affiliation;

	/**
	 * The role of the person in this {@link Investigation}
	 * @ TODO a person can have more than one role.
	 */
	private List<OntologyAnnotation> roles = new ArrayList<OntologyAnnotation>();


	/**
	 * Mandatory attributes of a {@link Person} for the {@link Investigation} file.
	 */
	public enum Attributes {
		LAST_NAME, FIRST_NAME, MID_INITIALS, EMAIL, PHONE, FAX, ADDRESS, AFFILIATION, ROLES,
		ROLES_TERM_ACCESSION_NUMBER, ROLES_TERM_SOURCE_REF;
	}

	public Person(String lastName, String firstName, String email, String affiliation, String address) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.affiliation= affiliation;
		this.address=address;
	}


	/**
	 * Get last name of the {@link Person}
	 *
	 * @return last name of the {@link Person}
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Set last name of the {@link Person}
	 *
	 * @param lastName last name of the {@link Person}
	 */
	public void setLastName(String lastName) {
		if (lastName == null) {
//			LoggerUtil.logger.error("The lastName of contact can't be null!");
			return;
		}
		this.lastName = lastName;
	}

	/**
	 * Get first name of the {@link Person}
	 *
	 * @return first name of the {@link Person}
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Set first name of the {@link Person}
	 *
	 * @param firstName first name of the {@link Person}
	 */
	public void setFirstName(String firstName) {
		if (firstName == null) {
//			LoggerUtil.logger.error("The first name of contact can't be null!");
		}
		this.firstName = firstName;
	}

	/**
	 * Get mid initials of the {@link Person}
	 *
	 * @return mid initials of the {@link Person}
	 */
	public String getMidInitials() {
		return this.midInitials;
	}

	/**
	 * Set mid initials of the {@link Person}
	 *
	 * @param midInitials mid initials of the {@link Person}
	 */
	public void setMidInitials(String midInitials) {
		this.midInitials = midInitials;
	}

	/**
	 * Get email of the {@link Person}
	 *
	 * @return email of the {@link Person}
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set email of the {@link Person}
	 *
	 * @param email email of the {@link Person}
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get phone number of the {@link Person}
	 *
	 * @return phone number of the {@link Person}
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Set phone number of the {@link Person}
	 *
	 * @param phone phone number of the {@link Person}
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Get fax number of the {@link Person}
	 *
	 * @return fax number of the {@link Person}
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * Set fax number of the {@link Person}
	 *
	 * @param fax fax number of the {@link Person}
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Get address of the {@link Person}
	 *
	 * @return address of the {@link Person}
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set address of the {@link Person}
	 *
	 * @param address address of the {@link Person}
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get affiliation of the {@link Person}
	 *
	 * @return affiliation of the {@link Person}
	 */
	public String getAffiliation() {
		return this.affiliation;
	}

	/**
	 * Set affiliation of the {@link Person}
	 *
	 * @param affiliation affiliation of the {@link Person}
	 */
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	/**
	 * Get role of the {@link Person}
	 *
	 * @return role of the {@link Person}
	 */
	public List<OntologyAnnotation> getRoles() {
		return this.roles;
	}
	
	public boolean addRole(OntologyAnnotation role) {
		if(role == null) {
			// TODO ERROR
			return false;
		}
		return this.roles.add(role);
	}

	/**
	 * Set role of the {@link Person}
	 *
	 * @param rolesOntology role of the {@link Person}
	 */
	public void setRoles(List<OntologyAnnotation> rolesOntology) {
		this.roles = rolesOntology;
	}

}
