package duan.sportify.rest.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.GlobalExceptionHandler;
import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Users;
import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/authorized/")
public class AuthorizedRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	AuthorizedDAO authorizedDAO;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}

//	@GetMapping("getAll")
//	public ResponseEntity<List<Users>> getAll(Model model){
//		return ResponseEntity.ok(userDAO.findAll());
//	}
//	@GetMapping("get/{id}")
//	public ResponseEntity<Users> getOne(@PathVariable("id") String id) {
//		if(!userDAO.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(userDAO.findById(id).get());
//	}
	@PostMapping("create")
	public ResponseEntity<Authorized> create(@RequestBody Authorized authorized) {
		if (authorized.getAuthorizedid() != null && authorizedDAO.existsById(authorized.getAuthorizedid())) {
			return ResponseEntity.badRequest().build();
		}
		authorizedDAO.save(authorized);
		return ResponseEntity.ok(authorized);
	}

	// lấy quyền của tài khoản
	@GetMapping("getRole/{username}")
	public ResponseEntity<List<Object[]>> getRole(@PathVariable("username") String username) {
		return ResponseEntity.ok(authorizedDAO.getRoleByUsername(username));
	}
	//xoa quyền
	@DeleteMapping("{authorizedid}")
	public void delete(@PathVariable("authorizedid") Integer authorizedid) {
		authorizedDAO.deleteById(authorizedid);
	}
//	@PutMapping("update/{id}")
//	public ResponseEntity<Users> update(@PathVariable("id") String id, @Valid @RequestBody Users user) {
//		if(!userDAO.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		userDAO.save(user);
//		return ResponseEntity.ok(user);
//	}

//	@DeleteMapping("delete/{id}")
//	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
//		if(!userDAO.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		userDAO.deleteById(id);
//		return ResponseEntity.ok().build();
//	}
}
