/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
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

public class Source extends StudyOrAssayTableObject implements Commentable {
	
	private List<Characteristic> characteristics = new ArrayList<Characteristic>();
	
	private CommentCollection comments = new CommentCollection();
	
	protected String name;
	public Source(String name) {
		this.setName(name);
	}
	public Source(String name, List<Characteristic> characteristics) {
		this(name);
		this.setCharacteristics(characteristics);
	}
	public void addCharacteristic(Characteristic characteristic) {
		if(this.characteristics.stream().map(Characteristic::getCategory).anyMatch(characteristic.getCategory()::equals))
			throw new RedundantItemException("Multiple entries for Characteristic: " + characteristic.getCategory());
		
		this.characteristics.add(characteristic);
	}
	public CommentCollection comments() {
		return this.comments;
	}
	
	/**
	 * @return the characteristics
	 */
	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}
	Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(StudyAssayAttribute.SOURCE_NAME.toString(), new String[]{this.name});
		fields.putAll(this.getFieldsForCharacteristics());
		fields.putAll(this.getFieldsForComments(this.comments));

		return fields;
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
	
	LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(StudyAssayAttribute.SOURCE_NAME.toString(), new String[]{StudyAssayAttribute.SOURCE_NAME.toString()});
		headers.putAll(this.getHeadersForCharacteristics());
		headers.putAll(this.getHeadersForComments(this.comments));
		
		return headers;
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
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(List<Characteristic> characteristics) {
		this.characteristics.clear();
		characteristics.stream().forEach(this::addCharacteristic);
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = StringUtil.sanitize(Objects.requireNonNull(name, "Name cannot be null"));
	}
	
	@Override
	public String toString() {
		return "<Source> '" + this.name + "'";
	}
	
}
