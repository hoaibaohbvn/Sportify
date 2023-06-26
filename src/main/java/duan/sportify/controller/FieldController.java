package duan.sportify.controller;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
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
		Sporttype tatca = new Sporttype();
		tatca.setCategoryname("Tất cả");
		tatca.setSporttypeid("tatca");
		sporttypeList.add(tatca);
		Collections.sort(sporttypeList, new Comparator<Sporttype>() {
		    @Override
		    public int compare(Sporttype s1, Sporttype s2) {
		        // Xác định logic sắp xếp
		        if (s1.getCategoryname().equals("Tất cả")) {
		            return -1; // Đẩy "Tất cả" lên đầu
		        } else if (s2.getCategoryname().equals("Tất cả")) {
		            return 1; // Đẩy "Tất cả" lên đầu
		        } else {
		            return s1.getCategoryname().compareTo(s2.getCategoryname());
		        }
		    }
		});
		// Hiển thị danh sách đã sắp xếp
		for (Sporttype sporttype : sporttypeList) {
		    model.addAttribute("cates",sporttype);
		}
		model.addAttribute("cates",sporttypeList);
		model.addAttribute("fieldList", eventList);
		return "user/san";
	}
	private String selectedSportTypeId;
	
	@GetMapping("/field/{cid}")
	public String list(Model model, @PathVariable("cid") String cid) {
			selectedSportTypeId = cid;
			List<Field> eventList = fieldservice.findAll();
			List<Field> fieldList = fieldservice.findBySporttypeId(cid);
			List<Sporttype> sporttypeList = sporttypeservice.findAll();
			Sporttype tatca = new Sporttype();
			tatca.setCategoryname("Tất cả");
			sporttypeList.add(tatca);
			tatca.setSporttypeid("tatca");
			Collections.sort(sporttypeList, new Comparator<Sporttype>() {
			    @Override
			    public int compare(Sporttype s1, Sporttype s2) {
			        // Xác định logic sắp xếp
			        if (s1.getCategoryname().equals("Tất cả")) {
			            return -1; // Đẩy "Tất cả" lên đầu
			        } else if (s2.getCategoryname().equals("Tất cả")) {
			            return 1; // Đẩy "Tất cả" lên đầu
			        } else {
			            return s1.getCategoryname().compareTo(s2.getCategoryname());
			        }
			    }
			});
			// Hiển thị danh sách đã sắp xếp
			for (Sporttype sporttype : sporttypeList) {
			    model.addAttribute("cates",sporttype);
			}
			if(cid.equalsIgnoreCase("tatca")) {
				model.addAttribute("fieldList", eventList);
			}else {
			
				model.addAttribute("fieldList",fieldList);
			}
			model.addAttribute("cates",sporttypeList);

			model.addAttribute("selectedSportTypeId",selectedSportTypeId);
			return "user/san";
	}
	@GetMapping("/sandetail")
	public String viewFieldDetail(Model model) {	
		return "user/san-single";
	}

}
