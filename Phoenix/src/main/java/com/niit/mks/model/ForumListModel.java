package com.niit.mks.model;

public class ForumListModel {

Forum forum;
	
	String firstName;
	
	String lastname;
	
	String username;
	
	ForumRequest forumRequest;
	
	int noOfMembers;

	public int getNoOfMembers() {
		return noOfMembers;
	}

	public void setNoOfMembers(int noOfMembers) {
		this.noOfMembers = noOfMembers;
	}

	public ForumRequest getForumRequest() {
		return forumRequest;
	}

	public void setForumRequest(ForumRequest forumRequest) {
		this.forumRequest = forumRequest;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
