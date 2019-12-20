/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import de.ipk_gatersleben.bit.bi.isa.Investigation;
import de.ipk_gatersleben.bit.bi.isa.components.NonNumericFactor;
import de.ipk_gatersleben.bit.bi.isa.components.NumericFactor;
import de.ipk_gatersleben.bit.bi.isa4j.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a factor value to describe an observed in the
 * {@link Study} or {@link Assay}, independent from a {@link Protocol}
 *
 * @author liufe, arendd
 */

public abstract class Factor extends Commentable {

	/**
	 * Mandatory attributes for the study factor entries in the
	 * {@link Investigation} file.
	 */
	public enum Attributes {
		NAME, TYPE, TYPE_TERM_ACCESSION_NUMBER, TYPE_TERM_SOURCE_REF
	}

	/**
	 * {@link OntologyAnnotation} description of {@link Factor}
	 */
	private OntologyAnnotation ontologyTermOfType;

	/**
	 * Get {@link OntologyAnnotation} for the type of the {@link Factor}
	 *
	 * @return the {@link OntologyAnnotation} for the type of this {@link Factor}
	 */
	public OntologyAnnotation getOntologyTermOfType() {
		return ontologyTermOfType;
	}

	/**
	 * Set {@link OntologyAnnotation} of the {@link Factor} type
	 *
	 * @param ontologyTermOfType {@link OntologyAnnotation} of the {@link Factor} type
	 */
	public void setOntologyTermOfType(OntologyAnnotation ontologyTermOfType) {
		if (ontologyTermOfType == null) {
			return;
		}
		this.ontologyTermOfType = ontologyTermOfType;
	}
}
