package de.ipk_gatersleben.bit.bi.isa4j.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Commentable {
	
	private List<Comment> comments = new ArrayList<Comment>();

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	// TODO returns null if there is none, maybe that's not ideal?
	// Maybe we should stick with returning Optional<Comment>?‚
	public Comment getCommentByName(String name) {
		Optional<Comment> comment = this.comments.stream().filter(c -> c.getName().equals(name)).findFirst();
		return comment.isEmpty() ? null : comment.get();
	}
	
	public boolean addComment(Comment comment) {
		// Don't add nulls in here
		if (comment == null) {
			// LoggerUtil.logger.error("Can not add an empty Comment");
			return false;
		}
		// Only one comment per name is allowed, so check here if one already exists.
		if(this.comments.stream().map(c -> c.getName()).collect(Collectors.toList()).contains(comment.getName())) {
			// ERROR MSG
			return false;
		}
		
		return this.comments.add(comment);
	}

	/**
	 * @param comments the comments to set
	 */
	public boolean setComments(List<Comment> comments) {
		if(comments == null) {
			// @ TODO error msg
			return false;
		} else {
			this.comments = comments;
			return true;
		}
	}

}
