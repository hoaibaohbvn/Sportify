package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Orderdetails;


@SuppressWarnings("unused")
public interface OrderDetailService {
	List<Orderdetails> findAll();

	Orderdetails create(Orderdetails orderdetails);

	Orderdetails update(Orderdetails orderdetails);

	void delete(Integer id);
	
	Orderdetails findById(Integer id);
}
