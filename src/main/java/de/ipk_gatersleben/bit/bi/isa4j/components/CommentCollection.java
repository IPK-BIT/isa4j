/**
 * Copyright (c) 2021 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials are made available under the terms of the MIT License (https://spdx.org/licenses/MIT.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;

/**
 * Class to wrap the functions to handle the {@link Comment}s of a component.
 * 
 * @author psaroudakis, arendd
 *
 */

public class CommentCollection {

	private List<Comment> comments = new ArrayList<Comment>();

	/**
	 * Adds a new Comment to the collection.
	 * 
	 * @param comment Cannot be null, only one Comment per name allowed, otherwise a
	 *                {@link RedundantItemException} will be thrown.
	 */
	public void add(Comment comment) {
		if (this.comments.stream().map(Comment::getName).anyMatch(comment.getName()::equals))
			throw new RedundantItemException("Comment name not unique: " + comment.getName());
		this.comments.add(comment);
	}

	/**
	 * Return the list of {@link Comment}s
	 * 
	 * @return the comments
	 */
	public List<Comment> getAll() {
		return comments;
	}

	/**
	 * Try to find the Comment in the collection with the given name.
	 * 
	 * @param name the searched {@link Comment}
	 * @return a container with the searched {@link Comment} or an empty object if
	 *         no one with the given name was found.
	 */
	public Optional<Comment> getByName(String name) {
		return this.comments.stream().filter(c -> c.getName().equals(name)).findFirst();
	}

	/**
	 * Deletes all saved comments and adds the ones from the passed list. List
	 * cannot contain nulls or multiple comments with the same name.
	 * 
	 * @param comments the {@link List} of {@link Comment}s to set
	 */
	public void set(List<Comment> comments) {
		this.comments.clear();
		comments.stream().forEach(this::add);
	}
	
	@Override
	public String toString() {
		return this.comments.toString();
	}

}