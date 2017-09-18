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
import com.niit.mks.model.ForumRequest;
import com.niit.mks.model.User;

@RestController
public class ForumRequestController {


	@Autowired
	ForumRequest forumRequest;
	
	@Autowired
	ForumRequestDAO forumRequestDAO;
	
	@Autowired 
	ForumDAO forumDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	User user;
	
	public ForumRequestController()
	{
		System.out.println("Instantiating Foru");
	}

	@RequestMapping(value = "/forum/request", method = RequestMethod.POST)
	public ResponseEntity<ForumRequest> createRequest(@RequestBody ForumRequest request) {
		
		forumRequest = forumRequestDAO.get(request.getUserId(), request.getForumId());
		
		if(forumRequest == null){
			//DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
			Date date = new Date();
			request.setStatus("PENDING");
			if(forumRequestDAO.saveOrUpdate(request) == false){
				forumRequest = new ForumRequest();
				forumRequest.setErrorCode("404");
				forumRequest.setErrorMessage("Failed to make a request. Please try again.");
			}
			else{
				forumRequest = new ForumRequest();
				forumRequest.setErrorCode("200");
				forumRequest.setErrorMessage("request created successfully.");
			}
		}else{
			forumRequest = new ForumRequest();
			forumRequest.setErrorCode("404");
			forumRequest.setErrorMessage("You already sent a request.");
		}
				
		return new ResponseEntity<ForumRequest>(forumRequest, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/forum/pendingRequest/{forumId}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listPendingRequest(@PathVariable("forumId") int forumId) {
		List<ForumRequest> requests = forumRequestDAO.getByStatus("PENDING", forumId);
		List<User> userlist = new ArrayList<User>();

		User user = null;
		
		for (ForumRequest f : requests) {
			user = userDAO.getById(f.getUserId());
			userlist.add(user);

		}
		
		
		if (userlist.isEmpty()) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("No user present.");
			userlist.add(user);
		}
		return new ResponseEntity<List<User>>(userlist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/forum/approvedRequest/{forumId}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listForumMembers(@PathVariable("forumId") int forumId) {
		List<ForumRequest> requests = forumRequestDAO.getByStatus("APPROVE", forumId);
		List<User> userlist = new ArrayList<User>();

		User user = null;
		
		for (ForumRequest f : requests) {
			user = userDAO.getById(f.getUserId());
			userlist.add(user);

		}
		
		
		if (userlist.isEmpty()) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("No user present.");
			userlist.add(user);
		}
		return new ResponseEntity<List<User>>(userlist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/forum/approveMember", method = RequestMethod.PUT)
	public ResponseEntity<User> approveBlog(@RequestBody ForumRequest userRequest) {
		forumRequest = forumRequestDAO.get(userRequest.getUserId(), userRequest.getForumId());
		forumRequest.setStatus("APPROVE");
			if(forumRequestDAO.saveOrUpdate(forumRequest) == false){
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to approve blog.");
			}else{
				user = userDAO.getById(userRequest.getUserId());
				user.setErrorCode("200");
				user.setErrorMessage("user approved successfully.");
			}
			
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/forum/disapproveMember", method = RequestMethod.PUT)
	public ResponseEntity<User> disapproveBlog(@RequestBody ForumRequest userRequest) {
		forumRequest = forumRequestDAO.get(userRequest.getUserId(), userRequest.getForumId());
		forumRequest.setStatus("REJECT");
			if(forumRequestDAO.saveOrUpdate(forumRequest) == false){
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to approve blog.");
			}else{
				user = userDAO.getById(userRequest.getUserId());
				user.setErrorCode("200");
				user.setErrorMessage("user approved successfully.");
			}
			
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
