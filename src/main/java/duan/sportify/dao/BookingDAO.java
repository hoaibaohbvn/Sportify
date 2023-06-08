package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Bookings;

public interface BookingDAO extends JpaRepository<Bookings, Integer>{

}
