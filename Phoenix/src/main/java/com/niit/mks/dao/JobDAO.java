package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.Job;

public interface JobDAO  {

	boolean saveOrUpdate(Job job);

	boolean delete(Job job);

	Job get(int jobId);

	List<Job> list();
	
	Job getByJobProfile(String jobProfile);
}
