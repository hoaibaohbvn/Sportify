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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.GlobalExceptionHandler;
import duan.sportify.dao.BookingDAO;
import duan.sportify.dao.OrderDAO;
import duan.sportify.entities.Bookings;
import duan.sportify.entities.Orders;
import duan.sportify.service.OrderService;
import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/orders/")
public class Admin_OrderRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	OrderService orderService;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}

	@GetMapping("getAll")
	public ResponseEntity<List<Orders>> getAll(Model model) {
		return ResponseEntity.ok(orderDAO.findAllOrderAndUser());
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Orders> getOne(@PathVariable("id") Integer id) {
		if (!orderDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderDAO.findById(id).get());
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Orders> update(@PathVariable("id") Integer id, @Valid @RequestBody Orders order) {
		if (!orderDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		orderDAO.save(order);
		return ResponseEntity.ok(order);
	}

	// search
	@GetMapping("search")
	public ResponseEntity<List<Orders>> search(@RequestParam("keyword") String keyword,
			@RequestParam("datebook") Date datebook, @RequestParam("status") String status,
			@RequestParam("payment") Optional<Integer> payment) {
		return ResponseEntity.ok(orderDAO.findByConditions(keyword, datebook, status, payment));
	}

	// xác nhận đơn hàng
	@PutMapping("confirm/{id}")
	public ResponseEntity<Orders> confirmOrder(@PathVariable("id") Integer id, @Valid @RequestBody Orders order) {
		if (!orderDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Orders updateOrder = orderService.findById(id);
		updateOrder.setOrderstatus("Đang Giao");
		orderDAO.save(updateOrder);
		return ResponseEntity.ok(order);
	}

	// hủy đơn hàng
	@PutMapping("cancel/{id}")
	public ResponseEntity<Orders> cancelOrder(@PathVariable("id") Integer id, @Valid @RequestBody Orders order) {
		if (!orderDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Orders updateOrder = orderService.findById(id);
		updateOrder.setOrderstatus("Hủy Đặt");
		orderDAO.save(updateOrder);
		return ResponseEntity.ok(order);
	}
}
