package com.niit.mks.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.ForumRequest;

@Repository("forumRequestDAO")
public class ForumRequestDAOImpl implements ForumRequestDAO {

	@Autowired
	SessionFactory sessionFactory;

	public ForumRequestDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean saveOrUpdate(ForumRequest forumRequest) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(forumRequest);
			return true;
		} catch (HibernateException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ForumRequest> getByStatus(String status, int forumId) {
		String hql = "from ForumRequest where forumId = '" + forumId + "' and status = '" + status + "'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Transactional
	public ForumRequest get(int userId, int forumId) {
		String hql = "from ForumRequest where forumId = '" + forumId + "' and userId = '" + userId + "'";
		Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
		try {
			return (ForumRequest) query.uniqueResult();
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ForumRequest> getByUserStatus(int userId) {
		String hql = "from ForumRequest where userId = '" + userId + "' and status = 'APPROVE'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
}
