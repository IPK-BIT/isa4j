package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.ipk_gatersleben.bit.bi.isa4j.exceptions.RedundantItemException;

public class CommentCollection {
	
	private List<Comment> comments = new ArrayList<Comment>();

	/**
	 * Adds a new Comment to the collection.
	 * @param comment Cannot be null, only one Comment per name allowed, otherwise a RedundantItemException will be thrown.
	 */
	public void add(Comment comment) {
		if(this.comments.stream().map(Comment::getName).anyMatch(comment.getName()::equals))
			throw new RedundantItemException("Comment name not unique: " + comment.getName());
		this.comments.add(comment);
	}
	
	/**
	 * @return the comments
	 */
	public List<Comment> getAll() {
		return comments;
	}
	
	/**
	 * Try to find the Comment in the collection with the given name.
	 * @param name
	 * @return
	 */
	public Optional<Comment> getByName(String name) {
		return this.comments.stream().filter(c -> c.getName().equals(name)).findFirst();
	}

	/**
	 * Deletes all saved comments and adds the ones from the passed list.
	 * List cannot contain nulls or multiple comments with the same name.
	 * @param comments the comments to set
	 */
	public void set(List<Comment> comments) {
		this.comments.clear();
		comments.stream().forEach(this::add);
	}

}
