/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j;

import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;

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
	 * the study, who has this assay
	 */
	private Study study;

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
