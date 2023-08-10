package duan.sportify.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.GlobalExceptionHandler;
import duan.sportify.dao.ShiftDAO;
import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Shifts;
import duan.sportify.entities.Users;
import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/shifts/")
public class ShiftRestController {
	
	@Autowired
	MessageSource messagesource;
	@Autowired
	ShiftDAO shiftDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Shifts>> getAll(){
		return ResponseEntity.ok(shiftDAO.findAll());
	}
}	
