package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Commentable {
	
	private List<Comment> comments = new ArrayList<Comment>();

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	public Optional<Comment> getCommentByName(String name) {
		return this.comments.stream().filter(c -> c.getName().equals(name)).findFirst();
	}
	
	public void addComment(Comment comment) {
		Objects.requireNonNull(comment);
		this.comments.add(comment);
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		comments.stream().forEach(Objects::requireNonNull);
		this.comments = comments;
	}

}
