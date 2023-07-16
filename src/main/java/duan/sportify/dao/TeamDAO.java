package duan.sportify.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamDAO extends JpaRepository<Teams, Integer> {

	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n" + "FROM teams\r\n"
			+ "LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "LEFT JOIN sporttype ON  teams.sporttypeid = sporttype.sporttypeid \r\n"
			+ "GROUP BY teams.teamid;", 
			nativeQuery = true)
	Page<Object[]> findAllTeam(Pageable pageable);

	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
			+ "			FROM teams\r\n" + "			LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "			LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+ "            where nameteam like %:searchText%\r\n"
			+ "			GROUP BY teams.teamid;", 
			nativeQuery = true)
	Page<Object[]> SearchTeam(@Param("searchText") String searchText,Pageable pageable);

	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
			+ "			FROM teams\r\n" + "			LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "			LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+ "            where sporttype.sporttypeid like :sporttypeid\r\n"
			+ "			GROUP BY teams.teamid;",
			nativeQuery = true)
	Page<Object[]> FilterTeam(String sporttypeid ,Pageable pageable);
}
