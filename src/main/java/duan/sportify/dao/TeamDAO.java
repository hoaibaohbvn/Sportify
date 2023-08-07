package duan.sportify.dao;
import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Teamdetails;
import duan.sportify.entities.Teams;


@SuppressWarnings("unused")
public interface TeamDAO extends JpaRepository<Teams, Integer> {

	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n" + "FROM teams\r\n"
			+ "LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "LEFT JOIN sporttype ON  teams.sporttypeid = sporttype.sporttypeid \r\n"
			+ "GROUP BY teams.teamid;", 
			nativeQuery = true)
	List<Object[]> findAllTeam();
	

	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
			+ "			FROM teams\r\n" + "			LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "			LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+ "            where nameteam like %:searchText%\r\n"
			+ "			GROUP BY teams.teamid;", 
			nativeQuery = true)
	List<Object[]> SearchTeam(@Param("searchText") String searchText);

	@Query(value = "SELECT teams.*, categoryname, COUNT(teamdetails.teamid) AS member_count \r\n"
			+ "			FROM teams\r\n" + "			LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "			LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+ "            where sporttype.sporttypeid like :sporttypeid\r\n"
			+ "			GROUP BY teams.teamid;",
			nativeQuery = true)
	List<Object[]> FilterTeam(String sporttypeid );
	
	//search in admin 
	@Query(value = "SELECT * FROM teams WHERE (:nameteam IS NULL OR nameteam LIKE %:nameteam%) AND (:sporttypeid IS NULL OR sporttypeid LIKE %:sporttypeid%)", nativeQuery = true)
	List<Teams> searchTeamAdmin(@Param("nameteam") Optional<String> nameteam, @Param("sporttypeid") Optional<String> sporttypeid);
	
	//team detail in admin
	
}
