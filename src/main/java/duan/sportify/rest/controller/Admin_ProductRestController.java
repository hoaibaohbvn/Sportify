package duan.sportify.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import duan.sportify.GlobalExceptionHandler;
import duan.sportify.dao.ProductDAO;

import duan.sportify.entities.Products;

import duan.sportify.utils.ErrorResponse;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/products/")
public class Admin_ProductRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	ProductDAO productDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Products>> getAll(Model model){
		return ResponseEntity.ok(productDAO.findProductActive());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Products> getOne(@PathVariable("id") Integer id) {
		if(!productDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Products> create(@Valid @RequestBody Products product) {
	    if (product.getProductid() != null && productDAO.existsById(product.getProductid())) {
	        return ResponseEntity.badRequest().build();
	    }
	    productDAO.save(product);
	    return ResponseEntity.ok(product);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Products> update(@PathVariable("id") Integer id, @Valid @RequestBody Products product) {
		if(!productDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		productDAO.save(product);
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if(!productDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		productDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
	@GetMapping("search")
	public ResponseEntity<List<Products>> search(
			@RequestParam("productname") Optional<String> productname,
			@RequestParam("categoryid") Optional<Integer> categoryid,
			@RequestParam("productstatus") Optional<Integer> productstatus
		){
		return ResponseEntity.ok(productDAO.searchProductAdmin(productname, categoryid, productstatus));
	}
}
