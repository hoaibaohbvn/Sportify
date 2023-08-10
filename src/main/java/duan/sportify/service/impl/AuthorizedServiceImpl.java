package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.service.AuthorizedService;

@Service
public class AuthorizedServiceImpl implements AuthorizedService{
	@Autowired
	AuthorizedDAO authorizedDAO;

	@Override
	public List<Authorized> findAll() {
		// TODO Auto-generated method stub
		return authorizedDAO.findAll();
	}

	@Override
	public Authorized create(Authorized authorized) {
		// TODO Auto-generated method stub
		return authorizedDAO.save(authorized);
	}

	@Override
	public Authorized update(Authorized authorized) {
		// TODO Auto-generated method stub
		return authorizedDAO.save(authorized);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		authorizedDAO.deleteById(id);
	}

	@Override
	public Authorized findById(Integer id) {
		// TODO Auto-generated method stub
		return authorizedDAO.findById(id).get();
	}

	@Override
	public Authorized findAllAuthorized(String username) {
		// TODO Auto-generated method stub
		return authorizedDAO.findAllAuthorized(username);
	}
}
