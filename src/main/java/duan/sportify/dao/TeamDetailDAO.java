package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import duan.sportify.entities.Teamdetails;

public interface TeamDetailDAO extends JpaRepository<Teamdetails, Integer>{
	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
			+ "FROM teams\r\n"
			+ "LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid \r\n"
			+"where teams.teamid like :teamId \r\n"
			+ "GROUP BY teams.teamid;",nativeQuery = true)
	List<Object[]> findByIdTeam(String teamId);
	
//	@Query(value = "SELECT * FROM teamdetails \r\n"
//			+ "where teamdetails.teamid like :teamId",nativeQuery = true)
//	List<Object[]> findUserByIdTeam(String teamId);
	
	@Query(value = "SELECT users.*\r\n"
			+ "FROM users\r\n"
			+ "INNER JOIN teamdetails ON users.username = teamdetails.username\r\n"
			+ "INNER JOIN teams ON teamdetails.teamid = teams.teamid\r\n"
			+ "WHERE teams.teamid like :teamId",nativeQuery = true)
	List<Object[]> findUserByIdTeam(String teamId);

}
