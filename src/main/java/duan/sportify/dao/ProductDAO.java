package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Products;

public interface ProductDAO extends JpaRepository<Products, Integer>{
	@Query(value="SELECT COUNT(*) FROM Products;", nativeQuery = true)
	List<Object> CountProduct();
	@Query("Select p From Products p Where p.categoryid=?1")
	List<Products> findByCategoryId(String categoryid);
	
}
