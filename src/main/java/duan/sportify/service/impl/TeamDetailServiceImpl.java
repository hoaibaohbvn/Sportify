package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.TeamDetailDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Teamdetails;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.TeamDetailService;

@SuppressWarnings("unused")
@Service
public class TeamDetailServiceImpl implements TeamDetailService{
	@Autowired
	TeamDetailDAO teamDetailDAO;

	@Override
	public List<Teamdetails> findAll() {
		// TODO Auto-generated method stub
		return teamDetailDAO.findAll();
	}

	@Override
	public Teamdetails create(Teamdetails teamdetails) {
		// TODO Auto-generated method stub
		return teamDetailDAO.save(teamdetails);
	}

	@Override
	public Teamdetails update(Teamdetails teamdetails) {
		// TODO Auto-generated method stub
		return teamDetailDAO.save(teamdetails);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		teamDetailDAO.deleteById(id);
	}

	@Override
	public Teamdetails findById(Integer id) {
		// TODO Auto-generated method stub
		return teamDetailDAO.findById(id).get();
	}
	
}
