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
import duan.sportify.dao.FieldDAO;

import duan.sportify.entities.Field;
import duan.sportify.entities.Products;
import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/fields/")
public class FieldRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	FieldDAO fieldDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Field>> getAll(Model model){
		return ResponseEntity.ok(fieldDAO.findAll());
	}
	@GetMapping("getAllActive")
	public ResponseEntity<List<Field>> getAllActive(Model model){
		return ResponseEntity.ok(fieldDAO.findAllActive());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Field> getOne(@PathVariable("id") Integer id) {
		if(!fieldDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(fieldDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Field> create(@Valid @RequestBody Field field) {
	    if (field.getFieldid() != null && fieldDAO.existsById(field.getFieldid())) {
	        return ResponseEntity.badRequest().build();
	    }
	    fieldDAO.save(field);
	    return ResponseEntity.ok(field);
	}
	@PutMapping("update/{id}")
	public ResponseEntity<Field> update(@PathVariable("id") Integer id, @Valid @RequestBody Field field) {
		if(!fieldDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		fieldDAO.save(field);
		return ResponseEntity.ok(field);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if(!fieldDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		fieldDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
	// search team in admin
	@GetMapping("search")
	public ResponseEntity<List<Field>> search(
			@RequestParam("namefield") Optional<String> namefield,
			@RequestParam("sporttypeid") Optional<String> sporttypeid,
			@RequestParam("status") Optional<Integer> status){
		return ResponseEntity.ok(fieldDAO.searchFieldAdmin(namefield, sporttypeid, status));
	}
}
