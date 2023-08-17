package duan.sportify.dao;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Field;
import duan.sportify.entities.Sporttype;


@SuppressWarnings("unused")
public interface FieldDAO extends JpaRepository<Field, Integer>{
	
	@Query(value="SELECT COUNT(*) FROM field;", nativeQuery = true)
	List<Object> CountField();

	@Query(value = "SELECT * FROM field f WHERE f.sporttypeid = ?1 AND f.status = 1", nativeQuery = true)
	List<Field> findBySporttypeId(String cid);

	
	@Query(value="SELECT f.*\r\n"
			+ "FROM field f inner join sporttype st on f.sporttypeid = st.sporttypeid\r\n"
			+ "LEFT JOIN bookingdetails bd ON f.fieldid = bd.fieldid AND bd.playdate LIKE :dateInput and bd.shiftid LIKE :shiftSelect and st.sporttypeid LIKE :categorySelect \r\n"
			+ "WHERE bd.fieldid IS NULL and st.sporttypeid LIKE :categorySelect And status = 1", nativeQuery = true)
	List<Field> findSearch(String dateInput, String categorySelect, Integer shiftSelect);
	
	@Query(value="select * from field where fieldid = :id", nativeQuery = true)
	List<Field> findFieldById(Integer id);
	
	@Query(value="SELECT s.categoryname FROM field f INNER JOIN sporttype s ON f.sporttypeid = s.sporttypeid WHERE f.fieldid = :id", nativeQuery = true)
	String findNameSporttypeById(Integer id);
	
	@Query(value="SELECT s.sporttypeid FROM field f INNER JOIN sporttype s ON f.sporttypeid = s.sporttypeid WHERE f.fieldid = :id", nativeQuery = true)
	String findIdSporttypeById(Integer id);
	
	@Query(value="select * from field where sporttypeid = :cid LIMIT 3", nativeQuery = true)
	List<Field> findBySporttypeIdlimit3(String cid);
	
	// admin 
	@Query(value = "select * from field where status = 1", nativeQuery = true)
	List<Field> findAllActive();
	// search team in admin
	@Query(value = "select * FROM field\r\n"
			+ "WHERE (namefield LIKE %:namefield% OR :namefield IS NULL)\r\n"
			+ "AND (sporttypeid like %:sporttypeid% OR :sporttypeid IS NULL)\r\n"
			+ "and (status = :status OR :status IS NULL);", nativeQuery = true)
	List<Field> searchFieldAdmin(@Param("namefield") Optional<String> namefield, @Param("sporttypeid") Optional<String> sporttypeid, @Param("status") Optional<Integer> status);
	// dashboard admin
	@Query(value = "  SELECT COUNT(*) AS total_field\r\n"
			+ "FROM field\r\n"
			+ "WHERE status = 1;", nativeQuery = true)
	int countFieldActiving();
}
