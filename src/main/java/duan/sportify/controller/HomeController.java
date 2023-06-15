package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.EventDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.entities.Eventweb;



@Controller
@RequestMapping("")
public class HomeController {
	@Autowired 
	FieldDAO fieldDAO;
	@Autowired 
	EventDAO eventDAO;
	@GetMapping()
	public String view(Model model) {
		
		List<Eventweb> eventList = eventDAO.findAll();
		model.addAttribute("eventList", eventList);
		return "user/blog";
	}
}