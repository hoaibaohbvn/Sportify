package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Shifts;

public interface ShiftDAO extends JpaRepository<Shifts, Integer>{
		@Query(value="select * from shifts where shifts.shiftid LIKE ?1",nativeQuery = true)
		List<Shifts> findShiftById(Integer id);
		
		@Query(value="select * from shifts where nameshift LIKE :name",nativeQuery = true)
		List<Shifts> findShiftByName(String name);
		@Query(value="SELECT * FROM shifts s WHERE s.shiftid NOT IN ( SELECT b.shiftid FROM bookingdetails b WHERE b.fieldid = :id AND b.playdate = :date )",nativeQuery = true)
		List<Shifts> findShiftDate(Integer id,String date);
}
