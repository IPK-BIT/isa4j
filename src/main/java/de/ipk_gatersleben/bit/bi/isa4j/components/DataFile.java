package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DataFile extends StudyOrAssayTableObject implements Commentable { 
	
	private CommentCollection comments = new CommentCollection();
	
	private String path;
	
	private String type;
	
	/**
	 * Creates a new Data File.
	 * @param type Type of DataFile, will be used for the column header. Ideally one of ISATab's predefined types but that's not checked.
	 * @param path Path to the file as should be printed in the ISATab. Existance or validity of path is not checked.
	 */
	public DataFile(String type, String path) {
		this.setType(type);
		this.setPath(path);
	}
	
	public CommentCollection comments() {
		return this.comments;
	}

	/**
	 * Please refer to documentation on StudyOrAssayTableObject.getFields
	 */
	Map<String, String[]> getFields() {
		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		
		fields.put(this.type, new String[]{this.path});
		fields.putAll(this.getFieldsForComments(this.comments));

		return fields;
	}
	
	/**
	 * Please refer to documentation on StudyOrAssayTableObject.getHeaders
	 */
	LinkedHashMap<String, String[]> getHeaders() {
		LinkedHashMap<String, String[]> headers = new LinkedHashMap<String, String[]>();
		
		headers.put(this.type, new String[]{this.type});
		headers.putAll(this.getHeadersForComments(this.comments));
		
		return headers;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param path Path to the file as should be printed in the ISATab. Existance or validity of path is not checked.
	 */
	public void setPath(String path) {
		this.path = Objects.requireNonNull(path, "Path cannot be null");
	}
	
	
	/**
	 * @param type Type of DataFile, will be used for the column header. Ideally one of ISATab's predefined types but that's not checked.
	 */
	public void setType(String type) {
		this.type = Objects.requireNonNull(type, "Type cannot be null");
	}

}
