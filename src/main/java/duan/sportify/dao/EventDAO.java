package duan.sportify.dao;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Eventweb;

@SuppressWarnings("unused")
public interface EventDAO extends JpaRepository<Eventweb, Integer>{
	@Query(value="SELECT COUNT(*) FROM eventweb;", nativeQuery = true)
	List<Object> CountEvent();
	@Query(value = "SELECT * FROM eventweb\r\n"
			+ "WHERE MONTH(datestart) = MONTH(CURDATE()) AND YEAR(datestart) = YEAR(CURDATE());", nativeQuery = true)
	List<Object[]> fillEventInMonth();
	@Query("SELECT s FROM Eventweb s WHERE MONTH(s.datestart) = :month")
    List<Eventweb> findByMonth(@Param("month") int month);
	
	// Sort all theo ngày bắt dầu nhỏ hơn ngày hiện tại
	@Query("SELECT e FROM Eventweb e WHERE e.datestart <= CURRENT_DATE ORDER BY e.datestart DESC")
	List<Eventweb> findLatestEvents();
	
	// Sort All theo ngày cao đến thấp
	@Query("SELECT e FROM Eventweb e ORDER BY e.datestart DESC")
	Page<Eventweb> findAllOrderByDateStart(Pageable pageable);
	
	// Hiển thị chi tiết Event
	@Query("SELECT e FROM Eventweb e WHERE e.eventid = ?1")
    Eventweb findEventById(Integer eventId);
	
	// Hiển thị các sự kiện diễn ra trong tháng
	@Query("SELECT ee FROM Eventweb ee WHERE MONTH(ee.datestart) = ?1")
    List<Eventweb> findEventsInMonth(int month);
	 
	// Tìm kiếm sự kiện theo tên chứa (tìm kiếm không phân biệt chữ hoa/thường)
	    @Query("SELECT e FROM Eventweb e WHERE LOWER(e.nameevent) LIKE %:eventName%")
	    List<Eventweb> findByNameContainingIgnoreCase(String eventName);

	    // Tìm kiếm sự kiện theo ngày diễn ra
	    @Query("SELECT e FROM Eventweb e WHERE e.datestart = :eventDate")
	    List<Eventweb> findByDate(Date eventDate);
	    
//	    @Query("SELECT e FROM Eventweb e WHERE LOWER(e.nameevent) LIKE %:keyword% OR e.datestart = :eventDate")
//	    List<Eventweb> searchEvents(@Param("keyword") String keyword, @Param("eventDate") Date eventDate);
	    
	    @Query(value = "SELECT * FROM Eventweb " +
                "WHERE lower(nameevent) LIKE %:keyword% or datestart LIKE %:keyword%", nativeQuery = true)
	    Page<Eventweb> searchEvents(@Param("keyword") String keyword, Pageable pageable);
	// search event in admin
	    @Query(value = "select * FROM eventweb\r\n"
	    		+ "WHERE (nameevent LIKE %:nameevent% OR :nameevent IS NULL) "
	    		+ "AND (eventtype like %:eventtype% OR :eventtype IS NULL)", nativeQuery = true)
	    List<Eventweb> searchEventAdmin(@Param("nameevent") Optional<String> nameevent, @Param("eventtype") Optional<String> eventtype);
}
