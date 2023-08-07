package duan.sportify.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duan.sportify.entities.Users;
@RestController
@RequestMapping("/sportify/user")
public class UserController {
	 	@Autowired
	    private HttpSession session;

	    @GetMapping("/get-username")
	    public ResponseEntity<Map<String, String>> getUsername() {
	        Map<String, String> response = new HashMap<>();
	        @SuppressWarnings("unchecked")
			Map<String, Object> authentication = (Map<String, Object>) session.getAttribute("authentication");
	        if (authentication != null && authentication.containsKey("user")) {
	            Users user = (Users) authentication.get("user");
	            response.put("username", user.getUsername());
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("error", "User not authenticated");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        }
	    }
	    
}
