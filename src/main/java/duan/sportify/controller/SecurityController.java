package duan.sportify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sportify")
public class SecurityController {
	@GetMapping("login")
	public String view() {
		return "/security/dist/index";
	}
}
