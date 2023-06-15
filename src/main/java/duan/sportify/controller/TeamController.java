package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.TeamDAO;
import duan.sportify.entities.Teams;

@Controller
public class TeamController {

	@Autowired
	TeamDAO teamdao;

	@RequestMapping("/team")
	public String viewTeam(Model model) {
		List<Teams> list = teamdao.findAll();
		model.addAttribute("team", list);
		return "user/doi";
	}
}
