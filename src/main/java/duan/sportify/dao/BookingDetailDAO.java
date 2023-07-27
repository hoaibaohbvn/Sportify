package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Field;


@SuppressWarnings("unused")
public interface BookingDetailDAO extends JpaRepository<Bookingdetails, Integer>{
	@Query(value = "SELECT  s.* FROM bookingdetails bd\r\n"
			+ "JOIN field s ON bd.fieldid = s.fieldid\r\n"
			+ "GROUP BY bd.fieldid\r\n"
			+ "ORDER BY COUNT(*) DESC\r\n"
			, nativeQuery = true)
    List<Object[]> findTopFieldsWithMostBookings();
}
