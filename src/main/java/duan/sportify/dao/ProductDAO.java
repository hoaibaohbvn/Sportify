package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Products;


public interface ProductDAO extends JpaRepository<Products, Integer>{

//	@Query("SELECT * FROM products")
//	List<Products> findAll();
}
