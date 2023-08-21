package duan.sportify.dao;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Teams;

@SuppressWarnings("unused")
public interface TeamDAO extends JpaRepository<Teams, Integer> {

	@Query(value = "SELECT teams.teamid,teams.sporttypeid,teams.nameteam,teams.quantity,teams.avatar,teams.contact,teams.descriptions,teams.username,teams.createdate, sporttype.categoryname, COUNT(CASE WHEN teamdetails.status = 1 THEN teamdetails.teamid ELSE NULL END) AS count,users.firstname,users.lastname\r\n"
			+ "			FROM teams LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "	LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+ "	LEFT JOIN users ON teams.username = users.username\r\n"
			+ "						GROUP BY teams.teamid;", nativeQuery = true)
	List<Object[]> findAllTeam();
	
	//tìm sân theo id

	@Query(value = "SELECT teams.teamid,teams.sporttypeid,teams.nameteam,teams.quantity,teams.avatar,teams.contact,teams.descriptions,teams.username,teams.createdate, sporttype.categoryname, COUNT(CASE WHEN teamdetails.status = 1 THEN teamdetails.teamid ELSE NULL END) AS count,users.firstname,users.lastname\r\n"
			+ "			FROM teams LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "	LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+"LEFT JOIN users ON teams.username = users.username\r\n"
			+ " WHERE teams.teamid IN (SELECT teamid FROM teamdetails WHERE username like :usernameLogin and status=1)\r\n"
			+ "						GROUP BY teams.teamid;", nativeQuery = true)
	List<Object[]> findTeamUsername(String usernameLogin);

	@Query(value = "SELECT teams.teamid,teams.sporttypeid,teams.nameteam,teams.quantity,teams.avatar,teams.contact,teams.descriptions,teams.username,teams.createdate, sporttype.categoryname, COUNT(CASE WHEN teamdetails.status = 1 THEN teamdetails.teamid ELSE NULL END) AS count,users.firstname,users.lastname\r\n"
			+ "			FROM teams LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "	LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+ "	LEFT JOIN users ON teams.username = users.username\r\n"
			+ "            where nameteam like %:searchText%\r\n"
			+ "						GROUP BY teams.teamid;", nativeQuery = true)
	List<Object[]> SearchTeam(@Param("searchText") String searchText);

	@Query(value = "SELECT teams.teamid,teams.sporttypeid,teams.nameteam,teams.quantity,teams.avatar,teams.contact,teams.descriptions,teams.username,teams.createdate, sporttype.categoryname, COUNT(CASE WHEN teamdetails.status = 1 THEN teamdetails.teamid ELSE NULL END) AS count,users.firstname,users.lastname\r\n"
			+ "			FROM teams LEFT JOIN teamdetails ON teams.teamid = teamdetails.teamid\r\n"
			+ "	LEFT JOIN sporttype ON  teams.sporttypeid =sporttype.sporttypeid\r\n"
			+ "	LEFT JOIN users ON teams.username = users.username\r\n"
			+ "            where sporttype.sporttypeid like :sporttypeid\r\n"
			+ "						GROUP BY teams.teamid;", nativeQuery = true)
	List<Object[]> FilterTeam(String sporttypeid);

	@Query(value = "SELECT * FROM sportify.teams\r\n"
			+ "where username like :username and teamid like :teamId", nativeQuery = true)
	Teams findOneTeam(Integer teamId, String username);

	@Query(value = "SELECT * FROM sportify.teams\r\n"
			+ "where teamid like :teamId  And username like :usernameLogin ", nativeQuery = true)
	Teams findOneTeamUser(Integer teamId, String usernameLogin);

	@Query(value = "SELECT * FROM sportify.teams\r\n" + "where username like :username ", nativeQuery = true)
	Teams findOneTeamUserin(String username);

	@Query(value = "SELECT * FROM sportify.teams\r\n" + "where username like :username ", nativeQuery = true)
	Teams findTeamUser(String username);

	// search in admin
	@Query(value = "SELECT * FROM teams WHERE (:nameteam IS NULL OR nameteam LIKE %:nameteam%) AND (:sporttypeid IS NULL OR sporttypeid LIKE %:sporttypeid%)", nativeQuery = true)
	List<Teams> searchTeamAdmin(@Param("nameteam") Optional<String> nameteam,
			@Param("sporttypeid") Optional<String> sporttypeid);
}
