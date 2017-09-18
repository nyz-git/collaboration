package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.ForumComment;

public interface ForumCommentDAO {
	
	boolean saveOrUpdate(ForumComment forumComment);

	boolean delete(ForumComment forumComment);

	ForumComment get(int id);

	List<ForumComment> list();


}
