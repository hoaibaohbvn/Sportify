package duan.sportify.service;

import java.util.List;

import duan.sportify.dao.ProductDAO;
import duan.sportify.entities.Products;



public interface ProductService {
	List<Products> findAll();

	Products create(Products products);

	Products update(Products products);

	void delete(Integer id);
	
	
	
	Products findByCategory(Integer categoryid);

	static Products findById(Integer productid) {
		return ProductDAO.findById(productid).get();
	}
}
