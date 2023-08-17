package duan.sportify.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Products;
import duan.sportify.entities.Users;

public interface UserDAO extends JpaRepository<Users, String>{
	@Query(value="SELECT COUNT(*) FROM users;", nativeQuery = true)
	List<Object> CountUser();
	
	@Query(value = "SELECT * FROM sportify.users WHERE username like :usernameLogin  AND passwords like :passwordLogin ", nativeQuery = true)
	Users findAcc(@Param("usernameLogin") String usernameLogin, @Param("passwordLogin") String passwordLogin);
	// search admin
	@Query(value = "SELECT DISTINCT  users.*\r\n"
			+ "FROM users\r\n"
			+ "JOIN authorized ON users.username = authorized.username\r\n"
			+ "JOIN roles ON authorized.roleid = roles.roleid\r\n"
			+ "WHERE users.username like %:user%\r\n"
			+ "AND users.firstname like %:ho%\r\n"
			+ "AND users.lastname like %:ten%\r\n"
			+ "AND (:status IS NULL OR users.status = :status)\r\n"
			+ "AND roles.rolename like %:role%;", nativeQuery = true)
	List<Users> searchUserAdmin(@Param("user") String user,
									@Param("ho") String ho,
									@Param("ten") String ten,
									@Param("status") Optional<Integer> status,
									@Param("role") String role);

	
}
