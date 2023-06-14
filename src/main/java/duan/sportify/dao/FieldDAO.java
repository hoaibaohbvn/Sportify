package duan.sportify.dao;



import org.springframework.data.jpa.repository.JpaRepository;


import duan.sportify.entities.Field;



public interface FieldDAO extends JpaRepository<Field, Integer>{
//	@Query("SELECT bd.fieldID, COUNT(*) AS luot_dat FROM BookingDetails bd GROUP BY bd.fieldID ORDER BY luot_dat DESC")
//    List<Object[]> getTop4BookingCounts();
}
