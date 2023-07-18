package duan.sportify.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Eventweb;
import duan.sportify.service.EventService;
import duan.sportify.service.impl.EventServiceImpl;
import io.micrometer.observation.Observation.Event;

@Controller
@RequestMapping("sportify")
public class EventController {
	@Autowired
	EventDAO eventDAO;
	@GetMapping("event")
	public String view(Model model) {
		List<Eventweb> eventwebList = eventDAO.findAllOrderByDateStart();
		model.addAttribute("eventList", eventwebList);
		return "user/blog";
	}
	
//	@GetMapping("/event/search/")
//    public String searchEventByMonth(@RequestParam("month") int month, Model model) {
//        List<Eventweb> eventList = eventDAO.findByMonth(month);
//        model.addAttribute("eventList", eventList);
//        return "user/blog"; // Trả về tên template Thymeleaf để hiển thị danh sách sân
//    }
	
	@GetMapping("eventdetail/{eventid}")
	public String showEventDetail(@PathVariable("eventid") Integer eventid, Model model) {
		Eventweb eventdetail = eventDAO.findEventById(eventid);
        model.addAttribute("eventdetail", eventdetail);
        List<Object[]> eventLQ = eventDAO.fillEventInMonth();
        model.addAttribute("eventLQ", eventLQ);
		return "user/blog-single";
	}
	
	
}
