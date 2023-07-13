package duan.sportify.service;

import java.util.List;


import duan.sportify.entities.Field;






public interface FieldService {
	List<Field> findAll();

	List<Field> findBySporttypeId(String cid);
	Field create(Field fields);

	Field update(Field fields);

	void delete(Integer id);
	
	Field findById(Integer id);
}
