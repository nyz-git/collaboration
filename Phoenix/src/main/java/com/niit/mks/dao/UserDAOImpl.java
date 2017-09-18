package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	User user;
	
	

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Boolean saveOrUpdate(User user) {

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Boolean delete(User user) {
		try {
			sessionFactory.getCurrentSession().delete(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public User getByUsername(String username) {
		try {

			String selectUser = "From User where username = :username";
			Query query = (Query) sessionFactory.getCurrentSession().createQuery(selectUser);
			query.setParameter("username", username);
			return (User) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public User getById(int userId) {
		return (User) sessionFactory.getCurrentSession().get(User.class, userId);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> list() {

		return sessionFactory.getCurrentSession().createQuery("From User").list();
	}

	@Transactional
	public List<User> getUsersByStatus(String status) {

		String hql = "from User where status = '" + status + "'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Transactional
	public boolean updateUserProfileId(String fileName, int userId) {
		String updateQuery = "UPDATE User SET profileId = :fileName WHERE userId = :userId";
		Query query = sessionFactory.getCurrentSession().createQuery(updateQuery);
		query.setParameter("userId", userId);
		query.setParameter("fileName", fileName);
		try {
			query.executeUpdate();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	@Transactional
	public User validate(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String hql = "from User where username = '" + username + "' and password = '" + password + "'";
		Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
		try {

			return (User) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<User> getTopUsers(int n) {
		String hql = "FROM User WHERE status = 'APPROVE' ORDER BY userId DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(1);
		query.setMaxResults(n);
		return query.list();
	}

}