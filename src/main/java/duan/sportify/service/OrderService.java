 package duan.sportify.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import duan.sportify.entities.Orders;



public interface OrderService {
	List<Orders> findAll();

	Orders create(JsonNode orderData);

	Orders update(Orders orders);

	void delete(Integer id);
	
	Orders findById(Integer id);

	List<Orders> findByUsername(String username);

	
	
}
