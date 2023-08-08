package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Orderdetails;

public interface OrderDetailDAO extends JpaRepository<Orderdetails, Integer>{
	
}
