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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.dao.EventDAO;
import duan.sportify.entities.Eventweb;
import duan.sportify.service.EventService;
import duan.sportify.service.impl.EventServiceImpl;


@SuppressWarnings("unused")
@Controller
@RequestMapping("/sportify")
public class EventController {
	@Autowired
	EventDAO eventDAO;
	@GetMapping("/event")
	public String view(@ModelAttribute("eventName") String eventName, Model model , Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
		) {
		Page<Eventweb> eventwebList;
		int keywordLength = keyword.length();// Kiểm tra xem người dùng có nhập vào ô tìm kiếm hay không
		if (eventName == null) {
	        eventName = "";
	    }
		if(keywordLength > 0 && eventName.isEmpty()) {
			eventwebList = eventDAO.searchEvents(keyword, pageable);
			
		}else if(eventName.equalsIgnoreCase("thethao") && keywordLength==0) {
			eventwebList = eventDAO.searchbtnTheThao(pageable);
		}else if(eventName=="khuyenmai" && keywordLength==0) {
			eventwebList = eventDAO.searchbtnKhuyenMai(pageable);
		}else if(eventName=="baotri" && keywordLength==0) {
			eventwebList = eventDAO.searchbtnBaoTri(pageable);
		}
			else{
			
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
    public String search(@RequestParam("keyword")String keyword, Model model ,RedirectAttributes redirectAttributes) {
		String event= null;
	    redirectAttributes.addFlashAttribute("eventName", event);
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
	
	
	@GetMapping("/event/thethao")
	public String thethao(Model model,RedirectAttributes redirectAttributes) {
		String event= "thethao";
	    redirectAttributes.addFlashAttribute("eventName", event);
		return "redirect:/sportify/event?eventName="+event;
	}
	
	@GetMapping("/event/khuyenmai")
	public String khuyenmai(Model model,RedirectAttributes redirectAttributes) {
		String event= "khuyenmai";
	    redirectAttributes.addFlashAttribute("eventName", event);
		return "redirect:/sportify/event?eventName="+event;
	}
	
	@GetMapping("/event/baotri")
	public String baotri(Model model,RedirectAttributes redirectAttributes) {
		String event= "baotri";
	    redirectAttributes.addFlashAttribute("eventName", event);
		return "redirect:/sportify/event?eventName="+event;
	}
	
	
}
