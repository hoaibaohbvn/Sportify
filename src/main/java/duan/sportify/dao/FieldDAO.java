package duan.sportify.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Field;



public interface FieldDAO extends JpaRepository<Field, Integer>{

	@Query(value="SELECT COUNT(*) FROM field;", nativeQuery = true)
	List<Object> CountField();

	@Query("SELECT f FROM Field f WHERE f.sporttype.sporttypeid = ?1")
	List<Field> findBySporttypeId(String cid);
	

}
