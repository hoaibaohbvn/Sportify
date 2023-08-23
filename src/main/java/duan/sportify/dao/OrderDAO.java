package duan.sportify.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Bookings;
import duan.sportify.entities.Orders;

public interface OrderDAO extends JpaRepository<Orders, Integer> {

	@Query("SELECT o FROM Orders o WHERE o.users.username = ?1")
	List<Orders> findByUsername(String username);

	// search admin
	@Query(value = "SELECT o.* FROM orders o \r\n" + "	        JOIN users u ON o.username = u.username \r\n"
			+ "	        where(CONCAT(u.firstname, ' ', u.lastname) LIKE %:keyword%)\r\n"
			+ "	        AND o.createdate LIKE %:datebook% \r\n" + "            and o.orderstatus like %:status%\r\n"
			+ "         and  (:payment IS NULL OR o.paymentstatus = :payment)", nativeQuery = true)
	List<Orders> findByConditions(@Param("keyword") String keyword, @Param("datebook") Date datebook,
			@Param("status") String status, @Param("payment") Optional<Integer> payment);

	// list admin
	@Query(value = "SELECT o.* FROM orders o \r\n"
			+ "	        JOIN users u ON o.username = u.username where o.paymentstatus = 0 and date(o.createdate) = curdate()", nativeQuery = true)
	List<Orders> findAllOrderAndUser();

	// dashboarsh admim
	// tổng dooanh thu order trong 6 năm
	@Query(value = "SELECT \r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 5 THEN price * quantity ELSE 0 END) AS `Prev5`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 4 THEN price * quantity ELSE 0 END) AS `Prev4`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 3 THEN price * quantity ELSE 0 END) AS `Prev3`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 2 THEN price * quantity ELSE 0 END) AS `Prev2`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 1 THEN price * quantity ELSE 0 END) AS `Prev1`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) THEN price * quantity ELSE 0 END) AS `Now`\r\n"
			+ "FROM \r\n" + "    orders\r\n" + "JOIN \r\n"
			+ "    orderdetails ON orders.orderid = orderdetails.orderid\r\n" + "WHERE \r\n"
			+ "    createdate >= DATE_SUB(NOW(), INTERVAL 6 YEAR) and orderstatus = 'Hoàn Thành';", nativeQuery = true)
	List<Object[]> getOrderSumary();

	// tổng phiếu đặt hàng qua 6 năm
	@Query(value = "SELECT\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 5 THEN orderid END) AS `Prev5`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 4 THEN orderid END) AS `Prev4`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 3 THEN orderid END) AS `Prev3`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 2 THEN orderid END) AS `Prev2`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 1 THEN orderid END) AS `Prev1`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) THEN orderid END) AS `Now`\r\n" + "FROM orders\r\n"
			+ "WHERE createdate >= DATE_SUB(CURDATE(), INTERVAL 6 YEAR);", nativeQuery = true)
	List<Object[]> countOrderDuring6Years();

	// Tổng số phiểu trong tháng này so với tháng trước
	@Query(value = "SELECT\r\n"
			+ "  (SELECT COUNT(*) FROM bookings WHERE MONTH(bookingdate) = MONTH(CURRENT_DATE()) AND YEAR(bookingdate) = YEAR(CURRENT_DATE())) +\r\n"
			+ "  (SELECT COUNT(*) FROM orders WHERE MONTH(createdate) = MONTH(CURRENT_DATE()) AND YEAR(createdate) = YEAR(CURRENT_DATE())) AS `ThisMonth`,\r\n"
			+ "  (SELECT COUNT(*) FROM bookings WHERE MONTH(bookingdate) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(bookingdate) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH)) +\r\n"
			+ "  (SELECT COUNT(*) FROM orders WHERE MONTH(createdate) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(createdate) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH)) AS `PrevMonth`;", nativeQuery = true)
	List<Object[]> countThisMonthAndThatMonth();

	// Tính tổng số doanh thu bán hàng tháng hiện tại và tháng trước cho bảng order
	@Query(value = "SELECT \r\n" + "    paymentstatus,\r\n"
			+ "    SUM(CASE WHEN MONTH(createdate) = MONTH(CURRENT_DATE) AND YEAR(createdate) = YEAR(CURRENT_DATE) THEN totalprice ELSE 0 END) AS doanhthu_thang_hien_tai,\r\n"
			+ "    SUM(CASE WHEN MONTH(createdate) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) AND YEAR(createdate) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) THEN totalprice ELSE 0 END) AS doanhthu_thang_truoc\r\n"
			+ "FROM orders\r\n"
			+ "WHERE MONTH(createdate) >= MONTH(CURRENT_DATE - INTERVAL 1 MONTH) AND YEAR(createdate) = YEAR(CURRENT_DATE)\r\n"
			+ "GROUP BY paymentstatus;", nativeQuery = true)
	List<Object[]> sumRevenueOrder2Month();

	// đếm phiếu dặt trong ngày
	@Query(value = "  SELECT COUNT(*) AS total_orders\r\n" + "FROM orders\r\n"
			+ "WHERE DATE(createdate) = CURDATE();", nativeQuery = true)
	int countOrderInDate();

	// tông số phiếu order tháng này và tháng trước
	@Query(value = "SELECT 'This Month' AS period, COUNT(*) AS total_orders\r\n" + "FROM orders\r\n"
			+ "WHERE YEAR(createdate) = YEAR(CURDATE()) AND MONTH(createdate) = MONTH(CURDATE())\r\n" + "UNION ALL\r\n"
			+ "SELECT 'Last Month', COUNT(*)\r\n" + "FROM orders\r\n"
			+ "WHERE YEAR(createdate) = YEAR(CURDATE() - INTERVAL 1 MONTH) AND MONTH(createdate) = MONTH(CURDATE() - INTERVAL 1 MONTH);\r\n"
			+ "", nativeQuery = true)
	List<Object[]> tongSoPhieuOrder2Thang();

	// tổng doanh thu tháng này và thang trước
	@Query(value = "SELECT \r\n"
			+ "    COALESCE(SUM(CASE WHEN MONTH(createdate) = MONTH(CURRENT_DATE) AND YEAR(createdate) = YEAR(CURRENT_DATE) THEN totalprice ELSE 0 END), 0) AS doanhthu_thang_hien_tai,\r\n"
			+ "    COALESCE(SUM(CASE WHEN MONTH(createdate) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) AND YEAR(createdate) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) THEN totalprice ELSE 0 END), 0) AS doanhthu_thang_truoc\r\n"
			+ "FROM orders\r\n"
			+ "WHERE MONTH(createdate) >= MONTH(CURRENT_DATE - INTERVAL 1 MONTH) AND YEAR(createdate) = YEAR(CURRENT_DATE) AND paymentstatus = 1;", nativeQuery = true)
	List<Object[]> doanhThuOrder2Month();

	// top 3 sản phẩm bán nhiều nhất
	@Query(value = "SELECT\r\n" + "    p.productname AS product_name,\r\n" + "    p.price AS product_price,\r\n"
			+ "    SUM(od.quantity) AS total_quantity_sold,\r\n"
			+ "    SUM(od.price * od.quantity) AS total_revenue\r\n" + "FROM\r\n" + "    products p\r\n" + "JOIN\r\n"
			+ "    orderdetails od ON p.productid = od.productid\r\n" + "JOIN\r\n"
			+ "    orders o ON od.orderid = o.orderid\r\n" + "WHERE\r\n" + "    o.paymentstatus = 1\r\n"
			+ "GROUP BY\r\n" + "    p.productid, p.productname, p.price\r\n" + "ORDER BY\r\n"
			+ "    total_quantity_sold DESC\r\n" + "LIMIT 3;", nativeQuery = true)
	List<Object[]> top3SanPhamBanNhieu();

	// top 5 user order nhieu nhat
	@Query(value = "SELECT\r\n" + "    u.firstname,\r\n" + "    u.lastname,\r\n" + "    u.phone,\r\n"
			+ "    COUNT(o.orderid) AS order_count,\r\n" + "    SUM(CASE\r\n"
			+ "        WHEN o.paymentstatus = 1 THEN od.price * od.quantity\r\n" + "        ELSE 0\r\n"
			+ "    END) AS total_revenue\r\n" + "FROM\r\n" + "    users u\r\n" + "JOIN\r\n"
			+ "    orders o ON u.username = o.username\r\n" + "JOIN\r\n"
			+ "    orderdetails od ON o.orderid = od.orderid\r\n" + "GROUP BY\r\n"
			+ "    u.username, u.firstname, u.lastname, u.phone\r\n" + "ORDER BY\r\n" + "    order_count DESC\r\n"
			+ "LIMIT 5;", nativeQuery = true)
	List<Object[]> top5UserOrder();

	// thông kê don ban hang trong ngày
	@Query(value = "SELECT\r\n" + "    'Tổng số order' AS description,\r\n" + "    COUNT(*) AS value,\r\n"
			+ "     SUM(CASE WHEN paymentstatus = 1 THEN totalprice  ELSE 0 END) AS doanhthu\r\n" + "FROM orders\r\n"
			+ "WHERE date(createdate) = curdate() \r\n" + "UNION ALL\r\n" + "SELECT\r\n"
			+ "    'Chưa thanh toán' AS description,\r\n" + "    COUNT(*) AS value,\r\n"
			+ "    IFNULL(SUM(totalprice), 0) AS doanhthu\r\n" + "FROM orders\r\n"
			+ "WHERE paymentstatus = 0 AND date(createdate) = curdate()\r\n" + "UNION ALL\r\n" + "SELECT\r\n"
			+ "    'Đã thanh toán' AS description,\r\n" + "    COUNT(*) AS value,\r\n"
			+ "    IFNULL(SUM(totalprice), 0) AS doanhthu\r\n" + "FROM orders\r\n"
			+ "WHERE paymentstatus = 1 AND date(createdate) = curdate();", nativeQuery = true)
	List<Object[]> thongKeOrderInDay();

	// rp
	// rp doanh thu order trong tháng
	@Query(value = "SELECT\r\n" + "  CONCAT(DAY(createdate), '-', MONTH(createdate)) AS ngay,\r\n" + "  SUM(\r\n"
			+ "    CASE\r\n" + "      WHEN paymentstatus = '1' THEN totalprice\r\n" + "      ELSE 0\r\n" + "    END\r\n"
			+ "  ) AS doanhThuThucTe,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '0' THEN  totalprice ELSE 0 END) AS chuathanhtoan,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '1' THEN  totalprice ELSE 0 END) AS hoanthanh,\r\n"
			+ "  SUM(totalprice) AS uoctinh\r\n" + "FROM orders\r\n" + "WHERE\r\n"
			+ "  YEAR(createdate) = :year AND MONTH(createdate) = :month \r\n" + "GROUP BY ngay\r\n"
			+ "ORDER BY ngay;", nativeQuery = true)
	List<Object[]> rpDoanhThuOrderTrongThang(@Param("year") String year, @Param("month") String month);

	// lấy năm của các phiếu order
	@Query(value = "SELECT DISTINCT YEAR(createdate) AS booking_year\r\n" + "FROM orders;", nativeQuery = true)
	List<Object[]> getYearOrder();

	// rp daonh thu order trong năm
	@Query(value = "SELECT\r\n" + "  CONCAT('Tháng ', MONTH(createdate)) AS thang,\r\n" + "  SUM(\r\n" + "    CASE\r\n"
			+ "      WHEN paymentstatus = '1' THEN totalprice\r\n" + "      ELSE 0\r\n" + "    END\r\n"
			+ "  ) AS doanhThuThucTe,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '0' THEN  totalprice ELSE 0 END) AS chuathanhtoan,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '1' THEN  totalprice ELSE 0 END) AS hoanthanh,\r\n"
			+ "  SUM(totalprice) AS uoctinh\r\n" + "FROM orders\r\n" + "WHERE\r\n" + "  YEAR(createdate) = :year\r\n"
			+ "GROUP BY thang\r\n" + "ORDER BY thang;", nativeQuery = true)
	List<Object[]> rpDoanhThuOrderTrongNam(@Param("year") String year);

	// rp so luong phieu order trong thang
	@Query(value = "SELECT\r\n" + "  CONCAT(DAY(createdate), '-', MONTH(createdate)) AS ngay,\r\n"
			+ "  COUNT(orderid) AS tongphieu,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '0' THEN 1 ELSE 0 END) AS chuathanhtoan,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '1' THEN 1 ELSE 0 END) AS thanhtoan\r\n" + "FROM orders\r\n"
			+ "WHERE\r\n" + "  YEAR(createdate) = :year AND MONTH(createdate) = :month \r\n" + "GROUP BY ngay\r\n"
			+ "ORDER BY ngay;", nativeQuery = true)
	List<Object[]> rpSoLuongOrderTrongThang(@Param("year") String year, @Param("month") String month);

	// rp so luong phieu order trong nam
	@Query(value = "SELECT\r\n" + "  CONCAT('Tháng ', MONTH(createdate)) AS thang,\r\n"
			+ "  COUNT(orderid) AS tongphieu,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '0' THEN 1 ELSE 0 END) AS chuathanhtoan,\r\n"
			+ "  SUM(CASE WHEN paymentstatus = '1' THEN 1 ELSE 0 END) AS thanhtoan\r\n" + "FROM orders\r\n"
			+ "WHERE\r\n" + "  YEAR(createdate) = :year\r\n" + "GROUP BY thang\r\n"
			+ "ORDER BY thang;", nativeQuery = true)
	List<Object[]> rpSoLuongOrderTrongNam(@Param("year") String year);
}
