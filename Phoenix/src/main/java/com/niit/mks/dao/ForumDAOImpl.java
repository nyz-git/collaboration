package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.Forum;

@Repository("forumDAO")

public class ForumDAOImpl implements ForumDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public ForumDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	@Transactional
	public boolean saveOrUpdate(Forum forum) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(forum);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Transactional
	public boolean delete(Forum forum) {
		try {
			sessionFactory.getCurrentSession().delete(forum);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Transactional
	public Forum get(int forumId) {
		return (Forum) sessionFactory.getCurrentSession().get(Forum.class, forumId);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Forum> list() {
		String hql = "from Forum";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	
	@Transactional
	public List<Forum> getForumsByStatus(String status) {
		String hql = "from Forum where status = '" + status + "'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	
	@Transactional
	public List<Forum> getForumsByUserId(int userId) {
		String hql = "from Forum where userId = '" + userId + "'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	
	@Transactional
	public Forum getByName(String name) {
		String hql = "from Forum where name = '" + name + "'";
		Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
		try {
			return (Forum) query.uniqueResult();
		} catch (Exception ex) {
			return null;
		}
	}

	
	@Transactional
	public List<Forum> getTopForums(int n) {
		String hql = "FROM Forum WHERE status = 'APPROVE' ORDER BY createdDate DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(1);
		query.setMaxResults(n);
		return query.list();
	}
}
