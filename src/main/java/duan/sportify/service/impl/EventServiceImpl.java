package duan.sportify.service.impl;

//import java.awt.Event;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Authorized;

import duan.sportify.entities.Eventweb;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.EventService;

@SuppressWarnings("unused")
@Service
public class EventServiceImpl implements EventService{
	@Autowired
	EventDAO eventDAO;

	@Override
	public List<Eventweb> findAll() {
		// TODO Auto-generated method stub
		return eventDAO.findAll();
	}

	@Override
	public Eventweb create(Eventweb events) {
		// TODO Auto-generated method stub
		return eventDAO.save(events);
	}

	@Override
	public Eventweb update(Eventweb events) {
		// TODO Auto-generated method stub
		return eventDAO.save(events);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		eventDAO.deleteById(id);
	}

	@Override
	public Eventweb findById(Integer id) {
		// TODO Auto-generated method stub
		return eventDAO.findById(id).get();
	}
	
    
    @Override
    public List<Eventweb> getEventByMonth(int month) {
        return eventDAO.findByMonth(month);
    }

    
}
