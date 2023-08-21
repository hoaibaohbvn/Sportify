package duan.sportify.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Bookings;
import duan.sportify.entities.Contacts;

public interface BookingDAO extends JpaRepository<Bookings, Integer> {

	@Query(value = "SELECT \r\n" + "    b.bookingid,\r\n" + "    b.bookingdate,\r\n" + "    b.bookingprice,\r\n"
			+ "    b.note,\r\n" + "    b.bookingstatus,\r\n" + "    f.namefield,\r\n" + "    f.image\r\n" + "FROM \r\n"
			+ "    bookings AS b\r\n" + "JOIN \r\n" + "    bookingdetails AS bd ON b.bookingid = bd.bookingid\r\n"
			+ "JOIN \r\n" + "    field AS f ON bd.fieldid = f.fieldid\r\n" + "WHERE \r\n"
			+ "    b.username = :username ORDER BY bookingdate DESC LIMIT 20", nativeQuery = true)
	List<Object[]> getBookingInfoByUsername(String username);

	@Query(value = "SELECT \r\n" + "    b.bookingid,\r\n" + "    b.bookingdate,\r\n" + "    b.bookingstatus,\r\n"
			+ "    bd.shiftid,\r\n" + "    bd.playdate,\r\n" + "    bd.price,\r\n" + "    f.namefield,\r\n"
			+ "    f.image,\r\n" + "    s.nameshift\r\n" + "FROM \r\n" + "    bookings AS b\r\n" + "JOIN \r\n"
			+ "    bookingdetails AS bd ON b.bookingid = bd.bookingid\r\n" + "JOIN \r\n"
			+ "    field AS f ON bd.fieldid = f.fieldid\r\n" + "JOIN \r\n"
			+ "    shifts AS s ON bd.shiftid = s.shiftid\r\n" + "WHERE \r\n"
			+ "    b.bookingid = :bookingid", nativeQuery = true)
	Object[] getBookingInfoByBookingDetail(String bookingid);

	@Query(value = "select count(*) from bookings", nativeQuery = true)
	int countBooking();
	// admin
	@Query(value = "SELECT b.* FROM bookings b \r\n"
			+ "	        JOIN users u ON b.username = u.username where b.bookingstatus like '%Đã Cọc%' and date(b.bookingdate) = curdate()\r\n"
		, nativeQuery = true)
	List<Bookings> findAllBookingAndUser();
	// search admin
	@Query(value = "SELECT b.* FROM bookings b "
	        + "JOIN users u ON b.username = u.username "
	        + "WHERE (CONCAT(u.firstname, ' ', u.lastname) LIKE %:keyword%) "
	        + "AND b.bookingdate LIKE %:datebook% "
	        + "AND b.bookingstatus LIKE %:status%", nativeQuery = true)
	List<Bookings> findByConditions(@Param("keyword") String keyword, 
	                                @Param("datebook") Date datebook,
	                                @Param("status") String status);
	// dashboard
	// tổng phiểu booking and order
	@Query(value = "SELECT COUNT(*) AS total_count\r\n" + "FROM (\r\n"
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
			+ "  \r\n" + "FROM\r\n" + "  bookings\r\n" + "WHERE\r\n" + "  YEAR(bookingdate) >= YEAR(CURDATE()) - 6\r\n"
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
			+ "FROM bookings\r\n" + "WHERE bookingdate >= DATE_SUB(CURDATE(), INTERVAL 6 YEAR);", nativeQuery = true)
	List<Object[]> countBookingOn6YearReturn();

	// Đếm số lượng hóa đơn trong ngày
	@Query(value = "  SELECT COUNT(*) AS total_bookings\r\n" + "FROM bookings\r\n"
			+ "WHERE DATE(bookingdate) = CURDATE();", nativeQuery = true)
	int countBookingInDate();

	// thong kê booking trong ngày
	@Query(value = "SELECT\r\n" + "    'Tổng số booking' AS description,\r\n" + "    COUNT(*) AS value,\r\n"
			+ "    IFNULL(SUM(bookingprice), 0) AS total_revenue\r\n" + "FROM bookings\r\n"
			+ "WHERE date(bookingdate) = curdate()\r\n" + "\r\n" + "UNION ALL\r\n" + "\r\n" + "SELECT\r\n"
			+ "    'Hoàn Thành' AS description,\r\n" + "    COUNT(*) AS value,\r\n"
			+ "    IFNULL(SUM(bookingprice), 0) AS total_revenue\r\n" + "FROM bookings\r\n"
			+ "WHERE bookingstatus = 'Hoàn Thành' AND date(bookingdate) = curdate()\r\n" + "\r\n" + "UNION ALL\r\n"
			+ "\r\n" + "SELECT\r\n" + "    'Đã Cọc' AS description,\r\n" + "    COUNT(*) AS value,\r\n"
			+ "    IFNULL(SUM(bookingprice), 0) AS total_revenue\r\n" + "FROM bookings\r\n"
			+ "WHERE bookingstatus = 'Đã Cọc' AND date(bookingdate) = curdate()\r\n" + "\r\n" + "UNION ALL\r\n" + "\r\n"
			+ "SELECT\r\n" + "    'Hủy Đặt' AS description,\r\n" + "    COUNT(*) AS value,\r\n"
			+ "    IFNULL(SUM(bookingprice), 0) AS total_revenue\r\n" + "FROM bookings\r\n"
			+ "WHERE bookingstatus = 'Hủy Đặt' AND date(bookingdate) = curdate();", nativeQuery = true)
	List<Object[]> thongkebookingtrongngay();

	// tổng số phiểu dat san tháng này và tháng trước
	@Query(value = "SELECT 'This Month' AS period, COUNT(*) AS total_bookings\r\n" + "FROM bookings\r\n"
			+ "WHERE YEAR(bookingdate) = YEAR(CURDATE()) AND MONTH(bookingdate) = MONTH(CURDATE())\r\n"
			+ "UNION ALL\r\n" + "SELECT 'Last Month', COUNT(*)\r\n" + "FROM bookings\r\n"
			+ "WHERE YEAR(bookingdate) = YEAR(CURDATE() - INTERVAL 1 MONTH) AND MONTH(bookingdate) = MONTH(CURDATE() - INTERVAL 1 MONTH);", nativeQuery = true)
	List<Object[]> tongSoPhieuDatSan2Thang();
	// tổng doanh thu booking thang nay va thang trước
	@Query(value = "SELECT\r\n"
			+ "    COALESCE(SUM(CASE \r\n"
			+ "        WHEN bookingstatus = 'Hoàn Thành' AND \r\n"
			+ "             YEAR(bookingdate) = YEAR(CURRENT_DATE) AND \r\n"
			+ "             MONTH(bookingdate) = MONTH(CURRENT_DATE) THEN bookingprice \r\n"
			+ "        WHEN bookingstatus = 'Đã Cọc' AND \r\n"
			+ "             YEAR(bookingdate) = YEAR(CURRENT_DATE) AND \r\n"
			+ "             MONTH(bookingdate) = MONTH(CURRENT_DATE) THEN (bookingprice * 0.3 )\r\n"
			+ "         WHEN bookingstatus = 'Hủy Đặt' AND \r\n"
			+ "             YEAR(bookingdate) = YEAR(CURRENT_DATE) AND \r\n"
			+ "             MONTH(bookingdate) = MONTH(CURRENT_DATE) THEN (- (bookingprice * 0.3 * 2) )\r\n"
			+ "    END), 0) AS doanh_thu_thang_hien_tai,\r\n"
			+ "    COALESCE(SUM(CASE \r\n"
			+ "        WHEN bookingstatus = 'Hoàn Thành' AND \r\n"
			+ "             YEAR(bookingdate) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) AND \r\n"
			+ "             MONTH(bookingdate) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) THEN bookingprice \r\n"
			+ "        WHEN bookingstatus = 'Đã Cọc' AND \r\n"
			+ "             YEAR(bookingdate) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) AND \r\n"
			+ "             MONTH(bookingdate) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) THEN (bookingprice * 0.3 )\r\n"
			+ "        WHEN bookingstatus = 'Hủy Đặt' AND \r\n"
			+ "             YEAR(bookingdate) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) AND \r\n"
			+ "             MONTH(bookingdate) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) THEN (- (bookingprice * 0.3 * 2) )\r\n"
			+ "    END), 0) AS doanh_thu_thang_truoc\r\n"
			+ "FROM bookings;", nativeQuery = true)
	List<Object[]> tongDoanhThuBooking2Month();
	// rp
	// rp doanh thu dặt sân trong tháng
	@Query(value = "SELECT\r\n"
			+ "  CONCAT(DAY(bookingdate), '-', MONTH(bookingdate)) AS booking_date_month,\r\n"
			+ "  SUM(\r\n"
			+ "    CASE\r\n"
			+ "      WHEN bookingstatus = 'Hoàn Thành' THEN bookingprice\r\n"
			+ "      WHEN bookingstatus = 'Đã Cọc' THEN bookingprice * 0.3\r\n"
			+ "      WHEN bookingstatus = 'Hủy Đặt' THEN - bookingprice * 0.3 * 2\r\n"
			+ "      ELSE 0\r\n"
			+ "    END\r\n"
			+ "  ) AS doanhThuThucTe,\r\n"
			+ "  SUM(CASE WHEN bookingstatus = 'Hủy Đặt' THEN  bookingprice * 0.3 * 2 ELSE 0 END) AS huy,\r\n"
			+ "  SUM(CASE WHEN bookingstatus = 'Đã Cọc' THEN  bookingprice * 0.3 ELSE 0 END) AS coc,\r\n"
			+ "  SUM(CASE WHEN bookingstatus = 'Hoàn Thành' THEN  bookingprice  ELSE 0 END) AS hoanthanh,\r\n"
			+ "  SUM(bookingprice) AS DoanhThuUocTinh\r\n"
			+ "FROM bookings\r\n"
			+ "WHERE\r\n"
			+ "  YEAR(bookingdate) = :year AND MONTH(bookingdate) = :month \r\n"
			+ "GROUP BY booking_date_month\r\n"
			+ "ORDER BY booking_date_month;", nativeQuery = true)
    List<Object[]> rpDoanhThuBookingTrongThang(@Param("year") String year, @Param("month") String month);
	// lấy năm của các phiếu dặt
	@Query(value = "SELECT DISTINCT YEAR(bookingdate) AS booking_year\r\n"
			+ "FROM bookings;", nativeQuery = true)
	List<Object[]> getYearBooking();
	// rp daonh thu dặt sân trong năm
	@Query(value = "SELECT\r\n"
			+ " concat('Tháng ',month(bookingdate)) AS booking_date_month,\r\n"
			+ "  SUM(\r\n"
			+ "    CASE\r\n"
			+ "      WHEN bookingstatus = 'Hoàn Thành' THEN bookingprice\r\n"
			+ "      WHEN bookingstatus = 'Đã Cọc' THEN bookingprice * 0.3\r\n"
			+ "      WHEN bookingstatus = 'Hủy Đặt' THEN - bookingprice * 0.3 * 2\r\n"
			+ "      ELSE 0\r\n"
			+ "    END\r\n"
			+ "  ) AS doanhThuThucTe,\r\n"
			+ "  SUM(CASE WHEN bookingstatus = 'Hủy Đặt' THEN  bookingprice * 0.3 * 2 ELSE 0 END) AS huy,\r\n"
			+ "  SUM(CASE WHEN bookingstatus = 'Đã Cọc' THEN  bookingprice * 0.3 ELSE 0 END) AS coc,\r\n"
			+ "  SUM(CASE WHEN bookingstatus = 'Hoàn Thành' THEN  bookingprice  ELSE 0 END) AS hoanthanh,\r\n"
			+ "  SUM(bookingprice) AS DoanhThuUocTinh\r\n"
			+ "FROM bookings\r\n"
			+ "WHERE\r\n"
			+ "  YEAR(bookingdate) = :year \r\n"
			+ "GROUP BY booking_date_month\r\n"
			+ "ORDER BY booking_date_month;", nativeQuery = true)
	List<Object[]> rpDoanhThuBookingTrongNam(@Param("year") String year);
	// rp so luong phieu dat san trong thang
	@Query(value = "SELECT\r\n"
			+ "  CONCAT('Ngày ', DAY(bookingdate), '-', MONTH(bookingdate)) AS booking_date_month,\r\n"
			+ "  COUNT(bookingid) AS tongphieu,\r\n"
			+ "  SUM(CASE WHEN bookingstatus LIKE 'Hủy Đặt' THEN 1 ELSE 0 END) AS huy,\r\n"
			+ "  SUM(CASE WHEN bookingstatus LIKE 'Đã Cọc' THEN 1 ELSE 0 END) AS coc,\r\n"
			+ "  SUM(CASE WHEN bookingstatus LIKE 'Hoàn Thành' THEN 1 ELSE 0 END) AS hoanthanh\r\n"
			+ "FROM bookings\r\n"
			+ "WHERE\r\n"
			+ "  YEAR(bookingdate) = :year AND MONTH(bookingdate) = :month \r\n"
			+ "GROUP BY booking_date_month\r\n"
			+ "ORDER BY booking_date_month;", nativeQuery = true)
	 List<Object[]> rpSoLuongBookingTrongThang(@Param("year") String year, @Param("month") String month);
	 // rp so luong phieu dat san trong nam
	 @Query(value = "SELECT\r\n"
	 		+ "  CONCAT('Tháng ', MONTH(bookingdate)) AS booking_date_month,\r\n"
	 		+ "  COUNT(bookingid) AS tongphieu,\r\n"
	 		+ "  SUM(CASE WHEN bookingstatus LIKE 'Hủy Đặt' THEN 1 ELSE 0 END) AS huy,\r\n"
	 		+ "  SUM(CASE WHEN bookingstatus LIKE 'Đã Cọc' THEN 1 ELSE 0 END) AS coc,\r\n"
	 		+ "  SUM(CASE WHEN bookingstatus LIKE 'Hoàn Thành' THEN 1 ELSE 0 END) AS hoanthanh\r\n"
	 		+ "FROM bookings\r\n"
	 		+ "WHERE\r\n"
	 		+ "  YEAR(bookingdate) = :year \r\n"
	 		+ "GROUP BY booking_date_month\r\n"
	 		+ "ORDER BY booking_date_month;", nativeQuery = true)
	 List<Object[]> rpSoLuongBookingTrongNam(@Param("year") String year);
}
