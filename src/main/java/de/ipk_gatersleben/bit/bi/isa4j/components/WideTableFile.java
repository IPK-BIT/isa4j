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
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public abstract class WideTableFile implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	/**
	 * The name of the study sample file linked to this Study.
	 */
	private String fileName;
	
	private ArrayList<LinkedHashMap<String, String[]>> headers = null;

	private OutputStreamWriter outputstreamwriter;
	
	/**
	 * Constructor, give the filename
	 *
	 * @param fileName
	 */
	public WideTableFile(String fileName) {
		this.setFileName(fileName);
	}

	public void closeFile() throws IOException {
		this.outputstreamwriter.close();
		this.outputstreamwriter = null;
		this.headers = null;
	}
	
	public CommentCollection comments() {
		return this.comments;
	}
	
	public void directToStream(OutputStream os) {
		if(this.outputstreamwriter != null) {
			throw new IllegalStateException("A file or stream is already being written to. Please close/release it first!");
		}
		this.outputstreamwriter = new OutputStreamWriter(os, Props.DEFAULT_CHARSET);
	}
	
	/**
	 * Get filename of study
	 *
	 * @return filename of study
	 */
	public String getFileName() {
		return fileName;
	}
	
	public boolean hasWrittenHeaders() {
		return this.headers != null;
	}
	
	public void openFile() throws FileNotFoundException {
		this.directToStream(new FileOutputStream(this.fileName));
	}
	public void releaseStream() throws IOException {
		this.outputstreamwriter.flush();
		this.outputstreamwriter = null;
		this.headers = null;
	}
	
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = StringUtil.sanitize(Objects.requireNonNull(fileName, "Filename cannot be null"));
	}
	
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
}
