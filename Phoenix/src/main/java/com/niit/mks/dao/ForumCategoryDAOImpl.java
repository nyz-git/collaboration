package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.ForumCategory;

@Repository("ForumCategoryDAO")

public class ForumCategoryDAOImpl implements ForumCategoryDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	public ForumCategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean saveOrUpdate(ForumCategory forumCategory) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(forumCategory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean delete(ForumCategory forumCategory) {
		try {
			sessionFactory.getCurrentSession().delete(forumCategory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public ForumCategory get(int forumId) {
		String hql = "from ForumCategory where forumId = :forumId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("forumId", forumId);
		try {
			return (ForumCategory) query.uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ForumCategory> list() {
		String hql = "from ForumCategory";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
}
