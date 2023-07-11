package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.TeamDAO;

@Controller
@RequestMapping("/sportify")
public class TeamController {

	@Autowired
	TeamDAO teamdao;

	@GetMapping("/team")
	public String viewTeam(Model model) {
		List<Object[]> list = teamdao.findAllTeam();
		model.addAttribute("team", list);
		return "user/doi";
	}
	
	
}
