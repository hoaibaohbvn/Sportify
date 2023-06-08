package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Categories;



public interface CategoryService {
	List<Categories> findAll();

	Categories create(Categories categories);

	Categories update(Categories categories);

	void delete(Integer id);
	
	Categories findById(Integer id);
}
