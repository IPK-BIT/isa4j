/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ipk_gatersleben.bit.bi.isa4j.constants.Props;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

public abstract class WideTableFile implements Commentable {

	private CommentCollection comments = new CommentCollection();

	private String fileName;

	private ArrayList<LinkedHashMap<String, String[]>> headers = null;

	private final Logger logger = LoggerFactory.getLogger(WideTableFile.class);
	
	private OutputStreamWriter outputstreamwriter;
	
	/**
	 * Constructor, give the filename
	 *
	 * @param fileName the name of the file
	 */
	public WideTableFile(String fileName) {
		this.setFileName(fileName);
	}

	/**
	 * Closes the file and forgets all headers.
	 * 
	 * @throws IOException is unable to close file
	 */
	public void closeFile() throws IOException {
		logger.debug("{}: Closing output file.", this);
		this.outputstreamwriter.close();
		this.outputstreamwriter = null;
		this.headers = null;
	}

	public CommentCollection comments() {
		return this.comments;
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
	 * Returns true if headers have already been written to file/stream. Can be used
	 * within a loop to make sure headers are exactly written once
	 * 
	 * @return if the headers are written
	 */
	public boolean hasWrittenHeaders() {
		return this.headers != null;
	}

	public void openFile() throws FileNotFoundException {
		logger.debug("{}: Directing output to File '{}'.", this, this.fileName);
		this.setOutputStream(new FileOutputStream(this.fileName));
	}

	public void releaseStream() throws IOException {
		logger.debug("{}: Releasing output stream.", this);	
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

	public void setOutputStream(OutputStream os) {
		if (this.outputstreamwriter != null) {
			throw new IllegalStateException(
					"A file or stream is already being written to. Please close/release it first!");
		}
		this.outputstreamwriter = new OutputStreamWriter(os, Props.DEFAULT_CHARSET);
	}

	public void writeHeadersFromExample(StudyOrAssayTableObject example) throws IOException {
		if (this.outputstreamwriter == null)
			throw new IllegalStateException("No file or stream open for writing");
		if (this.hasWrittenHeaders())
			throw new IllegalStateException("Headers were already written to this file or stream");

		this.headers = new ArrayList<LinkedHashMap<String, String[]>>();
		StringBuilder sb = new StringBuilder();
		while (example != null) {
			LinkedHashMap<String, String[]> headers = example.getHeaders();
			this.headers.add(headers);
			sb.append(headers.values().stream().map(o -> String.join(Symbol.TAB.toString(), o))
					.collect(Collectors.joining(Symbol.TAB.toString())));
			example = example.getNextStudyOrAssayTableObject();
			if (example != null)
				sb.append(Symbol.TAB.toString());
		}
		
		logger.debug("{}: Writing these headers to output: [{}]", this,
			this.headers.stream().map(
				t -> "{" + t.keySet().stream().map(
					k -> k + " = " + Arrays.toString(t.get(k)) )
				.collect(Collectors.joining(", ")) + "}")
			.collect(Collectors.joining(", ")));
		
		this.outputstreamwriter.write(sb.toString() + Symbol.ENTER);
	}

	public void writeLine(StudyOrAssayTableObject initiator) throws IOException {
		if (this.outputstreamwriter == null)
			throw new IllegalStateException("No file or stream open for writing");
		if (this.headers == null)
			throw new IllegalStateException("Headers were not written yet");

		StringBuilder sb = new StringBuilder();
		StudyOrAssayTableObject currentObject = initiator;
		// Loop through header groups and objects at the same time (see below where
		// currentObject = currentObject.getNextStudyOrAssayTableObject();
		// each header group corresponds to one object (Sample, Process ...)
		// So a header group for a Source could for example look like this:
		// {
		// "Source Name" => ["Source Name"],
		// "Characteristic[Organism]" => ["Characteristic [Organism]", "Term Source
		// REF", "Term Accession Number"],
		// "Characteristic[Genotype]" => ["Characteristic [Genotype]"]
		// }
		for (LinkedHashMap<String, String[]> currentHeaderGroup : this.headers) {
			// This happens if we have header groups left but no more currentObjects in the
			// line
			Objects.requireNonNull(currentObject,
					"This line contains fewer objects (Sources, Samples, Processes...) than were defined in the header."
							+ "\n Please make sure your line structure is uniform (e.g. Sample->Process->Material->Process->DataFile for ALL lines) and everything is linked with Processes correctly.");

			Map<String, String[]> fields = currentObject.getFields();

			sb.append(currentHeaderGroup.keySet().stream().map(o -> {
				if (currentHeaderGroup.get(o).length != fields.get(o).length)
					throw new IllegalStateException("Object has "
							+ (currentHeaderGroup.get(o).length > fields.get(o).length ? "fewer" : "more")
							+ "columns than header for " + o
							+ "\n Please make sure that every object contains the same information as the examplary objects that were passed to writeHeadersFromExample."
							+ "This error mostly occurs when only some objects of the same column (e.g. a specific Process ParameterValue) have Term Source Refs and Term Accession numbers.");
				String partOfLine = String.join(Symbol.TAB.toString(), fields.get(o));
				// Now we delete the entry from fields so that we know when there's any left in
				// the end, we are missing headers
				fields.remove(o);
				return partOfLine;
			}).collect(Collectors.joining(Symbol.TAB.toString())));

			if(fields.size() > 0)
				logger.warn("{}: There were fields for Object {} that had no corresponding header. They were ignored: {}", this, currentObject, String.join(", ", fields.keySet()));
			
			currentObject = currentObject.getNextStudyOrAssayTableObject();
			if (currentObject != null)
				sb.append(Symbol.TAB.toString());
		}
		this.outputstreamwriter.write(sb.toString() + Symbol.ENTER);
	}
}
