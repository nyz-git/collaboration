package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.Job;

@Repository("JobDAO")
@Transactional
public class JobDAOImpl implements JobDAO {

	@Autowired
	SessionFactory sessionFactory;
	public JobDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean saveOrUpdate(Job job) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(job);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Transactional
	public boolean delete(Job job) {
		try {
			sessionFactory.getCurrentSession().delete(job);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public Job get(int jobId) {
		String hql = "from Job where jobId = :jobId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("jobId", jobId);
		try {
			return (Job) query.uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<Job> list() {
		String hql = "from Job";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	
	@Transactional
	public Job getByJobProfile(String jobProfile){
		String hql = "from Job where jobProfile = :jobProfile";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("jobProfile", jobProfile);
		try {
			return (Job) query.uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}
}
