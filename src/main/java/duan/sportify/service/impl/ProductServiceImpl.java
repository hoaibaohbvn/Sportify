package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.ProductDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Products;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDAO productDAO;

	@Override
	public List<Products> findAll() {
		// TODO Auto-generated method stub
		return productDAO.findAll();
	}

	@Override
	public Products create(Products products) {
		// TODO Auto-generated method stub
		return productDAO.save(products);
	}

	@Override
	public Products update(Products products) {
		// TODO Auto-generated method stub
		return productDAO.save(products);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		productDAO.deleteById(id);
	}

	@Override
	public Products findById(Integer id) {
		// TODO Auto-generated method stub
		return productDAO.findById(id).get();
	}
}
