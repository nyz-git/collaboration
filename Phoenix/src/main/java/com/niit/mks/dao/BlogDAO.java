package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.Blog;

public interface BlogDAO {

	boolean saveOrUpdate(Blog blog);

	boolean delete(Blog blog);

	Blog get(int blogId);

	List<Blog> getByUserId(int userId);

	List<Blog> list();

	public List<Blog> getblogsByStatus(String status);

	List<Blog> getTopBlogs(int n);

}
