package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Shifts;

public interface ShiftDAO extends JpaRepository<Shifts, Integer>{

}
