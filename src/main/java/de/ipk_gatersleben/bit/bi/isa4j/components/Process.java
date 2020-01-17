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
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class Process extends StudyOrAssayTableObject implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	public CommentCollection comments() {
		return this.comments;
	}
	
	private Protocol protocol;
	
	public Process(Protocol protocol) {
		this.setProtocol(protocol);
	}
	
	private LinkedHashMap<String, String[]> getHeadersForParameterValues() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		for(ParameterValue parameterValue : this.parameterValues) {
			List<String> parameterValueColumns = new ArrayList<String>(3);
			String parameterValueName = StringUtil.putNameInAttribute(StudyAssayAttribute.PARAMETER_VALUE, parameterValue.getCategory().getName());		
			parameterValueColumns.add(parameterValueName);
			parameterValueColumns.addAll(this.getOntologyAnnotationExtensionHeaders(parameterValue, c -> c.getValue()));
			if(parameterValue.getUnit() != null) {
				parameterValueColumns.add(StudyAssayAttribute.UNIT.toString());
				parameterValueColumns.addAll(this.getOntologyAnnotationExtensionHeaders(parameterValue, c -> c.getUnit()));				
			}
			headers.put(parameterValueName, parameterValueColumns.toArray(new String[0]));
		}
		
		return headers;
	}
	
	private HashMap<String, String[]> getFieldsForParameterValues() {
		HashMap<String, String[]> headers = new HashMap<String, String[]>();
		
		for(ParameterValue parameterValue : this.parameterValues) {
			List<String> parameterValueFields = new ArrayList<String>(3);
			String parameterValueName = StringUtil.putNameInAttribute(StudyAssayAttribute.PARAMETER_VALUE, parameterValue.getCategory().getName());		
			parameterValueFields.add(parameterValue.getValue().getTerm());
			parameterValueFields.addAll(this.getOntologyAnnotationExtensionFields(parameterValue, c -> c.getValue()));
			if(parameterValue.getUnit() != null) {
				parameterValueFields.add(parameterValue.getUnit().getTerm());
				parameterValueFields.addAll(this.getOntologyAnnotationExtensionFields(parameterValue, c -> c.getUnit()));				
			}
			headers.put(parameterValueName, parameterValueFields.toArray(new String[0]));
		}
		
		return headers;
	}
	
	public LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(StudyAssayAttribute.PROTOCOL.toString(), new String[]{StudyAssayAttribute.PROTOCOL.toString()});
		headers.putAll(this.getHeadersForParameterValues());
		
		return headers;
	}
	
	public Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(StudyAssayAttribute.PROTOCOL.toString(), new String[]{this.protocol.getName()});
		fields.putAll(this.getFieldsForParameterValues());

		return fields;
	}
	
	private List<ParameterValue> parameterValues = new ArrayList<ParameterValue>();
	
	/**
	 * @return the factorValues
	 */
	public List<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	/**
	 * @param parameterValues the factorValues to set
	 */
	public void setParameterValues(List<ParameterValue> parameterValues) {
		parameterValues.stream().forEach(Objects::requireNonNull);
		this.parameterValues = parameterValues;
	}
	
	public void addParameterValue(ParameterValue parameterValue) {
		if(this.parameterValues.stream().map(ParameterValue::getCategory).anyMatch(parameterValue.getCategory()::equals))
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
