package com.niit.mks.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.mks.dao.ForumCommentDAO;
import com.niit.mks.dao.ForumDAO;
import com.niit.mks.dao.UserDAO;
import com.niit.mks.model.Forum;
import com.niit.mks.model.ForumComment;
import com.niit.mks.model.User;

@RestController
public class ForumCommentController {


	@Autowired
	ForumComment forumComment;
	
	@Autowired
	ForumCommentDAO forumCommentDAO;
	
	@Autowired
	Forum forum;
	
	@Autowired
	ForumDAO forumDAO;
	
	@Autowired
	User user;
	
	@Autowired
	UserDAO userDAO;
	
	public ForumCommentController()
	{
		System.out.println("Instantiating ForumCommentController");
	}

	@RequestMapping(value = "/forumComment/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<ForumComment> getforumComment(@PathVariable("id") int id) {
		System.out.println("Fetching forumComment");
		ForumComment forumComment = forumCommentDAO.get(id);
		if (forumComment == null) {
			forumComment = new ForumComment();
			forumComment.setErrorCode("404");
			forumComment.setErrorMessage("forumComment does not exist.");
		}
		return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
	}

	@RequestMapping(value = "/forumComment/create/{forumId}", method = RequestMethod.POST)
	public ResponseEntity<ForumComment> createforumComment(@PathVariable("forumId") int forumId, @RequestBody ForumComment currentforumComment) {

		forumComment = new ForumComment();
		//DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
		Date date = new Date();
		
		user = userDAO.getById(currentforumComment.getUserId());
		currentforumComment.setUsername(user.getUsername());
		
		forum = forumDAO.get(forumId);
		
		currentforumComment.setForum(forum);
		currentforumComment.setCommentDate(date);
		if(forumCommentDAO.saveOrUpdate(currentforumComment) == false){
			forumComment = new ForumComment();
			forumComment.setErrorCode("404");
			forumComment.setErrorMessage("Failed to create blogComment. Please try again.");
		}
		else{
			forumComment.setErrorCode("200");
			forumComment.setErrorMessage("blogComment created successfully.");
		}
			
	return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
	}

	
	 @RequestMapping(value = "/forumComment/delete/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<ForumComment> deleteforumComment(@PathVariable("id") int id) {
		 forumComment = forumCommentDAO.get(id);
			if(forumComment == null){
				forumComment = new ForumComment();
				forumComment.setErrorCode("404");
				forumComment.setErrorMessage("Invalid forumComment");
			}
			else{
				if(forumCommentDAO.delete(forumComment)){
					forumComment = new ForumComment();
					forumComment.setErrorCode("200");
					forumComment.setErrorMessage("forumComment deleted successfully.");
				}else{
					forumComment = new ForumComment();
					forumComment.setErrorCode("404");
					forumComment.setErrorMessage("Failed to delete forumComment.");
				}
			}
			return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
	    }
}
