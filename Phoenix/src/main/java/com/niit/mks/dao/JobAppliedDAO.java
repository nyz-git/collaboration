package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.JobApplied;

public interface JobAppliedDAO {


	boolean saveOrUpdate(JobApplied jobApplied);

	boolean delete(JobApplied jobApplied);

	JobApplied get(int id);

	List<JobApplied> list();
}
