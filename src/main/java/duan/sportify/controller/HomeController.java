package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.BookingDetailDAO;
import duan.sportify.dao.EventDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Eventweb;
import duan.sportify.entities.Field;



@Controller
@RequestMapping("sportify")
public class HomeController {
	@Autowired 
	FieldDAO fieldDAO;
	@Autowired 
	EventDAO eventDAO;
	@Autowired
	BookingDetailDAO bookingDetailDAO;
	@GetMapping("")
	public String view(Model model) {
		
		List<Object[]> eventList = eventDAO.fillEventInMonth();
		model.addAttribute("eventList", eventList);
		List<Object[]> fieldList = bookingDetailDAO.findTopFieldsWithMostBookings();
		
		model.addAttribute("fieldList", fieldList);
		return "user/index";
	}
}
