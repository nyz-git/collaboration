package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.ForumCategory;

public interface ForumCategoryDAO {

	boolean saveOrUpdate(ForumCategory forumCategory);

	boolean delete(ForumCategory iorumCategory);

	ForumCategory get(int id);

	List<ForumCategory> list();
}


