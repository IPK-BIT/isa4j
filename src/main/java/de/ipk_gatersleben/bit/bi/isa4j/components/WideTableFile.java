package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import de.ipk_gatersleben.bit.bi.isa4j.constants.Props;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;

public abstract class WideTableFile implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	public CommentCollection comments() {
		return this.comments;
	}
	
	/**
	 * Constructor, give the identifier and filename
	 *
	 * @param identifier
	 * @param fileName
	 */
	public WideTableFile(String identifier, String fileName) {
		this.setIdentifier(identifier);
		this.setFileName(fileName);
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = Objects.requireNonNull(identifier, "Study identifier cannot be null");
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = Objects.requireNonNull(fileName, "Study filename cannot be null");
	}
	
	/**
	 * Get filename of study
	 *
	 * @return filename of study
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Get id of study
	 *
	 * @return id of study
	 */
	public String getIdentifier() {
		return identifier;
	}
	

	/**
	 * A user defined identifier for the study.
	 */
	private String identifier;

	/**
	 * The name of the study sample file linked to this Study.
	 */
	private String fileName;
	
	private OutputStreamWriter outputstreamwriter;
	
	public void openFile() throws FileNotFoundException {
		this.directToStream(new FileOutputStream(this.fileName));
	}
	
	public void directToStream(OutputStream os) {
		if(this.outputstreamwriter != null) {
			throw new IllegalStateException("A file or stream is already being written to. Please close/release it first!");
		}
		this.outputstreamwriter = new OutputStreamWriter(os, Props.DEFAULT_CHARSET);
	}
	
	public void releaseStream() throws IOException {
		this.outputstreamwriter.flush();
		this.outputstreamwriter = null;
		this.headers = null;
	}
	
	private ArrayList<LinkedHashMap<String, String[]>> headers = null;
	public void writeHeadersFromExample(StudyOrAssayTableObject example) throws IOException {
		if(this.outputstreamwriter == null)
			throw new IllegalStateException("No file or stream open for writing");
		
		this.headers = new ArrayList<LinkedHashMap<String, String[]>>();
		StringBuilder sb = new StringBuilder();
		while(example != null) {
			LinkedHashMap<String, String[]> headers = example.getHeaders();
			this.headers.add(headers);
			sb.append(headers.values().stream().map(o -> String.join(Symbol.TAB.toString(), o)).collect(Collectors.joining(Symbol.TAB.toString())));
			example = example.getNextStudyOrAssayTableObject();
			if(example != null)
				sb.append(Symbol.TAB.toString());
		}
		this.outputstreamwriter.write(sb.toString() + Symbol.ENTER);
	}
	
	public void writeLine(StudyOrAssayTableObject initiator) throws IOException {
		if(this.outputstreamwriter == null)
			throw new IllegalStateException("No file or stream open for writing");
		if(this.headers == null)
			throw new IllegalStateException("Headers were not written yet");
		StringBuilder sb = new StringBuilder();
		for(LinkedHashMap<String, String[]> currentObject : this.headers) {
			Objects.requireNonNull(initiator, "The line is missing object(s) defined in the header");
			Map<String, String[]> fields = initiator.getFields();
			sb.append(currentObject.keySet().stream().map(o -> {
				if(currentObject.get(o).length != fields.get(o).length)
					throw new IllegalStateException("Number of columns in header don't match number of columns for " + o);
				String partOfLine = String.join(Symbol.TAB.toString(), fields.get(o));
				// Now we delete the entry from fields so that we know when there's any left in the end, we are missing headers
				fields.remove(o);
				return partOfLine;  }
				).collect(Collectors.joining(Symbol.TAB.toString())));

			if(fields.size() > 0)
				System.err.println("There were unused fields for Object" + initiator + ": " + String.join(",", fields.keySet()));
				// TODO use proper logging
			
			initiator = initiator.getNextStudyOrAssayTableObject();
			if(initiator != null)
				sb.append(Symbol.TAB.toString());
		}
		this.outputstreamwriter.write(sb.toString() + Symbol.ENTER);
	}
	
	public boolean hasWrittenHeaders() {
		return this.headers != null;
	}
	
	public void closeFile() throws IOException {
		this.releaseStream();
		this.outputstreamwriter.close();
	}
}
