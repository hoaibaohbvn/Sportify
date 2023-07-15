package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.ShiftDAO;
import duan.sportify.entities.Shifts;
import duan.sportify.service.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService{
	@Autowired
	ShiftDAO shiftDAO;

	

	@Override
	public Shifts create(Shifts shifts) {
		// TODO Auto-generated method stub
		return shiftDAO.save(shifts);
	}

	@Override
	public Shifts update(Shifts shifts) {
		// TODO Auto-generated method stub
		return shiftDAO.save(shifts);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		shiftDAO.deleteById(id);
	}

	@Override
	public Shifts findById(Integer id) {
		// TODO Auto-generated method stub
		return shiftDAO.findById(id).get();
	}

	@Override
	public List<Shifts> findAll() {
		return shiftDAO.findAll();
	}

	@Override
	public List<Shifts> findShiftById(Integer id) {
		// TODO Auto-generated method stub
		return shiftDAO.findShiftById(id);
	}

	
}
