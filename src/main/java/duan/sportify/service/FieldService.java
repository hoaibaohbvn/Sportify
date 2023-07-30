package duan.sportify.service;

import java.util.List;


import duan.sportify.entities.Field;
import duan.sportify.entities.Shifts;





@SuppressWarnings("unused")
public interface FieldService {
	List<Field> findAll();

	List<Field> findBySporttypeId(String cid);
	Field create(Field fields);

	Field update(Field fields);

	void delete(Integer id);
	
	Field findById(Integer id);

	
	List<Field> findSearch(String dateInput, String categorySelect, Integer shiftSelect);
	
	List<Field> findFieldById(Integer id);
	
	List<Field> findBySporttypeIdlimit3(String cid);
	String findNameSporttypeById(Integer id);
	
	String findIdSporttypeById(Integer id);
	
}
