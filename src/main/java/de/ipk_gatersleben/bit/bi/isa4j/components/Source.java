package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class Source extends StudyOrAssayTableObject {
	
	private String name;
	private List<Characteristic> characteristics = new ArrayList<Characteristic>();
	public Source(String name) {
		this.name = name;
	}
	public Source(String name, List<Characteristic> characteristics) {
		Objects.requireNonNull(characteristics);
		this.name = name;
		this.characteristics = characteristics;
	}
	public void addCharacteristic(Characteristic characteristic) {
		if(this.characteristics.stream().map(Characteristic::getCategory).anyMatch(characteristic.getCategory()::equals))
			throw new RedundantItemException("Characteristic category not unique: " + characteristic.getCategory());
		
		this.characteristics.add(characteristic);
	}
	/**
	 * @return the characteristics
	 */
	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	protected LinkedHashMap<String, String[]> getHeadersForCharacteristics() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		// TODO maybe use a TreeSet instead of sorting here?
		Collections.sort(this.characteristics, (a,b) -> a.getCategory().compareToIgnoreCase(b.getCategory()));
		for(Characteristic characteristic : this.characteristics) {
			List<String> characteristicColumns = new ArrayList<String>(3);
			String characteristicName = StringUtil.putNameInAttribute(StudyAssayAttribute.CHARACTERISTICS, characteristic.getCategory());		
			characteristicColumns.add(characteristicName);
			if(characteristic.getValue().getSourceREF() != null)
				characteristicColumns.add(StudyAssayAttribute.TERM_SOURCE_REF.toString());
			if(characteristic.getValue().getTermAccession() != null)
				characteristicColumns.add(StudyAssayAttribute.TERM_ACCESSION_NUMBER.toString());
			
			headers.put(characteristicName, characteristicColumns.toArray(new String[0]));
		}
		
		return headers;
	}
	
	public LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(StudyAssayAttribute.SOURCE_NAME.toString(), new String[]{StudyAssayAttribute.SOURCE_NAME.toString()});
		headers.putAll(this.getHeadersForCharacteristics());
		
		return headers;
	}
	
	protected Map<String, String[]> getFieldsForCharacteristics() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		for(Characteristic characteristic : this.characteristics) {
			String characteristicName = StringUtil.putNameInAttribute(StudyAssayAttribute.CHARACTERISTICS, characteristic.getCategory());
			List<String> characteristicFields = new ArrayList<String>(3);
			characteristicFields.add(characteristic.getValue().getTerm());
			if(characteristic.getValue().getSourceREF() != null)
				characteristicFields.add(characteristic.getValue().getSourceREF().getName());
			if(characteristic.getValue().getTermAccession() != null)
				characteristicFields.add(characteristic.getValue().getTermAccession());
			fields.put(characteristicName, characteristicFields.toArray(new String[0]));
		}
		
		return fields;
	}
	
	public Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(StudyAssayAttribute.SOURCE_NAME.toString(), new String[]{this.name});
		fields.putAll(this.getFieldsForCharacteristics());

		return fields;
	}
	
	
	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(List<Characteristic> characteristics) {
		characteristics.stream().forEach(Objects::requireNonNull);
		this.characteristics = characteristics;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
//	private StudyOrAssayTableObject nextItem;
//	/**
//	 * @return the nextItem
//	 */
//	public StudyOrAssayTableObject getNextStudyOrAssayTableObject() {
//		return nextItem;
//	}
//	/**
//	 * @param nextItem the nextItem to set
//	 */
//	public void setNextStudyOrAssayTableObject(StudyOrAssayTableObject nextItem) {
//		this.nextItem = nextItem;
//	}
	
}
