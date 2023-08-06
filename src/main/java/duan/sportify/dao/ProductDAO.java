package duan.sportify.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Products;

public interface ProductDAO extends JpaRepository<Products, Integer> {
	@Query(value = "SELECT COUNT(*) FROM Products;", nativeQuery = true)
	List<Object> CountProduct();

	@Query("Select p From Products p Where p.categoryid=?1")
	List<Products> findByCategoryId(Integer categoryid);

	@Query("Select p From Products p Where p.productname like :searchText%")
	List<Products> findByName(String searchText);

	// truy vấn tìm kiếm theo tên, loại hàng, trạng thái
	@Query(value = "SELECT * FROM products " + "WHERE (:productname IS NULL OR productname LIKE %:productname%) "
			+ "AND (:productstatus IS NULL OR productstatus = :productstatus) "
			+ "AND (:categoryid IS NULL OR categoryid = :categoryid)", nativeQuery = true)
	List<Products> searchProductAdmin(@Param("productname") Optional<String> productname, @Param("categoryid") Optional<Integer> categoryid,
			@Param("productstatus") Optional<Integer> productstatus);

	@Query(value = "select * from products where productstatus = 1", nativeQuery = true)
	List<Products> findProductActive();
}
