package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Events;


@Controller
@RequestMapping("sportify")
public class EventController {
//	@GetMapping("event")
//	public String viewEvent(Model model) {
//	return "user/blog";
//	}

	@Autowired 
	EventDAO eventDAO;
	@GetMapping("event")
	public String viewEvent(Model model) {
		List<Events> eventList = eventDAO.findAll();
		model.addAttribute("eventList", eventList);
		return "user/blog";
	}
	
	
	
}
