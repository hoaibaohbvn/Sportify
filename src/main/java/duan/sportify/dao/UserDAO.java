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
	
	@Query(value = "SELECT * FROM sportify.users WHERE username like :usernameSignup", nativeQuery = true)
	Users findAcc(String usernameSignup);
	// search admin
	@Query(value = "SELECT DISTINCT  users.*\r\n"
			+ "FROM users\r\n"
			+ "JOIN authorized ON users.username = authorized.username\r\n"
			+ "JOIN roles ON authorized.roleid = roles.roleid\r\n"
			+ "WHERE users.username like %:user%\r\n"
			+ "AND (CONCAT(users.firstname, ' ', users.lastname) LIKE %:keyword%)\r\n"
			+ "AND (:status IS NULL OR users.status = :status)\r\n"
			+ "AND roles.rolename like %:role%;", nativeQuery = true)
	List<Users> searchUserAdmin(@Param("user") String user,
									@Param("keyword") String keyword,
									@Param("status") Optional<Integer> status,
									@Param("role") String role);
	
	@Query(value = "SELECT firstname, lastname, phone, email, address FROM Users WHERE username = :username", nativeQuery = true)
	Object findByUsername(@Param("username") String username);

	
}
