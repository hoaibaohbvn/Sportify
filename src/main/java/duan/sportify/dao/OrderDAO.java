package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Orders;

public interface OrderDAO extends JpaRepository<Orders, Integer>{
	
	@Query("SELECT o FROM Orders o WHERE o.users.username = ?1")
	List<Orders> findByUsername(String username);
	
}
