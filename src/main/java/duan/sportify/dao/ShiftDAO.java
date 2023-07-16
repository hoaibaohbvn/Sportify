package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Shifts;

public interface ShiftDAO extends JpaRepository<Shifts, Integer>{
		@Query(value="select * from shifts where shifts.shiftid LIKE ?1",nativeQuery = true)
		List<Shifts> findShiftById(Integer id);
}
