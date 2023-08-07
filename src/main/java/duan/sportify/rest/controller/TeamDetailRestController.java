//package duan.sportify.rest.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import duan.sportify.dao.TeamDetailDAO;
//import duan.sportify.entities.Teamdetails;
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/rest/teams/")
//public class TeamDetailRestController {
//	@Autowired
//	TeamDetailDAO teamDetailDAO;
//	
//	@GetMapping("{teamid}")
//    public ResponseEntity<List<Teamdetails>> getTeamDetailsByTeamId(@PathVariable("teamid") Integer teamid) {
//        List<Teamdetails> teamDetails = teamDetailDAO.findByTeamIdAdmin(teamid);
//        if (teamDetails.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(teamDetails);
//    }
//}
