package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
		this.type = Objects.requireNonNull(type, "Type cannot be null");
	}
	
	public LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(this.type, new String[]{this.type});
		headers.putAll(this.getHeadersForCharacteristics());
		
		return headers;
	}
	
	
	public Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(this.type, new String[]{this.name});
		fields.putAll(this.getFieldsForCharacteristics());

		return fields;
	}

}
