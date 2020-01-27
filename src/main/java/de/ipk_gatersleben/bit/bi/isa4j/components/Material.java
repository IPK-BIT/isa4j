/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public class Material extends Source {
	private String type;
	
	public Material(String type, String name) {
		super(name);
		this.setType(type);
	}
	
	public Material(String type, String name, List<Characteristic> characteristics) {
		super(name, characteristics);
		this.setType(type);
	}

	Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(this.type, new String[]{this.name});
		fields.putAll(this.getFieldsForCharacteristics());

		return fields;
	}

	LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(this.type, new String[]{this.type});
		headers.putAll(this.getHeadersForCharacteristics());
		
		return headers;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = StringUtil.sanitize(Objects.requireNonNull(type, "Type cannot be null"));
	}

}
