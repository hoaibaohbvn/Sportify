package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.EventDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.dao.ProductDAO;
import duan.sportify.dao.UserDAO;
//import io.micrometer.observation.Observation.Event;

@SuppressWarnings("unused")
@Controller
@RequestMapping("sportify")
public class AboutController {
	@Autowired
	UserDAO userDAO;
	@Autowired
	FieldDAO fieldDAO;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	EventDAO eventDAO;
	@GetMapping("about")
	public String view(Model model) {
		List<Object> userCount = userDAO.CountUser();
		List<Object> fieldCount = fieldDAO.CountField();
		List<Object> eventCount = eventDAO.CountEvent();
		List<Object> productCount = productDAO.CountProduct();
		
		model.addAttribute("userCount", userCount);
		model.addAttribute("fieldCount", fieldCount);
		model.addAttribute("eventCount", eventCount);
		model.addAttribute("productCount", productCount);
		return"user/about";
	}
	@GetMapping("chinhsach")
	public String viewchinhsach(Model model) {
		return"user/chinhsach";
	}
	@GetMapping("quydinh")
	public String viewdieukien(Model model) {
		return "user/quydinh";
	}
}
