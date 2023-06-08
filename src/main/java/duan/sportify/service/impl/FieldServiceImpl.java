package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Fields;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.FieldService;

@Service
public class FieldServiceImpl implements FieldService{
	@Autowired
	FieldDAO fieldDAO;

	@Override
	public List<Fields> findAll() {
		// TODO Auto-generated method stub
		return fieldDAO.findAll();
	}

	@Override
	public Fields create(Fields fields) {
		// TODO Auto-generated method stub
		return fieldDAO.save(fields);
	}

	@Override
	public Fields update(Fields fields) {
		// TODO Auto-generated method stub
		return fieldDAO.save(fields);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		fieldDAO.deleteById(id);
	}

	@Override
	public Fields findById(Integer id) {
		// TODO Auto-generated method stub
		return fieldDAO.findById(id).get();
	}
	
}
