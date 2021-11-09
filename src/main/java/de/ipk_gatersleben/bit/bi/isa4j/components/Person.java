/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class to represent a person as {@link Person} for the described
 * {@link Investigation} or {@link Study}
 *
 * @author liufe, arendd
 */
public class Person implements Commentable {
	
	/**
	 * The address for the {@link Person}
	 */
	private String address;
	
	/**
	 * The affiliation for the {@link Person}
	 */
	private String affiliation;

	private CommentCollection comments = new CommentCollection();

	/**
	 * The email corresponding to the {@link Person}
	 */
	private String email;

	/**
	 * A fax number for the {@link Person}
	 */
	private String fax;

	/**
	 * The first name of the {@link Person}
	 */
	private String firstName;

	/**
	 * The last name/surname of the {@link Person}
	 */
	private String lastName;

	/**
	 * The mid initial of the {@link Person}
	 */
	private String midInitials;

	/**
	 * A phone number for the {@link Person}
	 */
	private String phone;

	/**
	 * The role of the person in this {@link Investigation}
	 */
	private List<OntologyAnnotation> roles = new ArrayList<OntologyAnnotation>();

	public Person(String lastName, String firstName, String email, String affiliation, String address) {
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setEmail(email);
		this.setAffiliation(affiliation);
		this.setAddress(address);
	}

	public void addRole(OntologyAnnotation role) {
		this.roles.add(Objects.requireNonNull(role, "Role cannot be null"));
	}


	public CommentCollection comments() {
		return this.comments;
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
	 * Get affiliation of the {@link Person}
	 *
	 * @return affiliation of the {@link Person}
	 */
	public String getAffiliation() {
		return this.affiliation;
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
	 * Get fax number of the {@link Person}
	 *
	 * @return fax number of the {@link Person}
	 */
	public String getFax() {
		return this.fax;
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
	 * Get last name of the {@link Person}
	 *
	 * @return last name of the {@link Person}
	 */
	public String getLastName() {
		return this.lastName;
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
	 * Get phone number of the {@link Person}
	 *
	 * @return phone number of the {@link Person}
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Get role of the {@link Person}
	 *
	 * @return role of the {@link Person}
	 */
	public List<OntologyAnnotation> getRoles() {
		return this.roles;
	}

	/**
	 * Set address of the {@link Person}
	 *
	 * @param address address of the {@link Person}
	 */
	public void setAddress(String address) {
		this.address = StringUtil.sanitize(address);
	}

	/**
	 * Set affiliation of the {@link Person}
	 *
	 * @param affiliation affiliation of the {@link Person}
	 */
	public void setAffiliation(String affiliation) {
		this.affiliation = StringUtil.sanitize(affiliation);
	}

	/**
	 * Set email of the {@link Person}
	 *
	 * @param email email of the {@link Person}
	 */
	public void setEmail(String email) {
		this.email = StringUtil.sanitize(email);
	}

	/**
	 * Set fax number of the {@link Person}
	 *
	 * @param fax fax number of the {@link Person}
	 */
	public void setFax(String fax) {
		this.fax = StringUtil.sanitize(fax);
	}

	/**
	 * Set first name of the {@link Person}
	 *
	 * @param firstName first name of the {@link Person}
	 */
	public void setFirstName(String firstName) {
		this.firstName = StringUtil.sanitize(firstName);
	}

	/**
	 * Set last name of the {@link Person}
	 *
	 * @param lastName last name of the {@link Person}
	 */
	public void setLastName(String lastName) {
		this.lastName = StringUtil.sanitize(lastName);
	}

	/**
	 * Set mid initials of the {@link Person}
	 *
	 * @param midInitials mid initials of the {@link Person}
	 */
	public void setMidInitials(String midInitials) {
		this.midInitials = StringUtil.sanitize(midInitials);
	}
	
	/**
	 * Set phone number of the {@link Person}
	 *
	 * @param phone phone number of the {@link Person}
	 */
	public void setPhone(String phone) {
		this.phone = StringUtil.sanitize(phone);
	}

	/**
	 * Set role of the {@link Person}
	 *
	 * @param roles role of the {@link Person}
	 */
	public void setRoles(List<OntologyAnnotation> roles) {
		roles.stream().forEach(Objects::requireNonNull);
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "<Person> '" + this.firstName + " " + this.lastName + "'";
	}

}
