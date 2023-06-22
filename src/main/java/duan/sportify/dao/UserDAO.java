package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Users;

public interface UserDAO extends JpaRepository<Users, String>{
	@Query(value="SELECT COUNT(*) FROM users;", nativeQuery = true)
	List<Object> CountUser();
}
