package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import duan.sportify.dao.BookingDetailDAO;
import duan.sportify.dao.EventDAO;
import duan.sportify.dao.FieldDAO;



@CrossOrigin(origins = "*")
@Controller
// Tạo đường dẫn chung http://localhost:8080/sportify
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
	/**
	 * phương thức gọi đến trang home
	 * @param model giá trị của model
	 * @return trả về file index trong user
	 */
	@GetMapping("")
	public String view(Model model) {
		
		List<Object[]> eventList = eventDAO.fillEventInMonth();
		model.addAttribute("eventList", eventList);
		List<Object[]> fieldList = bookingDetailDAO.findTopFieldsWithMostBookings();
		
		model.addAttribute("fieldList", fieldList);
		return "user/index";
	}
	/**
	 * Phương thức gọi đến trang admin
	 * Đường dẫn: http://localhost:8080/sportify/admin
	 * @return dường dẫn http://localhost:8080/admin/index.html
	 */
	@GetMapping("admin")
	public String admin() {
		return "redirect:/admin/index.html";
	}
	
	
}
