package duan.sportify.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Eventweb;
import duan.sportify.service.EventService;
import duan.sportify.service.impl.EventServiceImpl;
import io.micrometer.observation.Observation.Event;

@Controller
@RequestMapping("/sportify")
public class EventController {
	@Autowired
	EventDAO eventDAO;
	@GetMapping("/event")
	public String view(Model model , Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
		
		Page<Eventweb> eventwebList;
		int keywordLength = keyword.length();// Kiểm tra xem người dùng có nhập vào ô tìm kiếm hay không		
		if(keywordLength > 0 ) {
			eventwebList = eventDAO.searchEvents(keyword, pageable);
			
		}else {
			eventwebList = eventDAO.findAllOrderByDateStart(pageable);
		}
		
		
		
		List<Eventweb> events = eventwebList.getContent();
		
		model.addAttribute("eventList", events);
		model.addAttribute("page", eventwebList);
		model.addAttribute("keyword", keyword);
		return "user/blog";
	}
	
	
	@GetMapping("eventdetail/{eventid}")
	public String showEventDetail(@PathVariable("eventid") Integer eventid, Model model) {
		Eventweb eventdetail = eventDAO.findEventById(eventid);
        model.addAttribute("eventdetail", eventdetail);
        List<Object[]> eventLQ = eventDAO.fillEventInMonth();
        model.addAttribute("eventLQ", eventLQ);
		return "user/blog-single";
	}

	
	@PostMapping("/search")
    public String search(@RequestParam("keyword")String keyword, Model model) {
		try {
	        String encodedSearchText = URLEncoder.encode(keyword, "UTF-8");
	        if (encodedSearchText.isEmpty()) {
	            return "redirect:/sportify/event";
	        }
	        return "redirect:/sportify/event?keyword=" + encodedSearchText;
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return "error"; // Ví dụ: "error.html"
	    }
    }
}
