package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Eventweb;

public interface EventDAO extends JpaRepository<Eventweb, Integer>{
	@Query(value = "SELECT * FROM eventweb\r\n"
			+ "WHERE MONTH(datestart) = MONTH(CURDATE()) AND YEAR(datestart) = YEAR(CURDATE());", nativeQuery = true)
	List<Object[]> fillEventInMonth();
	
	@Query(value="SELECT COUNT(*) FROM eventweb;", nativeQuery = true)
	List<Object> CountEvent();
}
