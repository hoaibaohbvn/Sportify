package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Bookingdetails;

public interface BookingDetailDAO extends JpaRepository<Bookingdetails, Integer>{

}
