package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Orders;

public interface OrderDAO extends JpaRepository<Orders, Integer>{
	// dashboarsh admim
	// tổng dooanh thu order trong 6 năm
	@Query(value = "SELECT \r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 5 THEN price * quantity ELSE 0 END) AS `Prev5`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 4 THEN price * quantity ELSE 0 END) AS `Prev4`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 3 THEN price * quantity ELSE 0 END) AS `Prev3`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 2 THEN price * quantity ELSE 0 END) AS `Prev2`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) - 1 THEN price * quantity ELSE 0 END) AS `Prev1`,\r\n"
			+ "    SUM(CASE WHEN YEAR(createdate) = YEAR(NOW()) THEN price * quantity ELSE 0 END) AS `Now`\r\n"
			+ "FROM \r\n"
			+ "    orders\r\n"
			+ "JOIN \r\n"
			+ "    orderdetails ON orders.orderid = orderdetails.orderid\r\n"
			+ "WHERE \r\n"
			+ "    createdate >= DATE_SUB(NOW(), INTERVAL 6 YEAR) and orderstatus = 'Hoàn Thành';", nativeQuery = true)
	List<Object[]> getOrderSumary();
	// tổng phiếu đặt hàng qua 6 năm
	@Query(value = "SELECT\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 5 THEN orderid END) AS `Prev5`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 4 THEN orderid END) AS `Prev4`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 3 THEN orderid END) AS `Prev3`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 2 THEN orderid END) AS `Prev2`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) - 1 THEN orderid END) AS `Prev1`,\r\n"
			+ "  COUNT(CASE WHEN YEAR(createdate) = YEAR(CURDATE()) THEN orderid END) AS `Now`\r\n"
			+ "FROM orders\r\n"
			+ "WHERE createdate >= DATE_SUB(CURDATE(), INTERVAL 6 YEAR);", nativeQuery = true)
	List<Object[]> countOrderDuring6Years();
	// Tổng số phiểu trong tháng này so với tháng trước
	@Query(value = "SELECT\r\n"
			+ "  (SELECT COUNT(*) FROM bookings WHERE MONTH(bookingdate) = MONTH(CURRENT_DATE()) AND YEAR(bookingdate) = YEAR(CURRENT_DATE())) +\r\n"
			+ "  (SELECT COUNT(*) FROM orders WHERE MONTH(createdate) = MONTH(CURRENT_DATE()) AND YEAR(createdate) = YEAR(CURRENT_DATE())) AS `ThisMonth`,\r\n"
			+ "  (SELECT COUNT(*) FROM bookings WHERE MONTH(bookingdate) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(bookingdate) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH)) +\r\n"
			+ "  (SELECT COUNT(*) FROM orders WHERE MONTH(createdate) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(createdate) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH)) AS `PrevMonth`;", nativeQuery = true)
	List<Object[]> countThisMonthAndThatMonth();
	
	//Tính tổng số doanh thu bán hàng tháng hiện tại và tháng trước cho bảng order
	@Query(value = "SELECT\r\n"
			+ "  SUM(CASE WHEN MONTH(createdate) = MONTH(CURRENT_DATE()) AND YEAR(createdate) = YEAR(CURRENT_DATE()) THEN od.price * od.quantity ELSE 0 END) AS revenue_this_month,\r\n"
			+ "  SUM(CASE WHEN MONTH(createdate) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(createdate) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH) THEN od.price * od.quantity ELSE 0 END) AS revenue_last_month\r\n"
			+ "FROM orders o\r\n"
			+ "JOIN orderdetails od ON o.orderid = od.orderid\r\n"
			+ "WHERE o.paymentstatus = 1 or o.orderstatus = 'Hoàn Thành';", nativeQuery = true)
	List<Object[]> sumRevenueOrder2Month();
	
}
