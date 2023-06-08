package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Events;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.EventService;

@Service
public class EventServiceImpl implements EventService{
	@Autowired
	EventDAO eventDAO;

	@Override
	public List<Events> findAll() {
		// TODO Auto-generated method stub
		return eventDAO.findAll();
	}

	@Override
	public Events create(Events events) {
		// TODO Auto-generated method stub
		return eventDAO.save(events);
	}

	@Override
	public Events update(Events events) {
		// TODO Auto-generated method stub
		return eventDAO.save(events);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		eventDAO.deleteById(id);
	}

	@Override
	public Events findById(Integer id) {
		// TODO Auto-generated method stub
		return eventDAO.findById(id).get();
	}
}
