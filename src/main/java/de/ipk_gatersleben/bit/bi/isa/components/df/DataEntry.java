package de.ipk_gatersleben.bit.bi.isa.components.df;

import java.util.Calendar;
import java.util.Map;

import de.ipk_gatersleben.bit.bi.isa.components.tdf.Trait;

public class DataEntry {

	private String observationUnit;
	private String assayName;
	private Map<Trait, String> values;
	private Calendar date;

	public String getObservationUnit() {
		return observationUnit;
	}

	public void setObservationUnit(String observationUnit) {
		this.observationUnit = observationUnit;
	}

	public String getAssayName() {
		return assayName;
	}

	public void setAssayName(String assayName) {
		this.assayName = assayName;
	}

	public Map<Trait, String> getValues() {
		return values;
	}

	public void setValues(Map<Trait, String> values) {
		this.values = values;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public DataEntry(String observationUnit, String assayName, Map<Trait, String> values, Calendar date) {

		this.observationUnit = observationUnit;
		this.assayName = assayName;
		this.values = values;
		this.date = date;

	}

}
