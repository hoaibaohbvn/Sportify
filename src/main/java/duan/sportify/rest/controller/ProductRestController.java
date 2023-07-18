package duan.sportify.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.entities.Products;
import duan.sportify.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/sportify/rest/products")
public class ProductRestController {
	@Autowired
	ProductService productService;
	
	@GetMapping()
	public List<Products> getAll() {
		return productService.findAll();
	}
	
	@GetMapping("{productid}")
	public Products getOne(@PathVariable("productid") Integer productid) {
		return productService.findById(productid);
	}
}
