package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;

public class Process implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	public CommentCollection comments() {
		return this.comments;
	}
	
	private Protocol protocol;
	
	public Process(Protocol protocol) {
		this.setProtocol(protocol);
	}
	
	private List<ProcessParameterValue> parameterValues = new ArrayList<ProcessParameterValue>();
	
	/**
	 * @return the factorValues
	 */
	public List<ProcessParameterValue> getParameterValues() {
		return parameterValues;
	}

	/**
	 * @param parameterValues the factorValues to set
	 */
	public void setParameterValues(List<ProcessParameterValue> parameterValues) {
		parameterValues.stream().forEach(Objects::requireNonNull);
		this.parameterValues = parameterValues;
	}
	
	public void addParameterValue(ProcessParameterValue parameterValue) {
		if(this.parameterValues.stream().map(ProcessParameterValue::getCategory).anyMatch(parameterValue.getCategory()::equals))
			throw new RedundantItemException("Multiple ParameterValues for Parameter: " + parameterValue.getCategory().getName());
		this.parameterValues.add(parameterValue);
	}
	
	/**
	 * @return the protocol
	 */
	public Protocol getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(Protocol protocol) {
		this.protocol = Objects.requireNonNull(protocol, "Protocol cannot be null");
	}

	private LocalDate date;
	private LocalDateTime dateTime;

	/**
	 * @return the dateTime
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		this.date = null; // TODO document this behavior
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
		this.dateTime = null;
	}

}
