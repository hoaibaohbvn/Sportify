package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Fields;



public interface FieldService {
	List<Fields> findAll();

	Fields create(Fields fields);

	Fields update(Fields fields);

	void delete(Integer id);
	
	Fields findById(Integer id);
}
