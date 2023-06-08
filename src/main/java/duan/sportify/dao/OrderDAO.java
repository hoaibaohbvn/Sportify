package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Orders;

public interface OrderDAO extends JpaRepository<Orders, Integer>{

}
