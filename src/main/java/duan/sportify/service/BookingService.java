package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Bookings;


@SuppressWarnings("unused")
public interface BookingService {
	List<Bookings> findAll();

	Bookings create(Bookings bookings);

	Bookings update(Bookings bookings);

	void delete(Integer id);
	
	Bookings findById(Integer id);
}
