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
	//dem product đang hoạt động
	@Query(value = "  SELECT COUNT(*) AS total_product\r\n"
			+ "FROM products\r\n"
			+ "WHERE productstatus = 1", nativeQuery = true)
	int countProductActive();
	// Thống kê top 4 sản phẩm được mua nhiều nhất trong Trang Chủ
	@Query(value = "SELECT p.productid, p.productname, SUM(od.quantity) AS total_sold, p.image, p.price, p.descriptions\r\n"
			+ "FROM products p\r\n"
			+ "JOIN orderdetails od ON p.productid = od.productid\r\n"
			+ "GROUP BY p.productid, p.productname\r\n"
			+ "ORDER BY total_sold DESC\r\n"
			+ "LIMIT 4", nativeQuery = true)
	List<Object[]> Top4OrderProduct();
}
