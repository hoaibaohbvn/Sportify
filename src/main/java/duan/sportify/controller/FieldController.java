package duan.sportify.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.entities.Field;
import duan.sportify.entities.Shifts;
import duan.sportify.entities.Sporttype;
import duan.sportify.service.FieldService;
import duan.sportify.service.ShiftService;
import duan.sportify.service.SportTypeService;



@Controller
@RequestMapping("sportify")
public class FieldController {
	@Autowired
	FieldService fieldservice;
	@Autowired
	SportTypeService sporttypeservice;
	
	
	private String selectedSportTypeId;
	
	
	@GetMapping("/field")
	public String viewField(Model model) {
		selectedSportTypeId = "tatca";
		
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
//		System.out.println(shifts);
//		model.addAttribute("shift", shift);
		model.addAttribute("selectedSportTypeId",selectedSportTypeId);
		model.addAttribute("cates",sporttypeList);
		model.addAttribute("fieldList", eventList);

		return "user/san";
	}


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

	@GetMapping("/field/pricemin/{selectedValue}")
	public String listFieldMin(Model model, @PathVariable("selectedValue") String selectedValue) {
		   List<Field> eventList = new ArrayList<>();
		   if(selectedSportTypeId.equals("tatca")) {
			   eventList = fieldservice.listPriceMin();
		   }
		   else {
			   eventList = fieldservice.listMinPriceOfSportype(selectedSportTypeId);
		   }
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
			model.addAttribute("selectedSportTypeId",selectedSportTypeId);

			model.addAttribute("cates",sporttypeList);
		    model.addAttribute("fieldList",eventList);
		    return "user/san";
	}
	@GetMapping("/field/pricemax/{selectedValue1}")
	public String listFieldMax(Model model, @PathVariable("selectedValue1") String selectedValue1) {
		   List<Field> eventList = new ArrayList<>();
		   if(selectedSportTypeId.equals("tatca")) {
			   eventList = fieldservice.listPriceMax();
		   }
		   else {
			   eventList = fieldservice.listMaxPriceOfSportype(selectedSportTypeId);
		   }
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
			model.addAttribute("selectedSportTypeId",selectedSportTypeId);

			model.addAttribute("cates",sporttypeList);
		    model.addAttribute("fieldList",eventList);
		    return "user/san";
	}
}
