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

@SuppressWarnings("unused")
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
	public void delete(Integer productid) {
		// TODO Auto-generated method stub
		productDAO.deleteById(productid);
	}

	@Override
	public Products findById(Integer productid) {
		// TODO Auto-generated method stub
		return productDAO.findById(productid).get();
	}

	@Override
	public Products findByCategory(Integer categoryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> findByCategoryId(Integer categoryid) {
		return productDAO.findByCategoryId(categoryid);
	}

	

	
}
