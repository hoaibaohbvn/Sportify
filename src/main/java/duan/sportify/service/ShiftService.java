package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Shifts;



public interface ShiftService {
	List<Shifts> findAll();

	Shifts create(Shifts shifts);

	Shifts update(Shifts shifts);

	void delete(Integer id);
	
	Shifts findById(Integer id);
}
