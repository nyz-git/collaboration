package com.niit.mks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.mks.dao.FriendDAO;
import com.niit.mks.dao.UserDAO;
import com.niit.mks.model.Friend;
import com.niit.mks.model.FriendListModel;
import com.niit.mks.model.User;

@RestController
public class FriendController {

	@Autowired
	Friend friend;

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
	public ResponseEntity<Friend> addFriend(@RequestBody Friend request) {

		friend = friendDAO.get(request.getUserId(), request.getFriendId());
		if (friend == null) {
			friend = friendDAO.get(request.getFriendId(), request.getUserId());
			if (friend == null) {
				friend = new Friend();
				friend.setUserId(request.getUserId());
				friend.setFriendId(request.getFriendId());
				friend.setStatus("PENDING");
				if (friendDAO.saveOrUpdate(friend) == false) {
					friend = new Friend();
					friend.setErrorCode("404");
					friend.setErrorMessage("Failed to send a request. Please try again.");
				} else {
					friend.setErrorCode("200");
					friend.setErrorMessage("request sent successfully.");
				}
			} else {
				friend = new Friend();
				friend.setErrorCode("404");
				friend.setErrorMessage("request already present.");
			}
		} else {
			friend = new Friend();
			friend.setErrorCode("404");
			friend.setErrorMessage("request already present.");
		}

		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/friendList/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getFriends(@PathVariable("userId") int userId) {
		List<Friend> friendlist1 = friendDAO.getFriends(userId);
		List<Friend> friendlist2 = friendDAO.myFriends(userId);
		List<Friend> friends = new ArrayList<Friend>();
		List<User> users = new ArrayList<User>();

		for (Friend f : friendlist1) {
			friends.add(f);
		}

		for (Friend f : friendlist2) {
			friends.add(f);
		}

		for (Friend f : friends) {
			if (f.getUserId() != userId) {
				user = userDAO.getById(f.getUserId());
				users.add(user);
			}
			if (f.getFriendId() != userId) {
				user = userDAO.getById(f.getFriendId());
				users.add(user);
			}
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/online/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getOnlineFriends(@PathVariable("userId") int userId) {
		List<Friend> friendlist1 = friendDAO.getFriends(userId);
		List<Friend> friendlist2 = friendDAO.myFriends(userId);
		List<Friend> friends = new ArrayList<Friend>();
		List<User> users = new ArrayList<User>();

		for (Friend f : friendlist1) {
			friends.add(f);
		}

		for (Friend f : friendlist2) {
			friends.add(f);
		}

		for (Friend f : friends) {
			if (f.getUserId() != userId && userDAO.getById(f.getUserId()).getIsOnline().equals("YES")) {
				user = userDAO.getById(f.getUserId());
				users.add(user);
			}
			if (f.getFriendId() != userId && userDAO.getById(f.getFriendId()).getIsOnline().equals("YES")) {
				user = userDAO.getById(f.getFriendId());
				users.add(user);
			}
		}

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/friendRequests/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getRequests(@PathVariable("userId") int userId) {
		List<Friend> friends = friendDAO.getRequest(userId);
		List<User> users = new ArrayList<User>();

		for (Friend f : friends) {
			user = userDAO.getById(f.getUserId());
			users.add(user);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/acceptRequest", method = RequestMethod.PUT)
	public ResponseEntity<Friend> acceptFriendRequests(@RequestBody Friend request) {
		friend = friendDAO.get(request.getUserId(), request.getFriendId());
		if (friend == null) {
			friend = new Friend();
			friend.setErrorCode("404");
			friend.setErrorMessage("Invalid request");
		} else {

			friend.setStatus("ACCEPT");
			if (friendDAO.saveOrUpdate(friend) == false) {
				friend = new Friend();
				friend.setErrorCode("404");
				friend.setErrorMessage("Failed to accept request.");
			} else {
				friend = friendDAO.get(request.getUserId(), request.getFriendId());
				friend.setErrorCode("200");
				friend.setErrorMessage("request accepted successfully.");
			}

		}
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectRequest", method = RequestMethod.PUT)
	public ResponseEntity<Friend> rejectFriendRequests(@RequestBody Friend request) {
		friend = friendDAO.get(request.getUserId(), request.getFriendId());
		if (friend == null) {
			friend = new Friend();
			friend.setErrorCode("404");
			friend.setErrorMessage("Invalid request");
		} else {
			if (friendDAO.delete(friend) == false) {
				friend = new Friend();
				friend.setErrorCode("404");
				friend.setErrorMessage("Failed to reject request.");
			} else {
				friend = new Friend();
				friend.setErrorCode("200");
				friend.setErrorMessage("request rejected successfully.");
			}

		}
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	@RequestMapping(value = "/latestFriends", method = RequestMethod.GET)
	public ResponseEntity<List<FriendListModel>> listLatestblogs() {
		List<Friend> friends = friendDAO.getTopFriends(3);
		List<FriendListModel> friendslist = new ArrayList<FriendListModel>();

		FriendListModel friendModel = null;

		for (Friend f : friends) {
			friendModel = new FriendListModel();
			friendModel.setFriend(f);
			friendModel.setUserDetails(userDAO.getById(f.getUserId()));
			friendModel.setFriendDetails(userDAO.getById(f.getFriendId()));
			friendslist.add(friendModel);

		}

		if (friendslist.isEmpty()) {
			friend = new Friend();
			friend.setErrorCode("404");
			friend.setErrorMessage("No blogs present.");
			friends.add(friend);
		}
		return new ResponseEntity<List<FriendListModel>>(friendslist, HttpStatus.OK);
	}
}
