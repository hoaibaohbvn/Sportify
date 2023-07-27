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
	
	@Query("Select p From Products p Where p.productname like :searchText%")
	List<Products> findByName(String searchText);
	// truy vấn tìm kiếm theo tên, loại hàng, trạng thái
	@Query(value = "SELECT * \r\n"
			+ "FROM products\r\n"
			+ "WHERE productname LIKE '%?1%'\r\n"
			+ "  AND categoryid Like '%?2%'\r\n"
			+ "  AND productstatus Like '%?3%';", nativeQuery = true)
	List<Products> searchProductAdmin(String productname, Integer categoryid, Integer productstatus);
	@Query(value = "select * from products where productstatus = 1", nativeQuery = true)
	List<Products> findProductActive();
}

