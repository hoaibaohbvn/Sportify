package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Products;

public interface ProductDAO extends JpaRepository<Products, Integer>{
	// truy vấn đếm số lượng Product
	@Query(value="SELECT COUNT(*) FROM products;", nativeQuery = true)
	List<Object> CountProduct();
	// hàm tìm Product theo id
	Products findByProductid(Integer id );
}
