package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Orders;



public interface OrderService {
	List<Orders> findAll();

	Orders create(Orders orders);

	Orders update(Orders orders);

	void delete(Integer id);
	
	Orders findById(Integer id);
}
