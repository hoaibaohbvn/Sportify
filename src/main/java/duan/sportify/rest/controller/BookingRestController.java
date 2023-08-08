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
import duan.sportify.dao.BookingDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.entities.Bookings;
import duan.sportify.entities.Field;
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
		return ResponseEntity.ok(bookingDAO.findAll());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Bookings> getOne(@PathVariable("id") Integer id) {
		if(!bookingDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bookingDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Bookings> create(@Valid @RequestBody Bookings booking) {
	    if (booking.getBookingid() != null && bookingDAO.existsById(booking.getBookingid())) {
	        return ResponseEntity.badRequest().build();
	    }
	    bookingDAO.save(booking);
	    return ResponseEntity.ok(booking);
	}
	@PutMapping("update/{id}")
	public ResponseEntity<Bookings> update(@PathVariable("id") Integer id, @Valid @RequestBody Bookings booking) {
		if(!bookingDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		bookingDAO.save(booking);
		return ResponseEntity.ok(booking);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if(!bookingDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		bookingDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
