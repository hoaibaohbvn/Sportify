package duan.sportify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sportify")
public class ContactController {
	@GetMapping("contact")
	public String view() {
		
		return "user/contact"; 
	}
}
