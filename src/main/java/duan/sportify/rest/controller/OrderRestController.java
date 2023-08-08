package duan.sportify.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import duan.sportify.dao.VoucherDAO;
import duan.sportify.entities.Orders;
import duan.sportify.entities.Products;
import duan.sportify.entities.Voucher;
import duan.sportify.service.OrderService;
import duan.sportify.service.VoucherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/sportify/rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;
	@Autowired
	VoucherService voucherService;
	@Autowired
	VoucherDAO voucherDAO;
	
	@PostMapping
	public Orders create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}
	
	@GetMapping()
	public List<Voucher> getAll() {
		return voucherService.findAll();
	}

	@GetMapping("cart/{id}")
	public Voucher getOne(@PathVariable("id") String id) {
		return (Voucher) voucherService.findByVoucherId(id);
	}
}
