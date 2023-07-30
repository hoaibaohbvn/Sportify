package duan.sportify.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.entities.Field;
import duan.sportify.entities.Sporttype;

@SuppressWarnings("unused")
public interface SportTypeDAO extends JpaRepository<Sporttype, String>{
	@Query(value="select * from sporttype where sporttype.sporttypeid LIKE ?1",nativeQuery = true)
	List<Sporttype> findSporttypeById(String id);
	
	@Query(value="SELECT * FROM sporttype where categoryname like '%?1%'",nativeQuery = true)
	List<Sporttype> findSporttypeByNameAdmin(@RequestParam("name") String name);
	// search Sporttype in admin
	@Query(value = "select * FROM sporttype\r\n"
			+ "WHERE (categoryname LIKE %:categoryname% OR :categoryname IS NULL)", nativeQuery = true)
	List<Sporttype> searchSport(@Param("categoryname") Optional<String> categoryname);
}
