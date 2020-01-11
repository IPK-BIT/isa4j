package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.ipk_gatersleben.bit.bi.isa4j.constants.StudyAssayAttribute;

public class Sample extends Source {
	
	public Sample(String name) {
		super(name);
	}
	
	public Sample(String name, List<Characteristic> characteristics) {
		super(name, characteristics);
	}
	
	public LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(StudyAssayAttribute.SAMPLE_NAME.toString(), new String[]{StudyAssayAttribute.SAMPLE_NAME.toString()});
		headers.putAll(this.getHeadersForCharacteristics());
		
		return headers;
	}
	
	public Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(StudyAssayAttribute.SAMPLE_NAME.toString(), new String[]{this.getName()});
		fields.putAll(this.getFieldsForCharacteristics());

		return fields;
	}
	
}
