package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.SportTypeDAO;
import duan.sportify.dto.SportTypeDTO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Sporttype;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.SportTypeService;

@Service
public class SportTypeServiceImpl implements SportTypeService{
	@Autowired
	SportTypeDAO sportTypeDAO;
	@Override
	public List<Sporttype> findSporttypeById(String id) {
		// TODO Auto-generated method stub
		return sportTypeDAO.findSporttypeById(id);
	}
	@Override
	public List<Sporttype> getAll() {
		// TODO Auto-generated method stub
		return sportTypeDAO.findAll();
	}
	@Override
	public SportTypeDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SportTypeDTO createOrUpdate(SportTypeDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Sporttype> findAll() {
		// TODO Auto-generated method stub
		return sportTypeDAO
				.findAll();
	}
}
