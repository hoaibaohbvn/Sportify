package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Bookings;



public interface BookingDetailService {
	List<Bookingdetails> findAll();

	Bookingdetails create(Bookingdetails bookingdetails);

	Bookingdetails update(Bookingdetails bookingdetails);

	void delete(Integer id);
	
	Bookingdetails findById(Integer id);
}
