package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.TeamDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Teams;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.TeamService;

@SuppressWarnings("unused")
@Service
public class TeamServiceImpl implements TeamService{
	@Autowired
	TeamDAO teamDAO;

	@Override
	public List<Teams> findAll() {
		// TODO Auto-generated method stub
		return teamDAO.findAll();
	}

	@Override
	public Teams create(Teams teams) {
		// TODO Auto-generated method stub
		return teamDAO.save(teams);
	}

	@Override
	public Teams update(Teams teams) {
		// TODO Auto-generated method stub
		return teamDAO.save(teams);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		teamDAO.deleteById(id);
	}

	@Override
	public Teams findById(Integer id) {
		// TODO Auto-generated method stub
		return teamDAO.findById(id).get();
	}
}
