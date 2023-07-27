package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.SportTypeDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Sporttype;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.SportTypeService;

@SuppressWarnings("unused")
@Service
public class SportTypeServiceImpl implements SportTypeService{
	@Autowired
	SportTypeDAO sportTypeDAO;

	@Override
	public List<Sporttype> findAll() {
		// TODO Auto-generated method stub
		return sportTypeDAO.findAll();
	}

	@Override
	public Sporttype create(Sporttype sporttype) {
		// TODO Auto-generated method stub
		return sportTypeDAO.save(sporttype);
	}

	@Override
	public Sporttype update(Sporttype sporttype) {
		// TODO Auto-generated method stub
		return sportTypeDAO.save(sporttype);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		sportTypeDAO.deleteById(id);
	}

	

	@Override
	public List<Sporttype> findSporttypeById(String id) {
		// TODO Auto-generated method stub
		return sportTypeDAO.findSporttypeById(id);
	}

	
}
