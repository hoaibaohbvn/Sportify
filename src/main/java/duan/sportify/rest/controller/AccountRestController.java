package duan.sportify.rest.controller;

import java.sql.Date;
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

import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Bookings;
import duan.sportify.entities.Users;
import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/accounts/")
public class AccountRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	UserDAO userDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Users>> getAll(Model model){
		return ResponseEntity.ok(userDAO.findAll());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Users> getOne(@PathVariable("id") String id) {
		if(!userDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Users> create(@Valid @RequestBody Users user) {
	    if (user.getUsername() != null && userDAO.existsById(user.getUsername())) {
	        return ResponseEntity.badRequest().build();
	    }
	    userDAO.save(user);
	    return ResponseEntity.ok(user);
	}
	@PutMapping("update/{id}")
	public ResponseEntity<Users> update(@PathVariable("id") String id, @Valid @RequestBody Users user) {
		if(!userDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		userDAO.save(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		if(!userDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		userDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("search")
	public ResponseEntity<List<Users>> search(@RequestParam("user") String user,
			@RequestParam("keyword") String keyword,
			@RequestParam("status") Optional<Integer> status,
			@RequestParam("role") String role){
		return ResponseEntity.ok(userDAO.searchUserAdmin(user, keyword, status, role));
	}
}
