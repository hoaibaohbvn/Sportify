package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.FavoriteFieldDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Favoritefield;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.FavoriteFieldService;

@Service
public class FavoriteServiceImpl implements FavoriteFieldService{
	@Autowired
	FavoriteFieldDAO favoriteFieldDAO;

	@Override
	public List<Favoritefield> findAll() {
		// TODO Auto-generated method stub
		return favoriteFieldDAO.findAll();
	}

	@Override
	public Favoritefield create(Favoritefield favoritefield) {
		// TODO Auto-generated method stub
		return favoriteFieldDAO.save(favoritefield);
	}

	@Override
	public Favoritefield updata(Favoritefield favoritefield) {
		// TODO Auto-generated method stub
		return favoriteFieldDAO.save(favoritefield);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		favoriteFieldDAO.deleteById(id);
	}

	@Override
	public Favoritefield findById(Integer id) {
		// TODO Auto-generated method stub
		return favoriteFieldDAO.findById(id).get();
	}
}
