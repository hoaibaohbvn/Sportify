package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Eventweb;

public interface EventDAO extends JpaRepository<Eventweb, Integer>{
	/**
	 * truy vấn tìm các sự kiện theo tháng hiện tại
	 * @return 1 list đối tượng object gốm các sự kiện có trong thang hiện tại
	 */
	@Query(value = "SELECT * FROM eventweb\r\n"
			+ "WHERE MONTH(datestart) = MONTH(CURDATE()) AND YEAR(datestart) = YEAR(CURDATE());", nativeQuery = true)
	List<Object[]> fillEventInMonth();
	/**
	 * truy vấn đếm sự kiện trong web
	 * @return 1 list đối tượng object gốm số lượng event
	 */
	@Query(value="SELECT COUNT(*) FROM eventweb;", nativeQuery = true)
	List<Object> CountEvent();
}
