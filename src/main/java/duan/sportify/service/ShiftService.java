package duan.sportify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import duan.sportify.entities.Shifts;


@Service
public interface ShiftService {
	List<Shifts> findAll();
	
	Shifts create(Shifts shifts);

	Shifts update(Shifts shifts);

	void delete(Integer id);
	
	Shifts findById(Integer id);
}
