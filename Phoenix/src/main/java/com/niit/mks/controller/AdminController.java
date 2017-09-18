package com.niit.mks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.mks.dao.BlogDAO;
import com.niit.mks.dao.FriendDAO;
import com.niit.mks.dao.UserDAO;
import com.niit.mks.model.Blog;
import com.niit.mks.model.BlogListModel;
import com.niit.mks.model.Friend;
import com.niit.mks.model.User;
import com.niit.mks.service.EmailService;

@RestController
public class AdminController {

	@Autowired
	Blog blog;

	@Autowired
	BlogDAO blogDAO;

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	@Autowired
	Friend friend;

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	private EmailService emailService;

	public AdminController() {
		System.out.println("Instantiating AdminController");
	}

	@RequestMapping(value = "/admin/blogs", method = RequestMethod.GET)
	public ResponseEntity<List<BlogListModel>> listAllblogs() {
		List<Blog> blogs = blogDAO.getblogsByStatus("PENDING");
		List<BlogListModel> bloglist = new ArrayList<BlogListModel>();

		BlogListModel blogModel = null;

		for (Blog b : blogs) {
			blogModel = new BlogListModel();
			blogModel.setBlog(b);
			blogModel.setFirstName(userDAO.getById(b.getUserId()).getFirstName());
			blogModel.setLastname(userDAO.getById(b.getUserId()).getLastName());
			bloglist.add(blogModel);

		}

		if (bloglist.isEmpty()) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("No blogs present.");
			blogs.add(blog);
		}
		return new ResponseEntity<List<BlogListModel>>(bloglist, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/approvedBlogs", method = RequestMethod.GET)
	public ResponseEntity<List<BlogListModel>> listApprovedblogs() {
		List<Blog> blogs = blogDAO.getblogsByStatus("APPROVE");
		List<BlogListModel> bloglist = new ArrayList<BlogListModel>();

		BlogListModel blogModel = null;

		for (Blog b : blogs) {
			blogModel = new BlogListModel();
			blogModel.setBlog(b);
			blogModel.setFirstName(userDAO.getById(b.getUserId()).getFirstName());
			blogModel.setLastname(userDAO.getById(b.getUserId()).getLastName());
			bloglist.add(blogModel);

		}

		if (bloglist.isEmpty()) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("No blogs present.");
			blogs.add(blog);
		}
		return new ResponseEntity<List<BlogListModel>>(bloglist, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/rejectedBlogs", method = RequestMethod.GET)
	public ResponseEntity<List<BlogListModel>> listRejectedblogs() {
		List<Blog> blogs = blogDAO.getblogsByStatus("REJECT");
		List<BlogListModel> bloglist = new ArrayList<BlogListModel>();

		BlogListModel blogModel = null;

		for (Blog b : blogs) {
			blogModel = new BlogListModel();
			blogModel.setBlog(b);
			blogModel.setFirstName(userDAO.getById(b.getUserId()).getFirstName());
			blogModel.setLastname(userDAO.getById(b.getUserId()).getLastName());
			bloglist.add(blogModel);

		}

		if (bloglist.isEmpty()) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("No blogs present.");
			blogs.add(blog);
		}
		return new ResponseEntity<List<BlogListModel>>(bloglist, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/approveBlog/{blogId}", method = RequestMethod.PUT)
	public ResponseEntity<Blog> approveBlog(@PathVariable("blogId") int blogId) {
		blog = blogDAO.get(blogId);
		if (blog == null) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Invalid blog");
		} else {

			blog.setStatus("APPROVE");
			if (blogDAO.saveOrUpdate(blog) == false) {
				blog = new Blog();
				blog.setErrorCode("404");
				blog.setErrorMessage("Failed to update blog.");
			} else {
				blog.setErrorCode("200");
				blog.setErrorMessage("blog updated successfully.");
			}

		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/disapproveBlog/{blogId}", method = RequestMethod.PUT)
	public ResponseEntity<Blog> disapproveBlog(@PathVariable("blogId") int blogId) {
		blog = blogDAO.get(blogId);
		if (blog == null) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Invalid blog");
		} else {

			blog.setStatus("REJECT");
			if (blogDAO.saveOrUpdate(blog) == false) {
				blog = new Blog();
				blog.setErrorCode("404");
				blog.setErrorMessage("Failed to update blog.");
			} else {
				blog.setErrorCode("200");
				blog.setErrorMessage("blog updated successfully.");
			}

		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllusers() {
		List<User> users = userDAO.getUsersByStatus("PENDING");
		if (users.isEmpty()) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("No users present.");
			users.add(user);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/approveUser/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<User> approveUser(@PathVariable("userId") int userId) {
		user = userDAO.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Invalid user");
		} else {

			user.setStatus("APPROVE");
			if (userDAO.saveOrUpdate(user) == false) {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to update user.");
			} else {
				user.setErrorCode("200");
				user.setErrorMessage("user updated successfully.");
				emailService.approvedUserMessage(user);
			}

		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/disapproveUser/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<User> disapproveUser(@PathVariable("userId") int userId) {
		user = userDAO.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Invalid user");
		} else {

			user.setStatus("REJECT");
			if (userDAO.saveOrUpdate(user) == false) {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to update user.");
			} else {
				user.setErrorCode("200");
				user.setErrorMessage("user updated successfully.");
			}

		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/approvedusers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listApprovedusers() {
		List<User> users = userDAO.getUsersByStatus("APPROVE");

		if (users.isEmpty()) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("No users present.");
			users.add(user);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/latestusers", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listLatestusers() {
		List<User> users = userDAO.getTopUsers(3);

		if (users.isEmpty()) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("No users present.");
			users.add(user);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/blockUser/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<User> blockUser(@PathVariable("userId") int userId) {
		user = userDAO.getById(userId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Invalid user");
		} else {

			user.setStatus("REJECT");
			user.setEnabled("FALSE");
			if (userDAO.saveOrUpdate(user) == false) {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMessage("Failed to update user.");
			} else {
				user.setErrorCode("200");
				user.setErrorMessage("user updated successfully.");
				emailService.approvedUserMessage(user);
			}

		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
