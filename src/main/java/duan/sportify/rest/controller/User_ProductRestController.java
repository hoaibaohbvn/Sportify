package duan.sportify.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.entities.Products;
import duan.sportify.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/sportify/rest/products_user")
public class User_ProductRestController {

	@Autowired
	ProductService productService;

	@GetMapping()
	public List<Products> getAll() {
		return productService.findAll();
	}

	@GetMapping("{id}")
	public Products getOne(@PathVariable("id") Integer id) {
		return productService.findById(id);
	}

	@PostMapping
	public Products create(@RequestBody Products product) {
		return productService.create(product);
	}

	@PutMapping("{id}")
	public Products update(@PathVariable("id") Integer id, @RequestBody Products product) {
		return productService.update(product);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		productService.delete(id);
	}
}
