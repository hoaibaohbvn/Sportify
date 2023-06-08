package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.BookingDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Bookings;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService{
	@Autowired
	BookingDAO bookingDAO;

	@Override
	public List<Bookings> findAll() {
		// TODO Auto-generated method stub
		return bookingDAO.findAll();
	}

	@Override
	public Bookings create(Bookings bookings) {
		// TODO Auto-generated method stub
		return bookingDAO.save(bookings);
	}

	@Override
	public Bookings update(Bookings bookings) {
		// TODO Auto-generated method stub
		return bookingDAO.save(bookings);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		bookingDAO.deleteById(id);
	}

	@Override
	public Bookings findById(Integer id) {
		// TODO Auto-generated method stub
		return bookingDAO.findById(id).get();
	}
}
