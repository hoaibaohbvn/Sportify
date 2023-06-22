package duan.sportify.controller;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.dao.FieldDAO;
import duan.sportify.dao.SportTypeDAO;
import duan.sportify.entities.Field;
import duan.sportify.entities.Sporttype;
import duan.sportify.service.FieldService;
import duan.sportify.service.SportTypeService;



@Controller
@RequestMapping("sportify")
public class FieldController {
	@Autowired
	FieldService fieldservice;
	@Autowired
	SportTypeService sporttypeservice;
	@GetMapping("/field")
	public String viewField(Model model) {
		List<Field> eventList = fieldservice.findAll();
		List<Sporttype> sporttypeList = sporttypeservice.findAll();
		model.addAttribute("cates",sporttypeList);
		model.addAttribute("fieldList", eventList);
		return "user/san";
	}
	@GetMapping("/field/{cid}")
	public String list(Model model, @PathVariable("cid") String cid) {
			Sporttype sport = sporttypeservice.findById(cid);
			List<Field> fieldList = fieldservice.findBySporttypeId(cid);
			List<Sporttype> sporttypeList = sporttypeservice.findAll();
			model.addAttribute("cates",sporttypeList);
			model.addAttribute("fieldList",fieldList);
			return "user/san";
	}
	@GetMapping("/sandetail")
	public String viewFieldDetail(Model model) {	
		return "user/san-single";
	}

}
