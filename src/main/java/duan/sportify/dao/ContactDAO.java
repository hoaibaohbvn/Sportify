package duan.sportify.dao;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Contacts;


public interface ContactDAO extends JpaRepository<Contacts, Integer>{
	// search admin 
	@Query(value = "select * from contact where  datecontact = :datecontact and category like %:category% ", nativeQuery = true)
    List<Contacts> findByDatecontact(@Param("datecontact") Date datecontact, @Param("category") Optional<String> category);
	// dashboard
	// lấy danh sách 3 contact trong ngày
	@Query(value = "SELECT * FROM contact where date(datecontact) = curdate() limit 2", nativeQuery = true)
	List<Contacts> fill3ContactOnDate();
	// dếm liên hệ trong ngày
	@Query(value = "SELECT COUNT(*) AS total_contacts FROM contact where date(datecontact) = curdate() ", nativeQuery = true)
	int demLienHeTrongNgay();
	// lấy danh sách username đã gửi liên hệ trong ngày
	@Query(value = "SELECT username\r\n"
			+ "FROM contact\r\n"
			+ "WHERE DATE(datecontact) = CURDATE();", nativeQuery = true)
	List<String> contactedInDay();
	
}
