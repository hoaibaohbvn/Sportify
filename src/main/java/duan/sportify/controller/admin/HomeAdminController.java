package duan.sportify.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@SuppressWarnings("unused")
@Controller
// Tạo đường dẫn chung http://localhost:8080/sportify
@RequestMapping("sportify")
public class HomeAdminController {
	@GetMapping("admin")
	public String admin() {
		return "redirect:/admin/index.html";
	}
	
}
