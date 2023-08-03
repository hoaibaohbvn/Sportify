package duan.sportify.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Eventweb;



public interface EventService {
	List<Eventweb> findAll();

	Eventweb create(Eventweb events);

	Eventweb update(Eventweb events);

	void delete(Integer id);
	
	Eventweb findById(Integer id);
	
	
	List<Eventweb> getEventByMonth(int month);
	
	
	
	
	
	

}
