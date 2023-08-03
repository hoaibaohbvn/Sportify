package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import duan.sportify.entities.Teamdetails;


public interface TeamDetailDAO extends JpaRepository<Teamdetails, Integer>{
	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
			+ "FROM teams\r\n"
			+ "LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid \r\n"
			+"where teams.teamid like :teamId \r\n"
			+ "GROUP BY teams.teamid;",nativeQuery = true)
	List<Object[]> findByIdTeam(Integer teamId);
	
//	@Query(value = "SELECT * FROM teamdetails \r\n"
//			+ "where teamdetails.teamid like :teamId",nativeQuery = true)
//	List<Object[]> findUserByIdTeam(String teamId);
	
	@Query(value = "SELECT users.*,teamdetails.teamdetailid,teamdetails.teamid,teamdetails.joindate,teamdetails.infouser\r\n"
			+ "FROM users\r\n"
			+ "INNER JOIN teamdetails ON users.username = teamdetails.username\r\n"
			+ "INNER JOIN teams ON teamdetails.teamid = teams.teamid\r\n"
			+ "WHERE teams.teamid like :teamId",nativeQuery = true)
	List<Object[]> findUserByIdTeam(Integer teamId);
	
	@Query(value = "SELECT * FROM sportify.teamdetails\r\n"
			+ "where teamid like :teamId And username like :username",nativeQuery = true)
	Teamdetails checkTeamUser(String username, Integer teamId);
	
	@Query(value = "SELECT COUNT(*) FROM teamdetails WHERE teamid like :teamId", nativeQuery = true)
	Integer countUser(Integer teamId);
	// team detail in admin
		@Query(value = "SELECT * FROM teamdetails WHERE teamid = :teamid", nativeQuery = true)
	    List<Teamdetails> findByTeamIdAdmin(@Param("teamid") Integer teamid);
	@Modifying
	@Query(value ="DELETE FROM teamdetails WHERE username like :username AND teamid like :teamId", nativeQuery = true)
	@Transactional
	int deleteByUsernameAndTeamId(String username, Integer teamId);
	
	

}
