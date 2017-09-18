package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.JobApplied;

@Repository("JobAppliedDAO")
@Transactional
public class JobAppliedDAOImpl implements JobAppliedDAO {

	
	@Autowired
	SessionFactory sessionFactory;


	public JobAppliedDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean saveOrUpdate(JobApplied jobApplied) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(jobApplied);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Transactional
	public boolean delete(JobApplied jobApplied) {
		try {
			sessionFactory.getCurrentSession().delete(jobApplied);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public JobApplied get(int id) {
		String hql = "from JobApplied where id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id);
		try {
			return (JobApplied) query.uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<JobApplied> list() {
		String hql = "from JobApplied";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
}
