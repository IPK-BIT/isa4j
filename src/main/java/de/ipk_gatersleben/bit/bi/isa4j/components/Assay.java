/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

/**
 * Class to represent a assay in context of the ISA hierarchy. It is used to
 * describe the measured traits and belong to a {@link Study}
 *
 * @author liufe, arendd
 */
public class Assay extends WideTableFile implements Commentable {

	/**
	 * The measurement being observed in this assay
	 */
	private OntologyAnnotation measurementType;

	/**
	 * The technology being employed to observe this measurement
	 */
	private OntologyAnnotation technologyType;

	/**
	 * The technology platform used
	 */
	private String technologyPlatform;

	/**
	 * the study, who has this assay
	 */
	private Study study;


	/**
	 * Constructor, give the identifier of study, filename is same with identifier
	 *
	 * @param identifier
	 */
	public Assay(String identifier) {
		super(identifier, "a_" + identifier + ".txt");
	}
	
	/**
	 * Constructor, give the identifier and filename
	 *
	 * @param identifier
	 * @param fileName
	 */
	public Assay(String identifier, String fileName) {
		super(identifier, fileName);
	}

	/**
	 * @return the measurementType
	 */
	public OntologyAnnotation getMeasurementType() {
		return measurementType;
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
	 * get type of technology platform
	 *
	 * @return type of technology platform
	 */
	public String getTechnologyPlatform() {
		return technologyPlatform;
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
	 * get type of measurement
	 *
	 * @return type of measurement
	 */
	public OntologyAnnotation measurementType() {
		return measurementType;
	}

	/**
	 * set type of measurement
	 *
	 * @param measurementType type of measurement
	 */
	public void setMeasurementType(OntologyAnnotation measurementType) {
		this.measurementType = measurementType;
	}

	protected void setStudy(Study study) {
		this.study = study;
	}

	/**
	 * set type of technology platform
	 *
	 * @param technologyPlatform type of technology platform
	 */
	public void setTechnologyPlatform(String technologyPlatform) {
		this.technologyPlatform = technologyPlatform;
	}

	/**
	 * set type of technology
	 *
	 * @param technologyType type of technology
	 */
	public void setTechnologyType(OntologyAnnotation technologyType) {
		this.technologyType = technologyType;
	}
}
