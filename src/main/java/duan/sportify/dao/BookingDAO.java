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
}
