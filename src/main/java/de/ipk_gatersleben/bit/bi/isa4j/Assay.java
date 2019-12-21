/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j;

import de.ipk_gatersleben.bit.bi.isa.components.*;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Props;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa.io.ThreadPool;
import de.ipk_gatersleben.bit.bi.isa.io.Writer;
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Class to represent a assay in context of the ISA hierarchy. It is used to
 * describe the measured traits and belong to a {@link Study}
 *
 * @author liufe, arendd
 */
public class Assay extends Commentable {

	/**
	 * id of assay
	 */
	private String identifier;

	/**
	 * The file name of this {@link Assay}
	 */
	private String fileName;

	/**
	 * The measurement being observed in this assay
	 */
	private OntologyAnnotation measurementType;

	/**
	 * @return the measurementType
	 */
	public OntologyAnnotation getMeasurementType() {
		return measurementType;
	}

	/**
	 * The technology being employed to observe this measurement
	 */
	private OntologyAnnotation technologyType;

	/**
	 * The technology platform used
	 */
	private String technologyPlatform;


	/**
	 * rows of assay file
	 */
	private List<RowOfAssay> rowsOfAssay = new Vector<>(1000, 1000);

	/**
	 * the study, who has this assay
	 */
	private Study study;

	private List<Characteristic> templateOfCharacteristic = new ArrayList<>();
	private List<Comment> templateOfComment = new ArrayList<>();
	private List<Protocol> templateOfProtocol = new ArrayList<>();
	private Map<String, List<Parameter>> templateOfParameter = new HashMap<>();
	private Map<String, List<Characteristic>> templateOfCharacteristicInProtocol = new HashMap<>();
	private List<Factor> templateOfFactor = new ArrayList<>();
	private List<Characteristic> templateOfSampleCharacteristic = new ArrayList<>();

	private boolean imageFileFlag, rawDataFileFlag, derivedDataFileFlag;

	/**
	 * Constructor, give file name
	 *
	 * @param fileName file name of assay
	 */
	public Assay(String fileName) {
		// TODO BAD!
		if (!fileName.endsWith(".txt")) {
			fileName += ".txt";
		}
		this.fileName = fileName;
	}

	/**
	 * add a new row to row list
	 *
	 * @param r the new row
	 */
	public void addRowOfAssay(RowOfAssay r) {
		this.rowsOfAssay.add(r);
	}


	/**
	 * get file name of assay
	 *
	 * @return file name of assay
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * get id of assay
	 *
	 * @return id of assay
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * get type of measurement
	 *
	 * @return type of measurement
	 */
	public OntologyAnnotation measurementType() {
		return measurementType;
	}

	/**
	 * get data of assay
	 *
	 * @return data of assay
	 */
	public List<RowOfAssay> getRowsOfAssay() {
		return rowsOfAssay;
	}

	/**
	 * get the study, that own this assay
	 *
	 * @return
	 */
	public Study getStudy() {
		return study;
	}

	/**
	 * get type of technology
	 *
	 * @return type of technology
	 */
	public OntologyAnnotation getTechnologyType() {
		return technologyType;
	}

	/**
	 * get type of technology platform
	 *
	 * @return type of technology platform
	 */
	public String getTechnologyPlatform() {
		return technologyPlatform;
	}

	/**
	 * set id of assay
	 *
	 * @param iD id of assay
	 */
	public void setIdentifier(String iD) {
		identifier = iD;
	}

	/**
	 * set type of measurement
	 *
	 * @param measurementOntology type of measurement
	 */
	public void setMeasurementType(OntologyAnnotation measurementOntology) {
		this.measurementType = measurementOntology;
	}

	/**
	 * set data of assay
	 *
	 * @param data data of assay
	 */
	public void setRowsOfAssay(List<RowOfAssay> data) {
		this.rowsOfAssay = data;
	}

	protected void setStudy(Study study) {
		this.study = study;
	}

	/**
	 * set type of technology
	 *
	 * @param technologyType type of technology
	 */
	public void setTechnologyType(OntologyAnnotation technologyOntology) {
		this.technologyType = technologyOntology;
	}

	/**
	 * set type of technology platform
	 *
	 * @param technologyPlatform type of technology platform
	 */
	public void setTechnologyPlatform(String technologyPlatform) {
		this.technologyPlatform = technologyPlatform;
	}
}
