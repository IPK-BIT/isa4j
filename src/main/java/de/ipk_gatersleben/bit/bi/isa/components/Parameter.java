/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.components.Component;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;

/**
 * Abstract super class for defining parameters.
 *
 * @author liufe, arendd
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = NonNumericParameter.class),
		@JsonSubTypes.Type(value = NumericParameter.class) })
public abstract class Parameter extends Component {

	/**
	 * the {@link OntologyAnnotation} for the {@link Parameter} name
	 */
	private OntologyAnnotation ontologyTermOfParameterName;

	/**
	 * Constructor with a given {@link Parameter} name
	 *
	 * @param nameOfParameter the name of the {@link Parameter}
	 */
	public Parameter(String nameOfParameter) {
		super(nameOfParameter);
	}

	/**
	 * Constructor with given name and value of the {@link Parameter}
	 *
	 * @param nameOfParameter  name of {@link Parameter}
	 * @param valueOfParameter value of {@link Parameter}
	 */
	public Parameter(String nameOfParameter, String valueOfParameter) {
		super(nameOfParameter, valueOfParameter);
	}

	/**
	 * Constructor using an {@link OntologyAnnotation} for the {@link Parameter} name
	 *
	 * @param ontologyTermOfParameterName name of {@link Parameter}
	 */
	public Parameter(OntologyAnnotation ontologyTermOfParameterName) {
		super(ontologyTermOfParameterName.getTerm());
		this.ontologyTermOfParameterName = ontologyTermOfParameterName;
	}

	/**
	 * Get the {@link OntologyAnnotation} for the {@link Parameter} name
	 *
	 * @return {@link OntologyAnnotation} of the {@link Parameter} name
	 */
	public OntologyAnnotation getOntologyTermOfParameterName() {
		return ontologyTermOfParameterName;
	}

	/**
	 * Set {@link OntologyAnnotation} of the {@link Parameter} name
	 *
	 * @param ontologyTermOfName {@link OntologyAnnotation} of the {@link Parameter} name
	 */
	public void setOntologyTermOfParameterName(OntologyAnnotation ontologyTermOfName) {
		if (ontologyTermOfName != null && getTerm() != null && !ontologyTermOfName.getTerm().equals(getTerm())) {
			LoggerUtil.logger
					.error("The nameOntologyTerm of Parameter is not right! Parameter: '" + getTerm() + "' value: '"
							+ getValue() + "' " + "ontologyTerm of name: '" + ontologyTermOfName.getTerm() + "'");
			return;
		}
		this.ontologyTermOfParameterName = ontologyTermOfName;
	}

}
