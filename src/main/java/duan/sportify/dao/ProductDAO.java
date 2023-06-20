package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Products;

public interface ProductDAO extends JpaRepository<Products, Integer>{

}
