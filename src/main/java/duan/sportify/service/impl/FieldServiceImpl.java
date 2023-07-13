package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import duan.sportify.dao.FieldDAO;

import duan.sportify.entities.Field;


import duan.sportify.service.FieldService;


@Service
public class FieldServiceImpl implements FieldService{
	@Autowired
	FieldDAO fieldDAO;
	
	@Override
	public List<Field> findAll() {
		// TODO Auto-generated method stub
		return fieldDAO.findAll();
	}
	@Override
	public Field findById(Integer id) {
		// TODO Auto-generated method stub
		return fieldDAO.findById(id).get();
	}
	@Override
	public List<Field> findBySporttypeId(String cid) {
		// TODO Auto-generated method stub
		return fieldDAO.findBySporttypeId(cid);
	}
	@Override
	public Field create(Field fields) {
		// TODO Auto-generated method stub
		return fieldDAO.save(fields);
	}

	@Override
	public Field update(Field fields) {
		// TODO Auto-generated method stub
		return fieldDAO.save(fields);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		fieldDAO.deleteById(id);
	}
	@Override
	public List<Field> listPriceMin(){
		return fieldDAO.listPriceMin();
	}
	@Override
	public List<Field> listMinPriceOfSportype(String cid) {
		// TODO Auto-generated method stub
		return fieldDAO.listMinPriceOfSportype(cid);
	}
	@Override
	public List<Field> listMaxPriceOfSportype(String cid) {
		// TODO Auto-generated method stub
		return fieldDAO.listMaxPriceOfSportype(cid);
	}
	@Override
	public List<Field> listPriceMax() {
		// TODO Auto-generated method stub
		return fieldDAO.listPriceMax();
	}
	


	
	
}
