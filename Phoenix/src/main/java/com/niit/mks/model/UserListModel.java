package com.niit.mks.model;

import java.util.List;

public class UserListModel {

	User user;

	List<User> mutualFriends;

	int noOfMutual;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getMutualFriends() {
		return mutualFriends;
	}

	public void setMutualFriends(List<User> mutualFriends) {
		this.mutualFriends = mutualFriends;
	}

	public int getNoOfMutual() {
		return noOfMutual;
	}

	public void setNoOfMutual(int noOfMutual) {
		this.noOfMutual = noOfMutual;
	}

}
