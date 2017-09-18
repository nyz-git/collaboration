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

import com.niit.mks.dao.ForumCategoryDAO;
import com.niit.mks.model.ForumCategory;

@RestController
public class ForumCategoryController {

	@Autowired
	ForumCategory forumCategory;

	@Autowired
	ForumCategoryDAO forumCategoryDAO;
	
	public ForumCategoryController()
	{
		System.out.println("Instantiating ForumCategoryController");
	}

	@RequestMapping(value = "/forumCategory/list", method = RequestMethod.GET)
	public ResponseEntity<List<ForumCategory>> listAllforumCategorys() {
		List<ForumCategory> forumCategorys = forumCategoryDAO.list();
		if (forumCategorys.isEmpty()) {
			forumCategory = new ForumCategory();
			forumCategory.setErrorCode("404");
			forumCategory.setErrorMessage("No forumCategorys present.");
			forumCategorys.add(forumCategory);
		}
		return new ResponseEntity<List<ForumCategory>>(forumCategorys, HttpStatus.OK);
	}

	@RequestMapping(value = "/forumCategory/get/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<ForumCategory> getforumCategory(@PathVariable("categoryId") int categoryId) {
		System.out.println("Fetching forumCategory");
		ForumCategory forumCategory = forumCategoryDAO.get(categoryId);
		if (forumCategory == null) {
			forumCategory = new ForumCategory();
			forumCategory.setErrorCode("404");
			forumCategory.setErrorMessage("forumCategory does not exist.");
		}
		return new ResponseEntity<ForumCategory>(forumCategory, HttpStatus.OK);
	}

	@RequestMapping(value = "/forumCategory/create", method = RequestMethod.POST)
	public ResponseEntity<ForumCategory> createforumCategory(@RequestBody ForumCategory currentforumCategory) {

		//forumCategory = new ForumCategory();
		// DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
		if (forumCategoryDAO.saveOrUpdate(currentforumCategory) == false) {
			forumCategory = new ForumCategory();
			forumCategory.setErrorCode("404");
			forumCategory.setErrorMessage("Failed to create forumCategory. Please try again.");
		} else {
			forumCategory.setErrorCode("200");
			forumCategory.setErrorMessage("forumCategory created successfully.");
		}

		return new ResponseEntity<ForumCategory>(forumCategory, HttpStatus.OK);
	}

	@RequestMapping(value = "/forumCategory/get/{categoryId}", method = RequestMethod.PUT)
	public ResponseEntity<ForumCategory> updateforumCategory(@PathVariable("categoryId") int categoryId,
			@RequestBody ForumCategory updatedforumCategory) {
		forumCategory = forumCategoryDAO.get(categoryId);
		if (forumCategory == null) {
			forumCategory = new ForumCategory();
			forumCategory.setErrorCode("404");
			forumCategory.setErrorMessage("Invalid forumCategory");
		} else {

			forumCategory.setDescription(updatedforumCategory.getDescription());
			if (forumCategoryDAO.saveOrUpdate(forumCategory) == false) {
				forumCategory = new ForumCategory();
				forumCategory.setErrorCode("404");
				forumCategory.setErrorMessage("Failed to update forumCategory.");
			} else {
				forumCategory.setErrorCode("200");
				forumCategory.setErrorMessage("forumCategory updated successfully.");
			}

		}
		return new ResponseEntity<ForumCategory>(forumCategory, HttpStatus.OK);
	}

	@RequestMapping(value = "/forumCategory/delete/{categoryId}", method = RequestMethod.DELETE)
	public ResponseEntity<ForumCategory> deleteforumCategory(@PathVariable("categoryId") int categoryId) {
		forumCategory = forumCategoryDAO.get(categoryId);
		if (forumCategory == null) {
			forumCategory = new ForumCategory();
			forumCategory.setErrorCode("404");
			forumCategory.setErrorMessage("Invalid forumCategory");
		} else {
			if (forumCategoryDAO.delete(forumCategory)) {
				forumCategory = new ForumCategory();
				forumCategory.setErrorCode("200");
				forumCategory.setErrorMessage("forumCategory deleted successfully.");
			} else {
				forumCategory = new ForumCategory();
				forumCategory.setErrorCode("404");
				forumCategory.setErrorMessage("Failed to delete forumCategory.");
			}
		}
		return new ResponseEntity<ForumCategory>(forumCategory, HttpStatus.OK);
	}

}
