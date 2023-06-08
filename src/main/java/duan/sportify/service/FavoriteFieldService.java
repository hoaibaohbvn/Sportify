package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Favoritefield;



public interface FavoriteFieldService {
	List<Favoritefield> findAll();

	Favoritefield create(Favoritefield favoritefield);

	Favoritefield updata(Favoritefield favoritefield);

	void delete(Integer id);
	
	Favoritefield findById(Integer id);
}
