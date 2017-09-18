package com.niit.mks.dao;

import java.util.List;

import com.niit.mks.model.Event;

public interface EventDAO {
	boolean saveOrUpdate(Event event);

	boolean delete(Event event);

	Event get(int id);

	List<Event> list();

}
