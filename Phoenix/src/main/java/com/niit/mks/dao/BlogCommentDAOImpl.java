package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.BlogComment;

@Repository("BlogCommentDAO")

public class BlogCommentDAOImpl implements BlogCommentDAO {

	@Autowired	
	SessionFactory sessionFactory;
	
	public BlogCommentDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}
	@Transactional
	public boolean saveOrUpdate(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(blogComment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean delete(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().delete(blogComment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public BlogComment get(int id) {
		String hql = "from BlogComment where id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("id", id);
		try {
			return (BlogComment) query.uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<BlogComment> list(int blogId) {
		String hql = "from BlogComment where blogId = :blogId";
		 Query query = sessionFactory.getCurrentSession().createQuery(hql);
		 query.setParameter("blogId",blogId);
		return query.list();
	}
	
}
