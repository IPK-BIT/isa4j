/**
 * Copyright (c) 2020 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

/**
 * Class to represent an assay in the ISA hierarchy. It is used to describe the
 * measured traits and belongs to a {@link Study}
 * 
 * @author psaroudakis
 *
 */
public class Assay extends WideTableFile implements Commentable {

	/**
	 * the measurement being observed in this assay
	 */
	private OntologyAnnotation measurementType;

	/**
	 * the study this assay belongs to
	 */
	private Study study;

	/**
	 * the technology platform used
	 */
	private String technologyPlatform;

	/**
	 * the technology being employed to observe this measurement
	 */
	private OntologyAnnotation technologyType;

	/**
	 * Constructor, pass the filename the assay will be written to when using the
	 * writeToFile method
	 *
	 * @param fileName the name of the assay file
	 */
	public Assay(String fileName) {
		super(fileName);
	}

	/**
	 * @return the measurementType
	 */
	public OntologyAnnotation getMeasurementType() {
		return measurementType;
	}

	/**
	 * @return the study this assay belongs to
	 */
	public Study getStudy() {
		return study;
	}

	/**
	 * @return type of technology platform
	 */
	public String getTechnologyPlatform() {
		return technologyPlatform;
	}

	/**
	 * @return type of technology
	 */
	public OntologyAnnotation getTechnologyType() {
		return technologyType;
	}

	/**
	 * @return type of measurement
	 */
	public OntologyAnnotation measurementType() {
		return measurementType;
	}

	/**
	 * @param measurementType type of measurement
	 */
	public void setMeasurementType(OntologyAnnotation measurementType) {
		this.measurementType = measurementType;
	}

	/**
	 * @param study the associated {@link Study} to set
	 */
	protected void setStudy(Study study) {
		this.study = study;
	}

	/**
	 * @param technologyPlatform type of technology platform
	 */
	public void setTechnologyPlatform(String technologyPlatform) {
		this.technologyPlatform = StringUtil.sanitize(technologyPlatform);
	}

	/**
	 * @param technologyType type of technology
	 */
	public void setTechnologyType(OntologyAnnotation technologyType) {
		this.technologyType = technologyType;
	}

	@Override
	public String toString() {
		return "<Assay> '" + this.getFileName() + "'";
	}
}
