package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Teams;


@SuppressWarnings("unused")
public interface TeamService {
	List<Teams> findAll();

	Teams create(Teams teams);

	Teams update(Teams teams);

	void delete(Integer id);
	
	Teams findById(Integer id);
}
