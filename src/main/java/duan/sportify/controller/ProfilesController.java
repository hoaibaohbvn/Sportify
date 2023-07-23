package duan.sportify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.entities.Users;
import duan.sportify.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("sportify")
public class ProfilesController {
	@Autowired
	UserService userService;
	
	@GetMapping("/profile")
	public String view(Model model, HttpSession session) {
		//Kiểm tra đăng nhập
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		String userName=loggedInUser.getUsername();
		Users profiles = userService.findById(userName);
		model.addAttribute("prodiles",profiles);
		return "/user/profiles";
	}
}
