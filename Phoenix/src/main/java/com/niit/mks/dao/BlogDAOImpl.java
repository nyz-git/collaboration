package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.Blog;

@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public BlogDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	@Transactional
	public boolean saveOrUpdate(Blog blog) {
		
		try{
			sessionFactory.getCurrentSession().saveOrUpdate(blog);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(Blog blog) {
		try {
		sessionFactory.getCurrentSession().delete(blog);
		return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	@Transactional
	public Blog get(int blogId) {
	
		String hql = "from Blog where blogId = :blogId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("blogId", blogId);
		try {
			return (Blog) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Transactional

	public List<Blog> getByUserId(int userId) {
		String hql = "from Blog where userId = '"+userId+"' and status = 'APPROVE'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Transactional

	public List<Blog> list() {
		return sessionFactory.openSession().createQuery("From Blog").list();
	}
	@Transactional

	
	public List<Blog> getblogsByStatus(String status)
	{
		String hql = "from Blog where status = '"+status+"'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
		
	}
	@Transactional

	
	public List<Blog> getTopBlogs(int n) {
		String hql = "FROM Blog WHERE status = 'APPROVE' ORDER BY postDate DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(1);
		query.setMaxResults(n);
		return query.list();
	}
	}


