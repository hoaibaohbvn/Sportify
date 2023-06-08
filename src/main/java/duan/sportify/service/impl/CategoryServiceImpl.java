package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.CategoryDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Categories;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryDAO categoryDAO;

	@Override
	public List<Categories> findAll() {
		// TODO Auto-generated method stub
		return categoryDAO.findAll();
	}

	@Override
	public Categories create(Categories categories) {
		// TODO Auto-generated method stub
		return categoryDAO.save(categories);
	}

	@Override
	public Categories update(Categories categories) {
		// TODO Auto-generated method stub
		return categoryDAO.save(categories);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		categoryDAO.deleteById(id);
	}

	@Override
	public Categories findById(Integer id) {
		// TODO Auto-generated method stub
		return categoryDAO.findById(id).get();
	}

	
}
