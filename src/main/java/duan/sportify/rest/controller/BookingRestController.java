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
import duan.sportify.dao.BookingDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.entities.Bookings;
import duan.sportify.entities.Field;
import duan.sportify.entities.Sporttype;
import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/bookings/")
public class BookingRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	BookingDAO bookingDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Bookings>> getAll(Model model){
		return ResponseEntity.ok(bookingDAO.findAllBookingAndUser());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Bookings> getOne(@PathVariable("id") Integer id) {
		if(!bookingDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bookingDAO.findById(id).get());
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<Bookings> update(@PathVariable("id") Integer id, @Valid @RequestBody Bookings booking) {
		if(!bookingDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		bookingDAO.save(booking);
		return ResponseEntity.ok(booking);
	}
	
	// search
	@GetMapping("search")
	public ResponseEntity<List<Bookings>> search(@RequestParam("keyword") String keyword, @RequestParam("datebook") Date datebook,@RequestParam("status") String status){
		return ResponseEntity.ok(bookingDAO.findByConditions(keyword, datebook, status));
	}
}
