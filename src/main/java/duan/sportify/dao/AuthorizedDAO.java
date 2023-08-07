package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Authorized;

public interface AuthorizedDAO extends JpaRepository<Authorized, Integer>{
	@Query(value = "SELECT * FROM sportify.authorized\r\n"
			+ "where username like :username", 
			nativeQuery = true)
	Authorized findAllAuthorized(String username);
}