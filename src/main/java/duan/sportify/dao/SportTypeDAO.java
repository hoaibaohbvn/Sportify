package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Field;
import duan.sportify.entities.Sporttype;

public interface SportTypeDAO extends JpaRepository<Sporttype, String>{
	@Query(value="select * from sporttype where sporttype.sporttypeid LIKE ?1",nativeQuery = true)
	List<Sporttype> findSporttypeById(String id);
	
	
}
