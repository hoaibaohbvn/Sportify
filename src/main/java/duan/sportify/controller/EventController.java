package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Eventweb;

@Controller
@RequestMapping("sportify")
public class EventController {
	@Autowired
	EventDAO eventDAO;
	@GetMapping("event")
	public String view(Model model) {
		List<Eventweb> eventwebList = eventDAO.findAll();
		model.addAttribute("eventList", eventwebList);
		return "user/blog";
	}
}
