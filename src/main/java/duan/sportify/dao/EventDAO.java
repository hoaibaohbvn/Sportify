package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Eventweb;

public interface EventDAO extends JpaRepository<Eventweb, Integer>{
	@Query(value="SELECT COUNT(*) FROM eventweb;", nativeQuery = true)
	List<Object> CountEvent();
	@Query(value = "SELECT * FROM eventweb\r\n"
			+ "WHERE MONTH(datestart) = MONTH(CURDATE()) AND YEAR(datestart) = YEAR(CURDATE());", nativeQuery = true)
	List<Object[]> fillEventInMonth();
	@Query("SELECT s FROM Eventweb s WHERE MONTH(s.datestart) = :month")
    List<Eventweb> findByMonth(@Param("month") int month);
	@Query("SELECT e FROM Eventweb e WHERE e.datestart <= CURRENT_DATE ORDER BY e.datestart DESC")
	List<Eventweb> findLatestEvents();
	// Sort All theo ngày cao đến thấp
	@Query("SELECT e FROM Eventweb e ORDER BY e.datestart DESC")
	List<Eventweb> findAllOrderByDateStart();
}
