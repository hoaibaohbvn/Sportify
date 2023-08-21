package duan.sportify.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.dao.BookingDetailDAO;
import duan.sportify.dao.EventDAO;
import duan.sportify.dao.FieldDAO;
import duan.sportify.dao.ProductDAO;



@CrossOrigin(origins = "*")
@Controller
@RequestMapping("sportify")
public class HomeController {
	// Tiêm FieldDAO
	@Autowired 
	FieldDAO fieldDAO;
	@Autowired 
	// Tiêm EventDAO
	EventDAO eventDAO;
	// Tiêm BookingDetailDAO
	@Autowired
	BookingDetailDAO bookingDetailDAO;
	@Autowired
	ProductDAO productDAO;
	@GetMapping("")
	public String view(Model model,  HttpServletRequest request) {
		List<Object[]> eventList = eventDAO.fillEventInMonth();
		model.addAttribute("eventList", eventList);
		List<Object[]> fieldList = bookingDetailDAO.findTopFieldsWithMostBookings();
		model.addAttribute("fieldList", fieldList);
		List<Object[]> topproduct = productDAO.Top4OrderProduct();
		model.addAttribute("topproduct", topproduct);
		return "user/index";
	}

	
	
}
