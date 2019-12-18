/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.Study;

import java.util.ArrayList;

/**
 * Class to represent a person as {@link Contact} for the described
 * {@link Investigation} or {@link Study}
 *
 * @author liufe, arendd
 */
public class Contact {

	/**
	 * The last name/surname of the {@link Contact}
	 */
	private String lastName;

	/**
	 * The first name of the {@link Contact}
	 */
	private String firstName;

	/**
	 * The mid initial of the {@link Contact}
	 */
	private String midInitials;

	/**
	 * The email corresponding to the {@link Contact}
	 */
	private String email;

	/**
	 * A phone number for the {@link Contact}
	 */
	private String phone;

	/**
	 * A fax number for the {@link Contact}
	 */
	private String fax;

	/**
	 * The address for the {@link Contact}
	 */
	private String address;

	/**
	 * The affiliation for the {@link Contact}
	 */
	private String affiliation;

	/**
	 * The role of the person in this {@link Investigation}
	 * @ TODO a person can have more than one role.
	 */
	private OntologyTerm rolesOntology;

	/**
	 * A list of {@link Comment}s for the {@link Contact}
	 */
	private ArrayList<Comment> comments = new ArrayList<>();

	/**
	 * Mandatory attributes of a {@link Contact} for the {@link Investigation} file.
	 */
	public enum Attributes {
		LAST_NAME, FIRST_NAME, MID_INITIALS, EMAIL, PHONE, FAX, ADDRESS, AFFILIATION, ROLES,
		ROLES_TERM_ACCESSION_NUMBER, ROLES_TERM_SOURCE_REF;
	}

	public Contact(String lastName, String firstName, String email, String affiliation, String address) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.affiliation= affiliation;
		this.address=address;
	}

	/**
	 * Add a {@link Comment} to describe the {@link Contact}
	 *
	 * @param comment the {@link Comment} to add
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	/**
	 * Get last name of the {@link Contact}
	 *
	 * @return last name of the {@link Contact}
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Set last name of the {@link Contact}
	 *
	 * @param lastName last name of the {@link Contact}
	 */
	public void setLastName(String lastName) {
		if (lastName == null) {
//			LoggerUtil.logger.error("The lastName of contact can't be null!");
			return;
		}
		this.lastName = lastName;
	}

	/**
	 * Get first name of the {@link Contact}
	 *
	 * @return first name of the {@link Contact}
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Set first name of the {@link Contact}
	 *
	 * @param firstName first name of the {@link Contact}
	 */
	public void setFirstName(String firstName) {
		if (firstName == null) {
//			LoggerUtil.logger.error("The first name of contact can't be null!");
		}
		this.firstName = firstName;
	}

	/**
	 * Get mid initials of the {@link Contact}
	 *
	 * @return mid initials of the {@link Contact}
	 */
	public String getMidInitials() {
		return this.midInitials;
	}

	/**
	 * Set mid initials of the {@link Contact}
	 *
	 * @param midInitials mid initials of the {@link Contact}
	 */
	public void setMidInitials(String midInitials) {
		this.midInitials = midInitials;
	}

	/**
	 * Get email of the {@link Contact}
	 *
	 * @return email of the {@link Contact}
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set email of the {@link Contact}
	 *
	 * @param email email of the {@link Contact}
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get phone number of the {@link Contact}
	 *
	 * @return phone number of the {@link Contact}
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Set phone number of the {@link Contact}
	 *
	 * @param phone phone number of the {@link Contact}
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Get fax number of the {@link Contact}
	 *
	 * @return fax number of the {@link Contact}
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * Set fax number of the {@link Contact}
	 *
	 * @param fax fax number of the {@link Contact}
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Get address of the {@link Contact}
	 *
	 * @return address of the {@link Contact}
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set address of the {@link Contact}
	 *
	 * @param address address of the {@link Contact}
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get affiliation of the {@link Contact}
	 *
	 * @return affiliation of the {@link Contact}
	 */
	public String getAffiliation() {
		return this.affiliation;
	}

	/**
	 * Set affiliation of the {@link Contact}
	 *
	 * @param affiliation affiliation of the {@link Contact}
	 */
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	/**
	 * Get role of the {@link Contact}
	 *
	 * @return role of the {@link Contact}
	 */
	public OntologyTerm getRolesOntology() {
		return this.rolesOntology;
	}

	/**
	 * Set role of the {@link Contact}
	 *
	 * @param rolesOntology role of the {@link Contact}
	 */
	public void setRolesOntology(OntologyTerm rolesOntology) {
		this.rolesOntology = rolesOntology;
	}

	/**
	 * Get comments of the {@link Contact}
	 *
	 * @return comments of the {@link Contact}
	 */
	public ArrayList<Comment> getComments() {
		return this.comments;
	}

	/**
	 * Set comments of the {@link Contact}
	 *
	 * @param comments comments of the {@link Contact}
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

}
