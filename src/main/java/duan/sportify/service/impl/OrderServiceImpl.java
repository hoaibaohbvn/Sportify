package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.OrderDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Orders;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderDAO orderDAO;

	@Override
	public List<Orders> findAll() {
		// TODO Auto-generated method stub
		return orderDAO.findAll();
	}

	@Override
	public Orders create(Orders orders) {
		// TODO Auto-generated method stub
		return orderDAO.save(orders);
	}

	@Override
	public Orders update(Orders orders) {
		// TODO Auto-generated method stub
		return orderDAO.save(orders);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		orderDAO.deleteById(id);
	}

	@Override
	public Orders findById(Integer id) {
		// TODO Auto-generated method stub
		return orderDAO.findById(id).get();
	}
	
	
}
