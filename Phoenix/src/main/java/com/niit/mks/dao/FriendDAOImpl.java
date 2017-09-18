package com.niit.mks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.mks.model.Friend;

@Repository("FriendDAO")
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean saveOrUpdate(Friend friend) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(friend);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean delete(Friend friend) {
		try {
			sessionFactory.getCurrentSession().delete(friend);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Friend get(int userId, int friendId) {
		String hql = "from Friend where userId = " + userId + " and friendId = " + friendId;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		try {
			return (Friend) query.uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Friend> list() {
		String hql = "from Friend";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Friend> getFriends(int userId) {
		String hql = "from Friend where userId = " + userId + " and status = 'ACCEPT'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Friend> myFriends(int userId) {
		String hql = "from Friend where friendId = " + userId + " and status = 'ACCEPT'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Friend> getRequest(int userId) {
		String hql = "from Friend where friendId = " + userId + " and status = 'PENDING'";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Friend> getTopFriends(int n) {
		String hql = "FROM Friend WHERE status = 'ACCEPT' ORDER BY id DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(1);
		query.setMaxResults(n);
		return query.list();
	}

}
