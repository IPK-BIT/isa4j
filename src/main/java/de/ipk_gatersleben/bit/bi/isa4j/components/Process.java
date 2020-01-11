package de.ipk_gatersleben.bit.bi.isa4j.components;

public class Process implements Commentable {
	
	private CommentCollection comments = new CommentCollection();
	
	public CommentCollection comments() {
		return this.comments;
	}
	
//	private StudyOrAssayTableObject nextItem;
//	/**
//	 * @return the nextItem
//	 */
//	public StudyOrAssayTableObject getNextStudyOrAssayTableObject() {
//		return nextItem;
//	}
//	/**
//	 * @param nextItem the nextItem to set
//	 */
//	public void setNextStudyOrAssayTableObject(StudyOrAssayTableObject nextItem) {
//		this.nextItem = nextItem;
//	}
//	
//	public void setInput(StudyOrAssayTableObject input) {
//		input.setNextStudyOrAssayTableObject(this);
//	}
//	
//	public void setOutput(StudyOrAssayTableObject output) {
//		this.setNextStudyOrAssayTableObject(output);
//	}

}
