package duan.sportify.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sportify")
public class HomeAdminController {
	@GetMapping()
	public String view() {
		
		return "admin/index";
	}
}
