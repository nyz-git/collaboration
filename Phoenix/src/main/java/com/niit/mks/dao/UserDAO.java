package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.User;

public interface UserDAO {

	public Boolean saveOrUpdate(User user);

	public Boolean delete(User user);
	
	public User getByUsername(String username);
	
	public User getById(int userId);
	
	public List<User> list();
	
	public List<User> getUsersByStatus(String status);
	
	boolean updateUserProfileId(String fileName, int userId);
	
	public User validate(User user);
	
	List<User> getTopUsers(int n);
}
