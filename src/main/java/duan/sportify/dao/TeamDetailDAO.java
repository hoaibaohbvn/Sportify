package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Teamdetails;

public interface TeamDetailDAO extends JpaRepository<Teamdetails, Integer>{

}
