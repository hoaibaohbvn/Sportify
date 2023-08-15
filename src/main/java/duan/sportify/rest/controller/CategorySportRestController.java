package duan.sportify.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.GlobalExceptionHandler;
import duan.sportify.dao.SportTypeDAO;
import duan.sportify.entities.Sporttype;

import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/sportTypes/")
public class CategorySportRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	SportTypeDAO sportTypeDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Sporttype>> getAll(Model model){
		return ResponseEntity.ok(sportTypeDAO.findAll());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Sporttype> getOne(@PathVariable("id") String id) {
		if(!sportTypeDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(sportTypeDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Sporttype> create(@Valid @RequestBody Sporttype sporttype) {
		if (sporttype.getSporttypeid() != null && sportTypeDAO.existsById(sporttype.getSporttypeid())) {
	        return ResponseEntity.status(1000).build();
	    }else if(sporttype.getSporttypeid() == null && !sportTypeDAO.existsById(sporttype.getSporttypeid())) {
	    	return ResponseEntity.badRequest().build();
	    }else {
	    	sportTypeDAO.save(sporttype);
		    return ResponseEntity.ok(sporttype);
	    }
	}
	@PutMapping("update/{id}")
	public ResponseEntity<Sporttype> update(@PathVariable("id") String id, @Valid @RequestBody Sporttype sportType) {
		if(!sportTypeDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		sportTypeDAO.save(sportType);
		return ResponseEntity.ok(sportType);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		if(!sportTypeDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		sportTypeDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
	// search
	@GetMapping("search")
	public ResponseEntity<List<Sporttype>> search(@RequestParam("categoryname") Optional<String> categoryname){
		return ResponseEntity.ok(sportTypeDAO.searchSport(categoryname));
	}
	
}
