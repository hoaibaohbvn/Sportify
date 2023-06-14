package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Field;




public interface FieldService {
	List<Field> findAll();

	Field create(Field fields);

	Field update(Field fields);

	void delete(Integer id);
	
	Field findById(Integer id);
}
