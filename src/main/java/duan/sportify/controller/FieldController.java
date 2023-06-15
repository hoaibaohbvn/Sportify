package duan.sportify.controller;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.dao.FieldDAO;

import duan.sportify.entities.Field;

@Controller
@RequestMapping("")
public class FieldController {
	@Autowired 
	FieldDAO fieldDAO;

	@GetMapping("field")
	public String viewField(Model model) {
		List<Field> eventList = fieldDAO.findAll();
		
		model.addAttribute("fieldList", eventList);
		return "user/san";
	}
	@GetMapping("fieldDetail")
	public String viewFieldDetail(Model model) {
		
		return "user/san-single";
	}
}
