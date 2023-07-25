package duan.sportify.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.GlobalExceptionHandler;

import duan.sportify.dao.TeamDAO;
import duan.sportify.entities.Teams;

import duan.sportify.utils.ErrorResponse;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/teams/")
public class TeamRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	TeamDAO teamDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Teams>> getAll(Model model){
		return ResponseEntity.ok(teamDAO.findAll());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Teams> getOne(@PathVariable("id") Integer id) {
		if(!teamDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(teamDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Teams> create(@RequestBody Teams team) {
		if(!teamDAO.existsById(team.getTeamid())) {
			
			return ResponseEntity.badRequest().build();
		}
		teamDAO.save(team);
		return ResponseEntity.ok(team);
	}
	@PutMapping("update")
	public ResponseEntity<Teams> update(@PathVariable("id") Integer id, @RequestBody Teams team) {
		if(!teamDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		teamDAO.save(team);
		return ResponseEntity.ok(team);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		if(!teamDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		teamDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
