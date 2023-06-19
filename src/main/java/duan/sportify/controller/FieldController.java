package duan.sportify.controller;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
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
@RequestMapping("sportify")
public class FieldController {
	@Autowired 
	FieldDAO fieldDAO;

	@GetMapping("field")
	public String viewField(Model model) {
//		double vnd = 0;


		List<Field> eventList = fieldDAO.findAll();
//		double[] vndArray = new double[eventList.size()];
//		for(int i = 0; i < eventList.size(); i++) {
//			 vnd = eventList.get(i).getPrice();
//			 vndArray[i] = vnd;
//				Locale locale = new Locale("vi", "VN");
//		        Currency currency = Currency.getInstance("VND");
//
//		        DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
//		        df.setCurrency(currency);
//		        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
//		        numberFormat.setCurrency(currency);
//		        System.out.println(numberFormat.format(vnd));
//		        model.addAttribute("vndArray",vndArray);
//		}


//		List<Field> eventList = fieldDAO.findAll();

		model.addAttribute("fieldList", eventList);
		return "user/san";
	}
	@GetMapping("fieldDetail")
	public String viewFieldDetail(Model model) {
		
		return "user/san-single";
	}
}
