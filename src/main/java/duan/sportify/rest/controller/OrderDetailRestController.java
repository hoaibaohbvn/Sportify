package duan.sportify.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.GlobalExceptionHandler;
import duan.sportify.dao.BookingDetailDAO;
import duan.sportify.dao.OrderDAO;
import duan.sportify.dao.OrderDetailDAO;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Orderdetails;
import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/orderdetails/")
public class OrderDetailRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("{id}")
    public List<Orderdetails> getOrderDetails(@PathVariable("id") Integer productid) {
        return orderDetailDAO.detailOrder(productid);
    }
}
