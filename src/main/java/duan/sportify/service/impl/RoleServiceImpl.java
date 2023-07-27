package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.RoleDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Roles;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.RoleService;

@SuppressWarnings("unused")
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleDAO roleDAO;

	@Override
	public List<Roles> findAll() {
		// TODO Auto-generated method stub
		return roleDAO.findAll();
	}

	@Override
	public Roles create(Roles roles) {
		// TODO Auto-generated method stub
		return roleDAO.save(roles);
	}

	@Override
	public Roles update(Roles roles) {
		// TODO Auto-generated method stub
		return roleDAO.save(roles);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		roleDAO.deleteById(id);
	}

	@Override
	public Roles findById(String id) {
		// TODO Auto-generated method stub
		return roleDAO.findById(id).get();
	}
}
