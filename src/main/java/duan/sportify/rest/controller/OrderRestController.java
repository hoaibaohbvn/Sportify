package duan.sportify.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import duan.sportify.entities.Orders;
import duan.sportify.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/sportify/rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;
	
	@PostMapping()
	public Orders create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}
}
