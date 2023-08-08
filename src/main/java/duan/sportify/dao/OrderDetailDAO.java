package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import duan.sportify.entities.Orderdetails;


public interface OrderDetailDAO extends JpaRepository<Orderdetails, Integer>{
	@Query(value = "SELECT * FROM orderdetails where orderid = :orderid", nativeQuery =   true)
    List<Orderdetails> detailOrder(@Param("orderid") Integer orderid);
}
