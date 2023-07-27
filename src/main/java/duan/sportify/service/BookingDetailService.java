package duan.sportify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import duan.sportify.dao.BookingDAO;
import duan.sportify.dao.BookingDetailDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Bookings;


@SuppressWarnings("unused")
public interface BookingDetailService {
	
	List<Bookingdetails> findAll();

	Bookingdetails create(Bookingdetails bookingdetails);

	Bookingdetails update(Bookingdetails bookingdetails);

	void delete(Integer id);
	
	Bookingdetails findById(Integer id);
	
	
}
