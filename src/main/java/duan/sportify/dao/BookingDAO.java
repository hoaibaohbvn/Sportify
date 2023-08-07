package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import duan.sportify.entities.Bookings;


public interface BookingDAO extends JpaRepository<Bookings, Integer>{
	
	
	@Query(value = "SELECT \r\n"
            + "    b.bookingid,\r\n"
            + "    b.bookingdate,\r\n"
            + "    b.bookingprice,\r\n"
            + "    b.note,\r\n"
            + "    b.bookingstatus,\r\n"
            + "    f.namefield,\r\n"
            + "    f.image\r\n"
            + "FROM \r\n"
            + "    bookings AS b\r\n"
            + "JOIN \r\n"
            + "    bookingdetails AS bd ON b.bookingid = bd.bookingid\r\n"
            + "JOIN \r\n"
            + "    field AS f ON bd.fieldid = f.fieldid\r\n"
            + "WHERE \r\n"
            + "    b.username = :username ORDER BY bookingdate DESC LIMIT 20", nativeQuery = true)
	List<Object[]> getBookingInfoByUsername(String username);
	@Query(value = "SELECT \r\n"
			+ "    b.bookingid,\r\n"
			+ "    b.bookingdate,\r\n"
			+ "    b.bookingstatus,\r\n"
			+ "    bd.shiftid,\r\n"
			+ "    bd.playdate,\r\n"
			+ "    bd.price,\r\n"
			+ "    f.namefield,\r\n"
			+ "    f.image,\r\n"
			+ "    s.nameshift\r\n"
			+ "FROM \r\n"
			+ "    bookings AS b\r\n"
			+ "JOIN \r\n"
			+ "    bookingdetails AS bd ON b.bookingid = bd.bookingid\r\n"
			+ "JOIN \r\n"
			+ "    field AS f ON bd.fieldid = f.fieldid\r\n"
			+ "JOIN \r\n"
			+ "    shifts AS s ON bd.shiftid = s.shiftid\r\n"
			+ "WHERE \r\n"
			+ "    b.bookingid = :bookingid", nativeQuery = true)
	Object[] getBookingInfoByBookingDetail(String bookingid);
	
	@Query(value = "select count(*) from bookings", nativeQuery = true)
	int countBooking();
	
	// dashboard
		// tổng phiểu booking and order
		@Query(value = "SELECT COUNT(*) AS total_count\r\n"
				+ "FROM (\r\n"
				+ "    SELECT bookingid AS id, bookingdate AS date FROM bookings WHERE DATE(bookingdate) = CURDATE()\r\n"
				+ "    UNION ALL\r\n"
				+ "    SELECT orderid AS id, createdate AS date FROM orders WHERE DATE(createdate) = CURDATE()\r\n"
				+ ") AS combined_data;", nativeQuery = true)
		public Long sumOrderBooking();
		// tổng doanh thu booking trong 6 ngăm trở lại
		@Query(value = "SELECT\r\n"
				+ "	SUM(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 5 THEN bookingprice ELSE 0 END) AS revenue_5_years_ago,\r\n"
				+ "  SUM(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 4 THEN bookingprice ELSE 0 END) AS revenue_4_years_ago,\r\n"
				+ "  SUM(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 3 THEN bookingprice ELSE 0 END) AS revenue_3_years_ago,\r\n"
				+ "  SUM(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 2 THEN bookingprice ELSE 0 END) AS revenue_2_years_ago,\r\n"
				+ " \r\n"
				+ "   SUM(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 1 THEN bookingprice ELSE 0 END) AS revenue_last_year,\r\n"
				+ "  SUM(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) THEN bookingprice ELSE 0 END) AS revenue_current_year\r\n"
				+ "  \r\n"
				+ "FROM\r\n"
				+ "  bookings\r\n"
				+ "WHERE\r\n"
				+ "  YEAR(bookingdate) >= YEAR(CURDATE()) - 6\r\n"
				+ "  AND bookingstatus IN ('Hoàn Thành', 'Đã Cọc')", nativeQuery = true)
	    List<Object[]> getBookingPriceSummary();
	    // đếm tổng số phiếu trong 6 năm
	    @Query(value = "SELECT\r\n"
	    		+ "  COUNT(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 5 THEN bookingid END) AS `Prev5`,\r\n"
	    		+ "  COUNT(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 4 THEN bookingid END) AS `Prev4`,\r\n"
	    		+ "  COUNT(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 3 THEN bookingid END) AS `Prev3`,\r\n"
	    		+ "  COUNT(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 2 THEN bookingid END) AS `Prev2`,\r\n"
	    		+ "  COUNT(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) - 1 THEN bookingid END) AS `Prev1`,\r\n"
	    		+ "  COUNT(CASE WHEN YEAR(bookingdate) = YEAR(CURDATE()) THEN bookingid END) AS `Now`\r\n"
	    		+ "FROM bookings\r\n"
	    		+ "WHERE bookingdate >= DATE_SUB(CURDATE(), INTERVAL 6 YEAR);", nativeQuery = true)
	    List<Object[]> countBookingOn6YearReturn();
	    
	    //Tính tổng số doanh thu dặt sân tháng hiện tại và tháng trước cho bảng bookings
	    @Query(value = "SELECT\r\n"
	    		+ "  SUM(CASE WHEN MONTH(bookingdate) = MONTH(CURRENT_DATE()) AND YEAR(bookingdate) = YEAR(CURRENT_DATE()) THEN bookingprice ELSE 0 END) AS revenue_this_month,\r\n"
	    		+ "  SUM(CASE WHEN MONTH(bookingdate) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(bookingdate) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH) THEN bookingprice ELSE 0 END) AS revenue_last_month\r\n"
	    		+ "FROM bookings where bookingstatus like 'Hoàn Thành' or bookingstatus like 'Đã Cọc';", nativeQuery = true)
	    List<Object[]> sumThisThatMonth();
	    
	    // tính tổng hoàn tiền tháng này và tháng trước
	    @Query(value = "SELECT\r\n"
	    		+ "  SUM(CASE WHEN bookingstatus = 'Hủy Đặt' AND MONTH(bookingdate) = MONTH(CURRENT_DATE()) AND YEAR(bookingdate) = YEAR(CURRENT_DATE()) THEN bookingprice ELSE 0 END) * 2 AS `Tháng này`,\r\n"
	    		+ "  SUM(CASE WHEN bookingstatus = 'Hủy Đặt' AND MONTH(bookingdate) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(bookingdate) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH) THEN bookingprice ELSE 0 END) * 2 AS `Tháng trước`\r\n"
	    		+ "FROM bookings;\r\n"
	    		+ "", nativeQuery = true)
	    List<Object[]> total_cancelled_amount_this_that_month();
	    // Số liệu thống kê dặt sân
	    @Query(value = "SELECT\r\n"
	    		+ "  'Tổng số booking' AS description,\r\n"
	    		+ "  COUNT(*) AS value,\r\n"
	    		+ "  SUM(bookingprice) AS total_revenue\r\n"
	    		+ "FROM bookings\r\n"
	    		+ "\r\n"
	    		+ "UNION ALL\r\n"
	    		+ "\r\n"
	    		+ "SELECT\r\n"
	    		+ "  bookingstatus AS description,\r\n"
	    		+ "  COUNT(*) AS value,\r\n"
	    		+ "  SUM(bookingprice) AS total_revenue\r\n"
	    		+ "FROM bookings\r\n"
	    		+ "GROUP BY bookingstatus;", nativeQuery = true)
	    List<Object[]> statisticsBooking();
	    
}
