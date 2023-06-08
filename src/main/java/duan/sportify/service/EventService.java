package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Events;



public interface EventService {
	List<Events> findAll();

	Events create(Events events);

	Events update(Events events);

	void delete(Integer id);
	
	Events findById(Integer id);
}
