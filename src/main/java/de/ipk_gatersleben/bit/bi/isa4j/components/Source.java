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
			throw new RedundantItemException("Multiple entries for Characteristic: " + characteristic.getCategory());
		
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
		
		for(Characteristic characteristic : this.characteristics) {
			List<String> characteristicColumns = new ArrayList<String>(3);
			String characteristicName = StringUtil.putNameInAttribute(StudyAssayAttribute.CHARACTERISTICS, characteristic.getCategory());		
			characteristicColumns.add(characteristicName);
			characteristicColumns.addAll(this.getOntologyAnnotationExtensionHeaders(characteristic, c -> c.getValue()));
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
			characteristicFields.addAll(this.getOntologyAnnotationExtensionFields(characteristic, c -> c.getValue()));
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
	
}
