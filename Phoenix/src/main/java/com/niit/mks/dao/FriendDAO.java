package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.Friend;

public interface FriendDAO {

	
	boolean saveOrUpdate(Friend friend);

	boolean delete(Friend friend);

	Friend get(int userId, int friendId);

	List<Friend> list();
	
	List<Friend> getFriends(int userId);
	
	List<Friend> myFriends(int userId);
	
	List<Friend> getRequest(int userId);
	
	List<Friend> getTopFriends(int n);
	
}
