package com.niit.mks.model;

public class FriendListModel {

	Friend friend;

	User userDetails;

	User friendDetails;

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	public User getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(User userDetails) {
		this.userDetails = userDetails;
	}

	public User getFriendDetails() {
		return friendDetails;
	}

	public void setFriendDetails(User friendDetails) {
		this.friendDetails = friendDetails;
	}

}
