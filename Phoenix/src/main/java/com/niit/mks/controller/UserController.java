package com.niit.mks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.mks.dao.UserDAO;
import com.niit.mks.model.User;

@RestController
public class UserController {

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userDAO.list();
		if (users.isEmpty()) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("No Users present.");
			users.add(user);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/get/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("userId") int userId) {
		System.out.println("Fetching User");
		User user = userDAO.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist.");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User currentUser) {

		user = userDAO.getByUsername(currentUser.getUsername());
		if (user == null) {
			currentUser.setStatus("PENDING");
			currentUser.setEnabled("TRUE");
			currentUser.setIsOnline("NO");
			currentUser.setProfileId("USER_" + currentUser.getGender() + ".png");
			if (userDAO.saveOrUpdate(currentUser) == false) {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to register. Please try again.");
			} else {
				user = userDAO.getByUsername(currentUser.getUsername());
				user.setErrorCode("200");
				user.setErrorMessage("You are registered successfully.");
			}

		} else {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("User already present with this username.");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/get/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("userId") int userId, @RequestBody User updatedUser) {
		user = userDAO.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Invalid username");
		} else {

			user.setFirstName(updatedUser.getFirstName());
			if (userDAO.saveOrUpdate(user) == false) {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to update profile.");
			} else {
				user.setErrorCode("200");
				user.setErrorMessage("You are updated successfully.");
			}

		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> validateUser(@RequestBody User currentUser) {
		user.setUsername(currentUser.getUsername());
		user.setPassword(currentUser.getPassword());
		user = userDAO.validate(user);
		
		
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Incorrect username or password.");
		} else {
			user = userDAO.getByUsername(currentUser.getUsername());
			user.setIsOnline("YES");
			user.setEnabled("TRUE");
			userDAO.saveOrUpdate(user);
			user = userDAO.getByUsername(currentUser.getUsername());
			user.setErrorCode("200");
			user.setErrorMessage("WELCOME!");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> logout(@PathVariable("userId") int userId) {
		user = userDAO.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Invalid userId");
		} else {

			user.setIsOnline("NO");
			if (userDAO.saveOrUpdate(user) == false) {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to logout.");
			} else {
				user.setErrorCode("200");
				user.setErrorMessage("You are logout successfully.");
			}

		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
