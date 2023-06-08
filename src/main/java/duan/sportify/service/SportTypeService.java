package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Sporttype;



public interface SportTypeService {
	List<Sporttype> findAll();

	Sporttype create(Sporttype sporttype);

	Sporttype update(Sporttype sporttype);

	void delete(String id);
	
	Sporttype findById(String id);
}
