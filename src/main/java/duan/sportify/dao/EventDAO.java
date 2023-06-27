package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Eventweb;

public interface EventDAO extends JpaRepository<Eventweb, Integer>{
	@Query(value = "SELECT * FROM eventweb\r\n"
			+ "WHERE MONTH(datestart) = MONTH(CURDATE()) AND YEAR(datestart) = YEAR(CURDATE());", nativeQuery = true)
	List<Object[]> fillEventInMonth();
	@Query("SELECT s FROM Eventweb s WHERE MONTH(s.datestart) = :month")
    List<Eventweb> findByMonth(@Param("month") int month);
}
