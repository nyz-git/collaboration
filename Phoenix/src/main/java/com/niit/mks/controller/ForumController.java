package com.niit.mks.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.mks.dao.ForumDAO;
import com.niit.mks.dao.ForumRequestDAO;
import com.niit.mks.dao.UserDAO;
import com.niit.mks.model.Forum;
import com.niit.mks.model.ForumListModel;
import com.niit.mks.model.ForumRequest;

@RestController
public class ForumController {
	@Autowired
	Forum forum;

	@Autowired
	ForumDAO forumDAO;
	
	@Autowired
	ForumRequest forumRequest;

	@Autowired
	ForumRequestDAO forumRequestDAO;

	@Autowired
	UserDAO userDAO;
	
	
	public ForumController()
	{
		System.out.println("Instantiating ForumController");
	}
	
	@RequestMapping(value = "/forum/list/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<ForumListModel>> listApprovedblogs(@PathVariable("userId") int userId) {
		List<Forum> forums = forumDAO.getForumsByStatus("APPROVE");
		List<ForumListModel> forumlist = new ArrayList<ForumListModel>();

		ForumListModel forumModel = null;

		for (Forum f : forums) {
			forumModel = new ForumListModel();
			forumModel.setForum(f);
			forumModel.setFirstName(userDAO.getById(f.getUserId()).getFirstName());
			forumModel.setLastname(userDAO.getById(f.getUserId()).getLastName());
			forumModel.setUsername(userDAO.getById(f.getUserId()).getUsername());
			forumModel.setForumRequest(forumRequestDAO.get(userId, f.getForumId()));
			forumModel.setNoOfMembers(forumRequestDAO.getByStatus("APPROVE", f.getForumId()).size());
			forumlist.add(forumModel);

		}

		if (forumlist.isEmpty()) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("No blogs present.");
			forums.add(forum);
		}
		return new ResponseEntity<List<ForumListModel>>(forumlist, HttpStatus.OK);
	}

	@RequestMapping(value = "/forum/myForums/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<ForumListModel>> listAdminblogs(@PathVariable("userId") int userId) {
		List<Forum> forums = forumDAO.getForumsByUserId(userId);
		List<ForumListModel> forumlist = new ArrayList<ForumListModel>();

		ForumListModel forumModel = null;

		for (Forum f : forums) {
			forumModel = new ForumListModel();
			forumModel.setForum(f);
			forumModel.setFirstName(userDAO.getById(f.getUserId()).getFirstName());
			forumModel.setLastname(userDAO.getById(f.getUserId()).getLastName());
			forumModel.setUsername(userDAO.getById(f.getUserId()).getUsername());
			forumModel.setForumRequest(forumRequestDAO.get(userId, f.getForumId()));
			forumModel.setNoOfMembers(forumRequestDAO.getByStatus("APPROVE", f.getForumId()).size());
			forumlist.add(forumModel);

		}

		if (forumlist.isEmpty()) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("No blogs present.");
			forums.add(forum);
		}
		return new ResponseEntity<List<ForumListModel>>(forumlist, HttpStatus.OK);
	}

	@RequestMapping(value = "/forum/myForumlist/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<ForumListModel>> listUserblogs(@PathVariable("userId") int userId) {
		List<ForumRequest> requests = forumRequestDAO.getByUserStatus(userId);
		List<Forum> forums = new ArrayList<Forum>();

		for (ForumRequest r : requests) {
			forum = forumDAO.get(r.getForumId());
			forums.add(forum);
		}

		List<ForumListModel> forumlist = new ArrayList<ForumListModel>();

		ForumListModel forumModel = null;

		for (Forum f : forums) {
			forumModel = new ForumListModel();
			forumModel.setForum(f);
			forumModel.setFirstName(userDAO.getById(f.getUserId()).getFirstName());
			forumModel.setLastname(userDAO.getById(f.getUserId()).getLastName());
			forumModel.setUsername(userDAO.getById(f.getUserId()).getUsername());
			forumModel.setForumRequest(forumRequestDAO.get(userId, f.getForumId()));
			forumModel.setNoOfMembers(forumRequestDAO.getByStatus("APPROVE", f.getForumId()).size());
			forumlist.add(forumModel);

		}

		if (forumlist.isEmpty()) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("No blogs present.");
			forums.add(forum);
		}
		return new ResponseEntity<List<ForumListModel>>(forumlist, HttpStatus.OK);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/forum/get/{forumId}", method = RequestMethod.GET)
	public ResponseEntity<ForumListModel> getforum(@PathVariable("forumId") int forumId) {
		System.out.println("Fetching forum");
		ForumListModel forumListModel = new ForumListModel();
		Forum forum = forumDAO.get(forumId);
		forumListModel.setForum(forum);
		forumListModel.setFirstName(userDAO.getById(forum.getUserId()).getFirstName());
		forumListModel.setLastname(userDAO.getById(forum.getUserId()).getLastName());
		forumListModel.setLastname(userDAO.getById(forum.getUserId()).getUsername());

		if (forum == null) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("forum does not exist.");
		}
		return new ResponseEntity<ForumListModel>(forumListModel, HttpStatus.OK);
	}


	@RequestMapping(value = "/forum/create", method = RequestMethod.POST)
	public ResponseEntity<Forum> createforum(@RequestBody Forum currentForum) {

		forum = new Forum();
		// DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
		Date date = new Date();
		currentForum.setCreatedDate(date);
		currentForum.setCategoryId(1);
		currentForum.setStatus("APPROVE");
		if (forumDAO.saveOrUpdate(currentForum) == false) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("Failed to create forum. Please try again.");
		} else {
			forum = forumDAO.getByName(currentForum.getName());
			forumRequest = new ForumRequest();
			forumRequest.setForumId(forum.getForumId());
			forumRequest.setUserId(forum.getUserId());
			forumRequest.setStatus("APPROVE");
			forumRequestDAO.saveOrUpdate(forumRequest);
			forum.setErrorCode("200");
			forum.setErrorMessage("Forum created successfully .");
		}

		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	@RequestMapping(value = "/forum/get/{forumId}", method = RequestMethod.PUT)
	public ResponseEntity<Forum> updateforum(@PathVariable("forumId") int forumId, @RequestBody Forum updatedforum) {
		forum = forumDAO.get(forumId);
		if (forum == null) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("Invalid forum");
		} else {

			forum.setStatus(updatedforum.getStatus());
			if (forumDAO.saveOrUpdate(forum) == false) {
				forum = new Forum();
				forum.setErrorCode("404");
				forum.setErrorMessage("Failed to update forum.");
			} else {
				forum.setErrorCode("200");
				forum.setErrorMessage("forum updated successfully.");
			}

		}
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	@RequestMapping(value = "/forum/delete/{forumId}", method = RequestMethod.DELETE)
	public ResponseEntity<Forum> deleteforum(@PathVariable("forumId") int forumId) {
		forum = forumDAO.get(forumId);
		if (forum == null) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("Invalid forum");
		} else {
			if (forumDAO.delete(forum)) {
				forum = new Forum();
				forum.setErrorCode("200");
				forum.setErrorMessage("forum deleted successfully.");
			} else {
				forum = new Forum();
				forum.setErrorCode("404");
				forum.setErrorMessage("Failed to delete forum.");
			}
		}
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/latestForums/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<ForumListModel>> listLatestForums(@PathVariable("userId") int userId) {
		List<Forum> forums = forumDAO.getTopForums(3);
		List<ForumListModel> forumlist = new ArrayList<ForumListModel>();

		ForumListModel forumModel = null;

		for (Forum f : forums) {
			forumModel = new ForumListModel();
			forumModel.setForum(f);
			forumModel.setFirstName(userDAO.getById(f.getUserId()).getFirstName());
			forumModel.setLastname(userDAO.getById(f.getUserId()).getLastName());
			forumModel.setUsername(userDAO.getById(f.getUserId()).getUsername());
			forumModel.setForumRequest(forumRequestDAO.get(userId, f.getForumId()));
			forumModel.setNoOfMembers(forumRequestDAO.getByStatus("APPROVE", f.getForumId()).size());
			forumlist.add(forumModel);

		}

		if (forumlist.isEmpty()) {
			forum = new Forum();
			forum.setErrorCode("404");
			forum.setErrorMessage("No blogs present.");
			forums.add(forum);
		}
		return new ResponseEntity<List<ForumListModel>>(forumlist, HttpStatus.OK);
	}
}
