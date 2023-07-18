package duan.sportify.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	ShiftService shiftservice; // Service giờ chơi theo ca
	@Autowired
	FieldService fieldservice; // Service sân
	@Autowired
	SportTypeService sporttypeservice; // Service loại môn thể thao
	
	// Biến chứa ID kiểu sportype khi click vào chọn
	private String selectedSportTypeId;
	// Tìm sân trống theo input: date, sportype, giờ chơi
	 @PostMapping("/field/search")
	 public String SreachData(@RequestParam("dateInput") String dateInput,
                @RequestParam("categorySelect") String categorySelect,
                @RequestParam("shiftSelect") int shiftSelect, Model model) {
		 	selectedSportTypeId = categorySelect;
		 	// Lấy List sân thỏa mãn đầu vào người dùng tìm: date, môn thể thao, giờ chơi theo ID ca
		    List<Field> listsearch = fieldservice.findSearch(dateInput, categorySelect, shiftSelect);
		    List<Shifts> shiftById = shiftservice.findShiftById(shiftSelect); // List này để lấy tên ca đổ lên thông báo
		    List<Sporttype> sportypeById = sporttypeservice.findSporttypeById(categorySelect); // List này lấy tên môn thể thao đổ lên thông báo
			@SuppressWarnings("unused")
			List<Shifts> shift = shiftservice.findAll(); // Đổ tất cả ca lên dropdown tìm kiếm
			List<Sporttype> sporttypeListNotAll = sporttypeservice.findAll(); // Đổ môn thể thao không có Tất cả
			List<Sporttype> sporttypeList = sporttypeservice.findAll(); // Đổ tất cả môn thể thao 
			Sporttype tatca = new Sporttype(); // Tạo đối tượng loại môn thể thao
			tatca.setCategoryname("Tất cả"); // Thêm Tất cả vào list đối tượng
			tatca.setSporttypeid("tatca"); // Có Id là tatca
			sporttypeList.add(tatca);
			// Sắp xếp danh sách loại môn thể thao theo: Tất cả đầu tiên => các môn khác
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
			// Format yyyy-mm-dd thành dd-mm-yyyy
			LocalDate date = LocalDate.parse(dateInput);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedDate = date.format(formatter);
			//Thông báo kết quả tìm kiếm
			String nameshift = null; // Biến chứa tên ca
			for(int i = 0 ; i < shiftById.size();i++ ) { // Lấy tên ca theo id ca nhập vào
				nameshift = shiftById.get(i).getNameshift();
			}
			String namesporttype = null; // Biến chứa tên môn thể thao
			for(int i = 0 ; i  < sportypeById.size();i++) { //Lấy tên môn thể thao theo id nhập vào
				namesporttype = sportypeById.get(i).getCategoryname();
			}
			String message = "Kết quả tìm kiếm sân trống: ";
			// Add các đối tượng vào model để qua giao diện hiển thị
			model.addAttribute("namesporttype",namesporttype);
			model.addAttribute("nameshift",nameshift);
			model.addAttribute("formattedDate",formattedDate);
			model.addAttribute("thongbao",message);
			model.addAttribute("cateNotAll",sporttypeListNotAll); // Môn thể thao không có Tất cả
			model.addAttribute("shift", shift);
			model.addAttribute("selectedSportTypeId",selectedSportTypeId);
			model.addAttribute("cates",sporttypeList);
			model.addAttribute("fieldList", listsearch);
			// Chuyển hướng đến trang sân
	        return "user/san";
	    }
	// View giao diện lên theo url http://localhost:8080/sportify/field
	@GetMapping("/field")
	public String viewField(Model model) {
		selectedSportTypeId = "tatca"; // Giá trị được chọn mặc định môn thể thao là tất cả
		@SuppressWarnings("unused")
		List<Shifts> shift = shiftservice.findAll(); // Gọi tất cả danh sách ca
		List<Field> fieldList = fieldservice.findAll(); // Gọi tất cả sân thể thao
		List<Sporttype> sporttypeListNotAll = sporttypeservice.findAll(); // Đổ môn thể thao không có Tất cả
		List<Sporttype> sporttypeList = sporttypeservice.findAll(); // Gọi tất cả loại môn thể thao có trong hệ thống
		Sporttype tatca = new Sporttype(); // Tạo đối tượng loại môn thể thao
		tatca.setCategoryname("Tất cả");	// Thêm Tất Cả vào list loại môn thể thao
		tatca.setSporttypeid("tatca");		// Ráng Id sporttype Tất cả = tatca
		sporttypeList.add(tatca); // Add đối tượng tatca vô danh sách loại môn thể thao
		// Sắp xếp danh sách loại môn thể thao theo: Tất cả đầu tiên => các môn khác
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
		// Add các đối tượng vào model để qua giao diện hiển thị
		model.addAttribute("cateNotAll",sporttypeListNotAll); // môn thể thao không có tất cả
		model.addAttribute("shift", shift);
		model.addAttribute("selectedSportTypeId",selectedSportTypeId);
		model.addAttribute("cates",sporttypeList);
		model.addAttribute("fieldList", fieldList);
		// Chuyển hướng đến trang sân
		return "user/san";
	}
	// View Field theo môn thể thao - input vào là ID sportype
	@GetMapping("/field/{cid}")
	public String list(Model model, @PathVariable("cid") String cid) {
			selectedSportTypeId = cid; // Giá trị id sporttype khi người dùng chọn
			@SuppressWarnings("unused")
			List<Shifts> shift = shiftservice.findAll(); // Lấy tất cả ca
			List<Field> fieldList = fieldservice.findAll(); // Lấy tất cả sân
			List<Field> fieldListById = fieldservice.findBySporttypeId(cid); // Lấy sân theo Id môn thể thao
			List<Sporttype> sporttypeListNotAll = sporttypeservice.findAll(); // Đổ môn thể thao không có Tất cả
			List<Sporttype> sporttypeList = sporttypeservice.findAll(); // Lấy tất cả môn thể thao
			Sporttype tatca = new Sporttype(); // Tạo đối tượng loại môn thể thao
			tatca.setCategoryname("Tất cả"); // Thêm Tất Cả vào list loại môn thể thao
			sporttypeList.add(tatca);  // Add đối tượng tatca vô danh sách loại môn thể thao
			tatca.setSporttypeid("tatca"); // Ráng Id sporttype Tất cả = tatca
			// Sắp xếp danh sách loại môn thể thao theo: Tất cả đầu tiên => các môn khác
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
			// Nếu id sportype được chọn là tatca thì trả về tất cả sân
			if(cid.equalsIgnoreCase("tatca")) {
				model.addAttribute("fieldList", fieldList);
			}else { // Còn lại thì trả về các sân theo môn thể thao
			
				model.addAttribute("fieldList",fieldListById);
			}
			// Add các đối tượng vào model để qua giao diện hiển thị
			model.addAttribute("cateNotAll",sporttypeListNotAll); // môn thể thao không có tất cả
			model.addAttribute("shift", shift);
			model.addAttribute("cates",sporttypeList);
			model.addAttribute("selectedSportTypeId",selectedSportTypeId);
			// Chuyển hướng trang giao diện sân
			return "user/san";
	}
	// Sắp xếp sân theo min => max 
	@GetMapping("/field/pricemin/{selectedValue}")
	public String listFieldMin(Model model, @PathVariable("selectedValue") String selectedValue) {
		   List<Field> fieldtList = new ArrayList<>(); // Tạo đối tượng sân
		   @SuppressWarnings("unused")
			List<Shifts> shift = shiftservice.findAll(); // Lấy tất cả ca
		   
		   if(selectedSportTypeId.equals("tatca")) { // Nếu chọn tất cả môn thể thao thì trả về toàn bộ sân theo price min => max
			   fieldtList = fieldservice.listPriceMin();
		   }
		   else { // Ngược lại thì trả về sân theo loại môn thể thao có giá min => max
			   fieldtList = fieldservice.listMinPriceOfSportype(selectedSportTypeId);
		   }
			List<Sporttype> sporttypeListNotAll = sporttypeservice.findAll(); // Đổ môn thể thao không có Tất cả
		    List<Sporttype> sporttypeList = sporttypeservice.findAll(); // Lấy tất cả loại môn thể thao
			Sporttype tatca = new Sporttype(); // Tạo đối tượng loại môn thể thao
			tatca.setCategoryname("Tất cả"); // Thêm Tất Cả vào list loại môn thể thao
			tatca.setSporttypeid("tatca"); // Ráng Id sporttype Tất cả = tatca
			sporttypeList.add(tatca); // Add đối tượng tatca vô danh sách loại môn thể thao
			// Sắp xếp danh sách loại môn thể thao theo: Tất cả đầu tiên => các môn khác
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
			// Add các đối tượng vào model để qua giao diện hiển thị
			model.addAttribute("cateNotAll",sporttypeListNotAll); // môn thể thao không có tất cả
			model.addAttribute("shift", shift);
			model.addAttribute("selectedSportTypeId",selectedSportTypeId);
			model.addAttribute("cates",sporttypeList);
		    model.addAttribute("fieldList",fieldtList);
		    // Load lại trang sân
		    return "user/san";
	}
	// Sắp xếp sân theo max => min
	@GetMapping("/field/pricemax/{selectedValue1}")
	public String listFieldMax(Model model, @PathVariable("selectedValue1") String selectedValue1) {
		  List<Field> fieldList = new ArrayList<>(); // Tạo đối tượng sân
		   @SuppressWarnings("unused")
			List<Shifts> shift = shiftservice.findAll(); // Lấy tất cả ca
		   if(selectedSportTypeId.equals("tatca")) { // Nếu chọn tất cả  môn thể thao thì trả về tất cả sân theo giá max => min
			   fieldList = fieldservice.listPriceMax();
		   }
		   else { // Ngược lại trả về các sân theo môn thể thao có giá theo max => min
			   fieldList = fieldservice.listMaxPriceOfSportype(selectedSportTypeId);
		   }
			List<Sporttype> sporttypeListNotAll = sporttypeservice.findAll(); // Đổ môn thể thao không có Tất cả
		    List<Sporttype> sporttypeList = sporttypeservice.findAll(); // Lấy tất cả loại môn thể thao
			Sporttype tatca = new Sporttype(); // Tạo đối tượng loại môn thể thao
			tatca.setCategoryname("Tất cả"); // Thêm Tất Cả vào list loại môn thể thao
			tatca.setSporttypeid("tatca"); // Ráng Id sporttype Tất cả = tatca
			sporttypeList.add(tatca); // Add đối tượng tatca vô danh sách loại môn thể thao
			// Sắp xếp danh sách loại môn thể thao theo: Tất cả đầu tiên => các môn khác
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
			// Add các đối tượng vào model để qua giao diện hiển thị
			model.addAttribute("cateNotAll",sporttypeListNotAll); // môn thể thao không có tất cả
			model.addAttribute("shift", shift);
			model.addAttribute("selectedSportTypeId",selectedSportTypeId);
			model.addAttribute("cates",sporttypeList);
		    model.addAttribute("fieldList",fieldList);
		    // Load lại trang sân
		    return "user/san";
	}
	@GetMapping("/field/detail/{idField}")
	public String viewDetail(Model model, @PathVariable Integer idField) {
		List<Field> fieldListById = fieldservice.findFieldById(idField);
		String nameSportype = fieldservice.findNameSporttypeById(idField);
		String idSporttype = fieldservice.findIdSporttypeById(idField);
		List<Field> fieldListByIdSporttype = fieldservice.findBySporttypeIdlimit3(idSporttype); // Lấy sân theo Id môn thể thao
		model.addAttribute("fieldListByIdSporttype",fieldListByIdSporttype);
		model.addAttribute("nameSportype",nameSportype);
		model.addAttribute("fieldListById",fieldListById);
		return "user/san-single";
	}
}
