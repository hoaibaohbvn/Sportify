package duan.sportify.rest.controller;

import java.util.List;
import java.util.Optional;

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
import duan.sportify.dao.CategoryDAO;

import duan.sportify.entities.Categories;

import duan.sportify.utils.ErrorResponse;
import javax.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/categories/")
public class CategoryProductRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	CategoryDAO categoryDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Categories>> getAll(Model model){
		return ResponseEntity.ok(categoryDAO.findAll());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Categories> getOne(@PathVariable("id") Integer id) {
		if(!categoryDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoryDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Categories> create(@Valid @RequestBody Categories category) {
	    if (category.getCategoryid() != null && categoryDAO.existsById(category.getCategoryid())) {
	        return ResponseEntity.badRequest().build();
	    }
	    categoryDAO.save(category);
	    return ResponseEntity.ok(category);
	}
	@PutMapping("update/{id}")
	public ResponseEntity<Categories> update(@PathVariable("id") Integer id,@Valid @RequestBody Categories category) {
		if(!categoryDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		categoryDAO.save(category);
		return ResponseEntity.ok(category);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if(!categoryDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		categoryDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
	// search
	@GetMapping("search")
	public ResponseEntity<List<Categories>> search(@RequestParam("categoryname") Optional<String> categoryname){
		return ResponseEntity.ok(categoryDAO.searchCategoryAdmin(categoryname));
	}
}
