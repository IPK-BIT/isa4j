package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class Source implements StudyOrAssayTableObject {
	
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
	
	// TODO this method and the next one are horribly untidy 
	public LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> ret = new LinkedHashMap<String, String[]>();
		
		ret.put(StudyAssayAttribute.SOURCE_NAME.toString(), new String[]{StudyAssayAttribute.SOURCE_NAME.toString()});
		for(Characteristic characteristic : this.characteristics) {
			String key = StringUtil.putNameInAttribute(StudyAssayAttribute.CHARACTERISTICS, characteristic.getCategory());
			List<String> values = new ArrayList<String>(3);
			values.add(key);
			if(characteristic.getValue().getSourceREF() != null)
				values.add(StudyAssayAttribute.TERM_SOURCE_REF.toString());
			if(characteristic.getValue().getTermAccession() != null)
				values.add(StudyAssayAttribute.TERM_ACCESSION_NUMBER.toString());
			ret.put(key, values.toArray(new String[0]));
		}
		
		return ret;
	}
	
	public Map<String, String[]> getFields() {
		HashMap<String, String[]> ret = new HashMap<String, String[]>();
		
		ret.put(StudyAssayAttribute.SOURCE_NAME.toString(), new String[]{this.name});
		for(Characteristic characteristic : this.characteristics) {
			String key = StringUtil.putNameInAttribute(StudyAssayAttribute.CHARACTERISTICS, characteristic.getCategory());
			List<String> values = new ArrayList<String>(3);
			values.add(characteristic.getValue().getTerm());
			if(characteristic.getValue().getSourceREF() != null)
				values.add(characteristic.getValue().getSourceREF().getName());
			if(characteristic.getValue().getTermAccession() != null)
				values.add(characteristic.getValue().getTermAccession());
			ret.put(key, values.toArray(new String[0]));
		}
		
		return ret;
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
