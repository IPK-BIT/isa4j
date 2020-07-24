/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;

public class Process extends StudyOrAssayTableObject implements Commentable {

	private CommentCollection comments = new CommentCollection();

	private LocalDate date;

	private LocalDateTime dateTime;

	private StudyOrAssayTableObject input;

	private List<ParameterValue> parameterValues = new ArrayList<ParameterValue>();

	private Protocol protocol;

	public Process(Protocol protocol) {
		this.setProtocol(protocol);
	}

	public void addParameterValue(ParameterValue parameterValue) {
		if (this.parameterValues.stream().map(ParameterValue::getCategory)
				.anyMatch(parameterValue.getCategory()::equals))
			throw new RedundantItemException(
					"Multiple ParameterValues for Parameter: " + parameterValue.getCategory().getName());
		this.parameterValues.add(parameterValue);
	}

	public CommentCollection comments() {
		return this.comments;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return the dateTime
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();

		fields.put(StudyAssayAttribute.PROTOCOL.toString(), new String[] { this.protocol.getName() });
		fields.putAll(this.getFieldsForValues(StudyAssayAttribute.PARAMETER_VALUE, this.parameterValues,
				pv -> pv.getCategory().getName().getTerm()));
		if (this.dateTime != null)
			fields.put(StudyAssayAttribute.PROTOCOL_DATE.toString(), new String[] { this.dateTime.toString() });
		else if (this.date != null)
			fields.put(StudyAssayAttribute.PROTOCOL_DATE.toString(), new String[] { this.date.toString() });
		fields.putAll(this.getFieldsForComments(this.comments));

		return fields;
	}

	LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();

		headers.put(StudyAssayAttribute.PROTOCOL.toString(), new String[] { StudyAssayAttribute.PROTOCOL.toString() });
		headers.putAll(this.getHeadersForValues(StudyAssayAttribute.PARAMETER_VALUE, this.parameterValues,
				pv -> pv.getCategory().getName().getTerm()));
		if (this.dateTime != null || this.date != null)
			headers.put(StudyAssayAttribute.PROTOCOL_DATE.toString(),
					new String[] { StudyAssayAttribute.PROTOCOL_DATE.toString() });
		headers.putAll(this.getHeadersForComments(this.comments));

		return headers;
	}

	public StudyOrAssayTableObject getInput() {
		return this.input;
	}

	public StudyOrAssayTableObject getOutput() {
		return this.getNextStudyOrAssayTableObject();
	}

	/**
	 * @return the factorValues
	 */
	public List<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	/**
	 * @return the protocol
	 */
	public Protocol getProtocol() {
		return protocol;
	}

	/**
	 * Sets a Date for this Process (without time of day). Overwrites any Date or
	 * DateTime set before.
	 * 
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
		this.dateTime = null;
	}

	/**
	 * Sets a DateTime for this Process. Overwrites any Date or DateTime set before.
	 * 
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		this.date = null;
	}

	/**
	 * Declares the input to this Process. This information is used to link
	 * different entities together in the output Study and Assay Files through
	 * Processes, e.g. Source-&gt;Process-&gt;Sample.
	 * 
	 * @param input to set
	 */
	public void setInput(StudyOrAssayTableObject input) {
		// Remove myself from previously defined input
		if (this.input != null)
			this.input.setNextStudyOrAssayTableObject(null);
		input.setNextStudyOrAssayTableObject(this);
		this.input = input;
	}

	/**
	 * Declares the output of this Process. This information is used to link
	 * different entities together in the output Study and Assay Files through
	 * Processes, e.g. Source-&gt;Process-&gt;Sample.
	 * 
	 * @param output to set
	 */
	public void setOutput(StudyOrAssayTableObject output) {
		this.setNextStudyOrAssayTableObject(output);
	}

	/**
	 * @param parameterValues the factorValues to set
	 */
	public void setParameterValues(List<ParameterValue> parameterValues) {
		parameterValues.stream().forEach(Objects::requireNonNull);
		this.parameterValues = parameterValues;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(Protocol protocol) {
		this.protocol = Objects.requireNonNull(protocol, "Protocol cannot be null");
	}

	@Override
	public String toString() {
		return "<Process> '" + this.protocol.getName() + "' on input " + this.input.toString();
	}

}
