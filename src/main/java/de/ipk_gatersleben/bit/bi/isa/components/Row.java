/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.components;

import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.Study;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;

import java.util.*;

public abstract class Row {

	/**
	 * Name to link the entries of the {@link Study} file to the corresponding entry
	 * in the {@link Assay}
	 */
	private String sampleName;

	/**
	 * List of {@link Characteristic}s to describe the investigated biological
	 * material
	 */
	private ArrayList<Characteristic> characteristics = new ArrayList<>(5);

	/**
	 * List of {@link Comment}s assigned to the entry
	 */
	private ArrayList<Comment> comments;

	/**
	 * List of {@link Protocol}s assigned to the entry
	 */
	private ArrayList<Protocol> protocols = new ArrayList<>(5);

	/**
	 * List of {@link Factor}s assigned to the entry
	 */
	private ArrayList<Factor> factors;

	/**
	 * List of {@link Characteristic}s to describe the investigated samples
	 */
	private ArrayList<Characteristic> sampleCharacteristics = new ArrayList<>(5);

	public ArrayList<Characteristic> getSampleCharacteristics() {
		return sampleCharacteristics;
	}

	public void setSampleCharacteristics(ArrayList<Characteristic> sampleCharacteristics) {
		this.sampleCharacteristics = sampleCharacteristics;
	}

	/**
	 * get sample name
	 *
	 * @return sample name
	 */
	public String getSampleName() {
		return sampleName;
	}

	/**
	 * set sample name
	 *
	 * @param sampleName sample name
	 */
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	/**
	 * Get {@link Characteristic}s of the {@link Row}
	 *
	 * @return the {@link Characteristic} of the {@link Row}
	 */
	public ArrayList<Characteristic> getCharacteristics() {
		return characteristics;
	}

	/**
	 * Set {@link Characteristic}s of the {@link Row}
	 *
	 * @param characteristics the {@link Characteristic} of the {@link Row}
	 */
	public void setCharacteristics(ArrayList<Characteristic> characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * Get {@link Protocol}s of the {@link Row}
	 *
	 * @return protocols of the {@link Row}
	 */
	public ArrayList<Protocol> getProtocols() {
		return protocols;
	}

	/**
	 * Set {@link Protocol}s of the {@link Row}
	 *
	 * @param protocols {@link Protocol} of the {@link Row}
	 */
	public void setProtocols(ArrayList<Protocol> protocols) {
		this.protocols = protocols;
	}

	/**
	 * Get {@link Comment}s of the {@link Row}
	 *
	 * @return the list of {@link Comment}s
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}

	/**
	 * Set {@link Comment}s of the {@link Row}
	 *
	 * @param comments list of {@link Comment}s
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * Add a {@link Comment} to this {@link Row}
	 *
	 * @param comment the {@link Comment} to add
	 * @return if the comment was added successfully
	 */
	public boolean addComment(Comment comment) {
		if (comments == null) {
			comments = new ArrayList<>(2);
		}
		if (!this.comments.contains(comment)) {
			this.comments.add(comment);
			return true;
		} else {
			LoggerUtil.logger.error("The list of comments in class Row contains comment '" + comment.getName() + "'. "
					+ "It can't add same comment again.");
			return false;
		}
	}

	/**
	 * Add a new {@link Characteristic} to this {@link Row}
	 *
	 * @param characteristic a new {@link Characteristic} to add
	 */
	public boolean addCharacteristic(Characteristic characteristic) {
		if (!this.characteristics.contains(characteristic)) {
			this.characteristics.add(characteristic);
			return true;
		} else {
			LoggerUtil.logger.error("The list of characteristics in class Row contains characteristic '"
					+ characteristic.getTerm() + "'. " + "It can't add same characteristic again.");
			return false;
		}
	}

	/**
	 * Add a new {@link Characteristic} to this {@link Row}
	 *
	 * @param sampleCharacteristic a new {@link Characteristic} to add
	 */
	public boolean addSampleCharacteristic(Characteristic sampleCharacteristic) {
		if (!this.sampleCharacteristics.contains(sampleCharacteristic)) {
			this.sampleCharacteristics.add(sampleCharacteristic);
			return true;
		} else {
			LoggerUtil.logger.error("The list of characteristics in class Row contains sampleCharacteristic '"
					+ sampleCharacteristic.getTerm() + "'. " + "It can't add same characteristic again.");
			return false;
		}
	}

	/**
	 * Add a {@link Protocol} to the {@link RowOfStudy}
	 *
	 * @param protocol {@link Protocol} to add
	 * @return if add successful
	 */
	public boolean addProtocol(Protocol protocol) {
		if (!this.protocols.contains(protocol)) {
			this.protocols.add(protocol);
			return true;
		} else {
			LoggerUtil.logger.error("The list of protocol in class RowOfStudy contains protocol '" + protocol.getName()
					+ "'. " + "It can't add same protocol again.");
			return false;
		}
	}

	/**
	 * Get {@link Factor}s of the {@link Row}
	 *
	 * @return the list of {@link Factor}s
	 */
	public ArrayList<Factor> getFactors() {
		return factors;
	}

	/**
	 * Set {@link Factor}s of the {@link Row}
	 *
	 * @param factors list of {@link Factor}s
	 */
	public void setFactors(ArrayList<Factor> factors) {
		this.factors = factors;
	}

	public boolean addFactor(Factor factor) {
		if (factors == null) {
			factors = new ArrayList<>(2);
		}
		if (!this.factors.contains(factor)) {
			this.factors.add(factor);
			return true;
		} else {
			LoggerUtil.logger.error("The list of factor in class Row contains factor '" + factor.getTerm() + "'. "
					+ "It can't add same factor again.");
			return false;
		}
	}

	/**
	 * This function check the components of this row, if the template has them. If
	 * the template doesn't have the component, it will add to template.
	 *
	 * @param motherOfCharacteristics       template of characteristics
	 * @param hasPositionOfCharacteristic   record, which position is used for
	 *                                      characteristic by user(user sets by
	 *                                      function setPosition)
	 * @param orderPositionOfCharacteristic record, which position for
	 *                                      characteristic total is used
	 * @param motherOfComment               template of comments
	 * @param hasPositionOfComment          record, which position is used for
	 *                                      comment by user(user sets by function
	 *                                      setPosition)
	 * @param orderPositionOfComment        record, which position for comment total
	 *                                      is used
	 * @param motherOfProtocols             template of protocols
	 * @param hasPositionOfProtocol         record, which position is used for
	 *                                      protocol by user(user sets by function
	 *                                      setPosition)
	 * @param orderPositionOfProtocol       record, which position for protocol
	 *                                      total is used
	 * @param motherOfParameters            template of parameters
	 * @param hasPositionOfParameter        record, which position is used for
	 *                                      parameter by user(user sets by function
	 *                                      setPosition)
	 * @param orderPositionOfParameter      record, which position for parameter
	 *                                      total is used
	 * @param motherOfFactors               template of factors
	 * @param hasPositionOfFactor           record, which position is used for
	 *                                      factor by user(user sets by function
	 *                                      setPosition)
	 * @param orderPositionOfFactor         record, which position for factor total
	 *                                      is used
	 */
	protected boolean addComponentsToTemplate(Map<String, Characteristic> motherOfCharacteristics,
			Set<Integer> hasPositionOfCharacteristic, Set<Integer> orderPositionOfCharacteristic,
			Map<String, Comment> motherOfComment, Set<Integer> hasPositionOfComment,
			Set<Integer> orderPositionOfComment, Map<String, Protocol> motherOfProtocols,
			Set<Integer> hasPositionOfProtocol, Set<Integer> orderPositionOfProtocol,
			Map<String, Map<String, Parameter>> motherOfParameters,
			HashMap<String, HashSet<Integer>> hasPositionOfParameter,
			HashMap<String, HashSet<Integer>> orderPositionOfParameter,
			Map<String, Map<String, Characteristic>> motherOfCharacteristicsInProtocol,
			HashMap<String, HashSet<Integer>> hasPositionOfCharacteristicsInPtorocol,
			HashMap<String, HashSet<Integer>> orderPositionOfCharacteristicsInProtocol,
			Map<String, Factor> motherOfFactors, Set<Integer> hasPositionOfFactor, Set<Integer> orderPositionOfFactor,
			Map<String, Characteristic> motherOfSampleCharacteristics, Set<Integer> hasPositionOfSampleCharacteristics,
			Set<Integer> orderPositionOfSampleCharacteristics, Set<String> ontologySourceREF) {
		if (!Characteristic.addComponentToTemplate(motherOfCharacteristics, characteristics,
				hasPositionOfCharacteristic, orderPositionOfCharacteristic, ontologySourceREF)) {
			return false;
		}
		if (!Characteristic.addComponentToTemplate(motherOfSampleCharacteristics, sampleCharacteristics,
				hasPositionOfSampleCharacteristics, orderPositionOfSampleCharacteristics, ontologySourceREF)) {
			return false;
		}

		Comment.addCommentToTemplate(motherOfComment, comments, hasPositionOfComment, orderPositionOfComment);

		if (!Protocol.addProtocolToTemplate(motherOfProtocols, motherOfParameters, motherOfCharacteristicsInProtocol,
				protocols, hasPositionOfProtocol, hasPositionOfParameter, hasPositionOfCharacteristicsInPtorocol,
				orderPositionOfProtocol, orderPositionOfParameter, orderPositionOfCharacteristicsInProtocol,
				ontologySourceREF)) {
			return false;
		}

		if (!Factor.addComponentToTemplate(motherOfFactors, factors, hasPositionOfFactor, orderPositionOfFactor,
				ontologySourceREF)) {
			return false;
		}
		return true;
	}

	/**
	 * When the template is decided, use this function to sort the components by
	 * position
	 *
	 * @param motherOfCharacteristics template of characteristics
	 * @param orderOfCharacteristic   sorted characteristics
	 * @param motherOfComment         template of comments
	 * @param orderOfComment          sorted comments
	 * @param motherOfProtocols       template of protocols
	 * @param orderOfProtocol         sorted protocols
	 * @param motherOfParameters      template of parameters
	 * @param orderOfParameter        sorted parameters
	 * @param motherOfFactors         template of factors
	 * @param orderOfFactor           sorted factors
	 */
	protected static void sortTemplateWithPosition(Map<String, Characteristic> motherOfCharacteristics,
			List<Characteristic> orderOfCharacteristic, Map<String, Comment> motherOfComment,
			List<Comment> orderOfComment, Map<String, Protocol> motherOfProtocols, List<Protocol> orderOfProtocol,
			Map<String, Map<String, Parameter>> motherOfParameters, Map<String, List<Parameter>> orderOfParameter,
			Map<String, Map<String, Characteristic>> motherOfCharacteristicsInProtocol,
			Map<String, List<Characteristic>> orderOfCharacteristicInProtocol, Map<String, Factor> motherOfFactors,
			List<Factor> orderOfFactor, Map<String, Characteristic> motherOfSampleCharacteristics,
			List<Characteristic> orderOfSampleCharacteristics) {

		Characteristic.sortTemplateComponent(motherOfCharacteristics, orderOfCharacteristic);

		Characteristic.sortTemplateComponent(motherOfSampleCharacteristics, orderOfSampleCharacteristics);

		Comment.sortTemplateComment(motherOfComment, orderOfComment);

		Protocol.sortedProtocolWithPosition(motherOfProtocols, orderOfProtocol, motherOfParameters, orderOfParameter,
				motherOfCharacteristicsInProtocol, orderOfCharacteristicInProtocol);

		Factor.sortTemplateComponent(motherOfFactors, orderOfFactor);
	}
}
