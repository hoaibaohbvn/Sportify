package duan.sportify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Teams;

public interface TeamDAO extends JpaRepository<Teams, Integer>{
	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
			+ "FROM teams\r\n"
			+ "LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid \r\n"
			+ "GROUP BY teams.teamid;",nativeQuery = true)
		List<Object[]> findAllTeam();
		
		
		@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
				+ "			FROM teams\r\n"
				+ "			LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
				+ "			LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
				+ "            where nameteam like %:searchText%\r\n"
				+ "			GROUP BY teams.teamid;",nativeQuery = true)
		List<Object[]> SearchTeam(@Param("searchText") String searchText);
		
		@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
				+ "			FROM teams\r\n"
				+ "			LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
				+ "			LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
				+ "            where sporttype.sporttypeid like :cid\r\n"
				+ "			GROUP BY teams.teamid;",nativeQuery = true)
		List<Object[]> FilterTeam(String cid);
}
