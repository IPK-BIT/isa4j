/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.components;

import de.ipk_gatersleben.bit.bi.isa.constants.StudyAssayAttribute;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa4j.Study;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;

import java.util.*;

/**
 * Class to represent a single line/entry of a {@link Study} file
 *
 * @author liufe, arendd
 */
public class RowOfStudy extends Row {

	/**
	 * Name to identify the investigated biological material used in the
	 * {@link Study}
	 */
	private String sourceName;

	/**
	 * Get the source name of the {@link RowOfStudy}
	 *
	 * @return source name of the {@link RowOfStudy}
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * Set source name of the {@link RowOfStudy}
	 *
	 * @param sourceName source name of the {@link RowOfStudy}
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * The function will get the template of all components in rows. The parameters
	 * of this function are the lists of components. They should be new list and
	 * after this function they will be the templates.
	 *
	 * @param orderOfCharacteristic template of characteristics
	 * @param orderOfComment        template of comment
	 * @param orderOfProtocol       template of protocol
	 * @param orderOfParameter      template of parameter
	 * @param orderOfFactor         template of factor
	 * @param rowsOfStudy           list of rowOfStudy
	 */
	public static boolean getTemplateOfRows(List<Characteristic> orderOfCharacteristic, List<Comment> orderOfComment,
			List<Protocol> orderOfProtocol, Map<String, List<Parameter>> orderOfParameter,
			Map<String, List<Characteristic>> orderOfCharacteristicInProtocol, List<Factor> orderOfFactor,
			List<Characteristic> orderOfSampleCharacteristic, List<RowOfStudy> rowsOfStudy,
			Set<String> ontologySourceREF) {
		Map<String, Characteristic> motherOfCharacteristics = new HashMap<>();
		Map<String, Comment> motherOfComment = new HashMap<>();
		Map<String, Protocol> motherOfProtocols = new HashMap<>();
		Map<String, Map<String, Parameter>> motherOfParameters = new HashMap<>();
		Map<String, Map<String, Characteristic>> motherOfCharacteristicsInProtocol = new HashMap<>();
		Map<String, Factor> motherOfFactors = new HashMap<>();
		Map<String, Characteristic> motherOfSampleCharacteristics = new HashMap<>();

		Set<Integer> hasPositionOfCharacteristic = new HashSet<>();
		Set<Integer> orderPositionOfCharacteristic = new HashSet<>();
		Set<Integer> hasPositionOfComment = new HashSet<>();
		Set<Integer> orderPositionOfComment = new HashSet<>();

		Set<Integer> hasPositionOfProtocol = new HashSet<>();
		Set<Integer> orderPositionOfProtocol = new HashSet<>();
		HashMap<String, HashSet<Integer>> hasPositionOfParameter = new HashMap<>();
		HashMap<String, HashSet<Integer>> orderPositionOfParameter = new HashMap<>();
		HashMap<String, HashSet<Integer>> hasPositionOfCharacteristicsInProtocol = new HashMap<>();
		HashMap<String, HashSet<Integer>> orderPositionOfCharacteristicsInProtocol = new HashMap<>();

		Set<Integer> hasPositionOfFactor = new HashSet<>();
		Set<Integer> orderPositionOfFactor = new HashSet<>();
		Set<Integer> hasPositionOfSampleCharateristic = new HashSet<>();
		Set<Integer> orderPositionOfSampleCharateristic = new HashSet<>();

		for (RowOfStudy rowOfStudy : rowsOfStudy) {
			if (!rowOfStudy.addComponentsToTemplate(motherOfCharacteristics, hasPositionOfCharacteristic,
					orderPositionOfCharacteristic, motherOfComment, hasPositionOfComment, orderPositionOfComment,
					motherOfProtocols, hasPositionOfProtocol, orderPositionOfProtocol, motherOfParameters,
					hasPositionOfParameter, orderPositionOfParameter, motherOfCharacteristicsInProtocol,
					hasPositionOfCharacteristicsInProtocol, orderPositionOfCharacteristicsInProtocol, motherOfFactors,
					hasPositionOfFactor, orderPositionOfFactor, motherOfSampleCharacteristics,
					hasPositionOfSampleCharateristic, orderPositionOfSampleCharateristic, ontologySourceREF)) {
				return false;
			}
		}
		sortTemplateWithPosition(motherOfCharacteristics, orderOfCharacteristic, motherOfComment, orderOfComment,
				motherOfProtocols, orderOfProtocol, motherOfParameters, orderOfParameter,
				motherOfCharacteristicsInProtocol, orderOfCharacteristicInProtocol, motherOfFactors, orderOfFactor,
				motherOfSampleCharacteristics, orderOfSampleCharacteristic);
		return true;
	}

	/**
	 * Get attribute header of study, it uses the template to get attributes. In
	 * this function it will differentiate, which one should have ontology
	 *
	 * @param templateOfCharacteristic template of characteristic
	 * @param templateOfProtocol       template of protocol
	 * @param templateOfParameter      template of parameter
	 * @param templateOfFactor         template of factor
	 * @param templateOfComment        template of comment
	 * @return the attribute of assay
	 */
	public static String getAttributeOfStudy(List<Characteristic> templateOfCharacteristic,
			List<Protocol> templateOfProtocol, Map<String, List<Parameter>> templateOfParameter,
			Map<String, List<Characteristic>> templateOfCharacteristicInProtocol, List<Factor> templateOfFactor,
			List<Comment> templateOfComment, List<Characteristic> templateOfSampleCharacteristic) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(StudyAssayAttribute.SOURCE_NAME);
		stringBuilder.append(Characteristic.getAttributeWithTemplate(templateOfCharacteristic));
		stringBuilder.append(Protocol.getAttributeWithTemplate(templateOfProtocol, templateOfParameter,
				templateOfCharacteristicInProtocol));
		stringBuilder.append(Factor.getAttributeWithTemplate(templateOfFactor));
		stringBuilder.append(Comment.getAttributeWithTemplate(templateOfComment));
		stringBuilder.append(StudyAssayAttribute.SAMPLE_NAME);
		stringBuilder.append(Characteristic.getAttributeWithTemplate(templateOfSampleCharacteristic));
		stringBuilder.append(Symbol.ENTER);

		return stringBuilder.toString();
	}

	/**
	 * Get the String content of a {@link RowOfStudy}
	 *
	 * @param templateOfCharacteristic template of characteristic
	 * @param templateOfProtocol       template of protocol
	 * @param templateOfParameter      template of parameter
	 * @param templateOfFactor         template of factor
	 * @param templateOfComment        template of comment
	 * @return string
	 */
	public String getDataOfRow(List<Characteristic> templateOfCharacteristic, List<Protocol> templateOfProtocol,
			Map<String, List<Parameter>> templateOfParameter,
			Map<String, List<Characteristic>> templateOfCharacteristicInProtocol, List<Factor> templateOfFactor,
			List<Comment> templateOfComment, List<Characteristic> templateOfSampleCharacteristic) {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append((getSourceName() == null ? Symbol.EMPTY.toString() : getSourceName())).append(Symbol.TAB);
		stringBuilder.append(Characteristic.getDataWithTemplate(templateOfCharacteristic, getCharacteristics()));
		stringBuilder.append(Protocol.getDataWithTemplate(templateOfProtocol, templateOfParameter,
				templateOfCharacteristicInProtocol, getProtocols()));
		stringBuilder.append(Factor.getDataWithTemplate(templateOfFactor, getFactors()));
		stringBuilder.append(Comment.getDataWithTemplate(templateOfComment, getComments()));
		stringBuilder.append((getSampleName() == null ? Symbol.EMPTY.toString() : getSampleName())).append(Symbol.TAB);
		stringBuilder
				.append(Characteristic.getDataWithTemplate(templateOfSampleCharacteristic, getSampleCharacteristics()));

		stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(Symbol.TAB.toString()));

		return stringBuilder.toString();

	}

}
