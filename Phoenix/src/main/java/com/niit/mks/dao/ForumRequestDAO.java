package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.ForumRequest;

public interface ForumRequestDAO {

	boolean saveOrUpdate(ForumRequest forumRequest);

	public List<ForumRequest> getByStatus(String status, int forumId);

	public List<ForumRequest> getByUserStatus(int userId);

	ForumRequest get(int userId, int forumId);

}
