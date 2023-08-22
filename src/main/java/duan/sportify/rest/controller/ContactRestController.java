package duan.sportify.rest.controller;


import java.sql.Date;
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
import duan.sportify.dao.ContactDAO;
import duan.sportify.entities.Categories;
import duan.sportify.entities.Contacts;
import duan.sportify.utils.ErrorResponse;
import javax.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/contacts/")
public class ContactRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	ContactDAO contactDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Contacts>> getAll(Model model){
		return ResponseEntity.ok(contactDAO.findAll());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Contacts> getOne(@PathVariable("id") Integer id) {
		if(!contactDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(contactDAO.findById(id).get());
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if(!contactDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		contactDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
	// search
	@GetMapping("search")
	public ResponseEntity<List<Contacts>> search(@RequestParam("datecontact") Date datecontact, @RequestParam("category") Optional<String> category){
		return ResponseEntity.ok(contactDAO.findByDatecontact(datecontact, category));
	}
}
