package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Teamdetails;

public interface TeamDetailDAO extends JpaRepository<Teamdetails, Integer>{

}
