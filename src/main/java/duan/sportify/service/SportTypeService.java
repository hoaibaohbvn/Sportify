package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Field;
import duan.sportify.entities.Sporttype;


@SuppressWarnings("unused")
public interface SportTypeService {
	List<Sporttype> findAll();

	Sporttype create(Sporttype sporttype);

	Sporttype update(Sporttype sporttype);

	void delete(String id);
	
	List<Sporttype> findSporttypeById(String id);
	

}
