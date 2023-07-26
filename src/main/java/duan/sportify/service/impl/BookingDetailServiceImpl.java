package duan.sportify.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.BookingDetailDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.BookingDetailService;

@SuppressWarnings("unused")
@Service
public class BookingDetailServiceImpl implements BookingDetailService{
	@Autowired
	BookingDetailDAO bookingDetailDAO;

	@Override
	public List<Bookingdetails> findAll() {
		// TODO Auto-generated method stub
		return bookingDetailDAO.findAll();
	}

	@Override
	public Bookingdetails create(Bookingdetails bookingdetails) {
		// TODO Auto-generated method stub
		return bookingDetailDAO.save(bookingdetails);
	}

	@Override
	public Bookingdetails update(Bookingdetails bookingdetails) {
		// TODO Auto-generated method stub
		return bookingDetailDAO.save(bookingdetails);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		bookingDetailDAO.deleteById(id);
	}

	@Override
	public Bookingdetails findById(Integer id) {
		// TODO Auto-generated method stub
		return bookingDetailDAO.findById(id).get();
	}
	
}
