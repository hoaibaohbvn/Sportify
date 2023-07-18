package duan.sportify.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Users;

public interface UserDAO extends JpaRepository<Users, String>{
	@Query(value="SELECT COUNT(*) FROM users;", nativeQuery = true)
	List<Object> CountUser();
	
	@Query(value = "SELECT * FROM sportify.users WHERE username like :usernameLogin  AND passwords like :passwordLogin ", nativeQuery = true)
	Users findAcc(@Param("usernameLogin") String usernameLogin, @Param("passwordLogin") String passwordLogin);
}
