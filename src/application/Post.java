package application;

import java.util.ArrayList;
import java.util.ListIterator;

public class Post implements Comparable<Post> {
	private int postId;
	private int creatorId;
	private String content;
	private String creationDate;
	private ArrayList<User> shareWith = new ArrayList<User>();

	public Post(int postId, int creatorId, String content, String creationDate) {
		setPostId(postId);
		setCreatorId(creatorId);
		this.content = content;
		this.creationDate = creationDate;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		if (postId > 0)
			this.postId = postId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		if (creatorId > 0)
			this.creatorId = creatorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		if (!creationDate.startsWith("-"))
			this.creationDate = creationDate;
	}

	public String shareWithToString() {
		ListIterator<User> curr = shareWith.listIterator();
		String s = "";
		while (curr.hasNext())
			s += ", " + curr.next().getName();
		return s;
	}

	@Override
	public String toString() {
		return " - Post ID:" + postId + ", Content: " + content + ", " + creationDate + ", Shared With: "
				+ shareWithToString();
	}

	@Override
	public int compareTo(Post o) {
		return this.postId - o.postId;
	}

	public ArrayList<User> getShareWith() {
		return shareWith;
	}

	public void setShareWith(ArrayList<User> shareWith) {
		this.shareWith = shareWith;
	}

}
