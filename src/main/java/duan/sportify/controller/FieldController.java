package duan.sportify.controller;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.entities.Field;
import duan.sportify.entities.Shifts;
import duan.sportify.entities.Sporttype;
import duan.sportify.entities.Users;
import duan.sportify.entities.Voucher;
import duan.sportify.service.FieldService;
import duan.sportify.service.ShiftService;
import duan.sportify.service.SportTypeService;
import duan.sportify.service.UserService;
import duan.sportify.service.VoucherService;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("sportify")
public class FieldController {
	@Autowired
	ShiftService shiftservice; // Service giờ chơi theo ca
	@Autowired
	FieldService fieldservice; // Service sân
	@Autowired
	SportTypeService sporttypeservice; // Service loại môn thể thao
	@Autowired
	UserService userService;
	@Autowired
	VoucherService voucherService;
	// Biến chứa ID kiểu sportype khi click vào chọn
	private String selectedSportTypeId;
	private String dateselect;
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
			List<Shifts> shift = shiftservice.findAll(); // Gọi tất cả danh sách ca
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
			model.addAttribute("dateInput",dateInput);
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
		String sportTypeId = null;
		@SuppressWarnings("unused")
		List<Shifts> shift = shiftservice.findAll(); // Gọi tất cả danh sách ca
		List<Field> fieldList = fieldservice.findAll(); // Gọi tất cả sân thể thao
		List<Field> activeFields = new ArrayList<>();
		List<Sporttype> sporttypeListNotAll = sporttypeservice.findAll(); // Đổ môn thể thao không có Tất cả
		List<Sporttype> sporttypeList = sporttypeservice.findAll(); // Gọi tất cả loại môn thể thao có trong hệ thống
		Sporttype tatca = new Sporttype(); // Tạo đối tượng loại môn thể thao
		tatca.setCategoryname("Tất cả");	// Thêm Tất Cả vào list loại môn thể thao
		tatca.setSporttypeid("tatca");		// Ráng Id sporttype Tất cả = tatca
		sporttypeList.add(tatca); // Add đối tượng tatca vô danh sách loại môn thể thao
		//Đổ dữ liệu sân trạng thái đang hoạt động
		for (int i = 0; i < fieldList.size(); i++) {
		    if (fieldList.get(i).getStatus()) { // Kiểm tra nếu status == true
		        activeFields.add(fieldList.get(i)); // Thêm sân có status == true vào danh sách activeFields
		    }
		}
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
		model.addAttribute("fieldList",activeFields);
		model.addAttribute("selectedSportTypeId",selectedSportTypeId);
		model.addAttribute("cates",sporttypeList);
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
			List<Field> activeFields = new ArrayList<>();
			List<Field> fieldListById = fieldservice.findBySporttypeId(cid); // Lấy sân theo Id môn thể thao
			List<Sporttype> sporttypeListNotAll = sporttypeservice.findAll(); // Đổ môn thể thao không có Tất cả
			List<Sporttype> sporttypeList = sporttypeservice.findAll(); // Lấy tất cả môn thể thao
			Sporttype tatca = new Sporttype(); // Tạo đối tượng loại môn thể thao
			tatca.setCategoryname("Tất cả"); // Thêm Tất Cả vào list loại môn thể thao
			sporttypeList.add(tatca);  // Add đối tượng tatca vô danh sách loại môn thể thao
			tatca.setSporttypeid("tatca"); // Ráng Id sporttype Tất cả = tatca
			//Đổ dữ liệu sân trạng thái đang hoạt động
			for (int i = 0; i < fieldList.size(); i++) {
			    if (fieldList.get(i).getStatus()) { // Kiểm tra nếu status == true
			        activeFields.add(fieldList.get(i)); // Thêm sân có status == true vào danh sách activeFields
			    }
			}
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
				model.addAttribute("fieldList", activeFields);
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
	//Chuyển hướng trang detail

	@GetMapping("/field/detail/{idField}")
	public String viewDetail(Model model, @PathVariable Integer idField) { // Lấy id sân về
		List<Field> fieldListById = fieldservice.findFieldById(idField); // Đổ sân theo id lấy giao diện về.
		String nameSportype = fieldservice.findNameSporttypeById(idField); // Tên môn thể thao để hiện thị trong các sân liên quan ở Detail
		String idSporttype = fieldservice.findIdSporttypeById(idField); // Lấy id môn thể thao dựa vào sân đang chọn Detail
		List<Field> fieldListByIdSporttype = fieldservice.findBySporttypeIdlimit3(idSporttype); // Danh sách 3 sân liên quan đến môn thể thao đang xem.
		// Dữ liệu hiển thị trong trang Detail
		model.addAttribute("fieldListByIdSporttype",fieldListByIdSporttype);
		model.addAttribute("nameSportype",nameSportype);
		model.addAttribute("fieldListById",fieldListById);
		return "user/san-single";
	}
	LocalTime time = null;
	 
	
	@GetMapping("/field/booking/{idField}")
	public String Booking(Model model, @RequestParam(value = "nameshift", required = false) String nameShift, 
			@PathVariable Integer idField, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(value = "voucher", required = false) String voucher) {
		int discountpercent = 0;
		double pricevoucher = 0;
		double totalprice = 0 ;
		double thanhtien = 0;
		double tiencoc = 0;
		double conlai = 0;
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		List<Shifts> shift = shiftservice.findShiftByName(nameShift);
		for(int i = 0 ; i < shift.size();i++) {
				time = shift.get(i).getStarttime();
		}
		// Khởi tạo giờ 17:00
        LocalTime timeToCompare = LocalTime.of(17, 0);
        LocalDate currentDate = LocalDate.now();
        
       
        
        double phuthu = 0; // giá phụ thu
		if (loggedInUser != null) {
			List<Voucher> listvoucher = voucherService.findAll();
			List<Field> fieldListById = fieldservice.findFieldById(idField);
			double giasan = fieldListById.get(0).getPrice();
			String nameSportype = fieldservice.findNameSporttypeById(idField);
			List<Voucher> magiamgia = voucherService.findAll();
			if (time.isAfter(timeToCompare)) {
				phuthu = fieldListById.get(0).getPrice() * 30 / 100;
				totalprice = giasan + phuthu;
				model.addAttribute("totalprice", totalprice);

				model.addAttribute("phuthu", phuthu);
			} else {
				totalprice = giasan;
				model.addAttribute("totalprice", totalprice);
				model.addAttribute("phuthu", phuthu);
			}
			if (voucher == null) {
				thanhtien = totalprice;
				tiencoc = thanhtien * 30 / 100;
				conlai = thanhtien - tiencoc;
				
			} else {
				model.addAttribute("voucher", voucher);
				

				for (int i = 0; i < magiamgia.size(); i++) {
					// Trong đoạn mã của bạn
					Date startDateSql = (Date) magiamgia.get(i).getStartdate();
					LocalDate startDate = startDateSql.toLocalDate();
					Date endDateSql = (Date) magiamgia.get(i).getEnddate();
					LocalDate endDate = endDateSql.toLocalDate();		
					if(voucher.equals(magiamgia.get(i).getVoucherid()) && startDate.isBefore(currentDate) && endDate.isAfter(currentDate)) {
						discountpercent = magiamgia.get(i).getDiscountpercent();
						pricevoucher = totalprice * discountpercent / 100;
						thanhtien = totalprice - pricevoucher;
						tiencoc = thanhtien * 30 / 100;
						conlai = thanhtien - tiencoc;
						model.addAttribute("thongbaovoucher","Mã "+ voucher +" đã được áp dụng giảm " +discountpercent+"%");
						break;
					}
					else if(!voucher.equals(magiamgia.get(i).getVoucherid())) {
						thanhtien = totalprice - pricevoucher;
						tiencoc = thanhtien * 30 / 100;
						conlai = thanhtien - tiencoc;
						model.addAttribute("thongbaovoucher","Mã "+voucher+ " không hợp lệ");
					
					}else if(startDate.isAfter(currentDate) || endDate.isBefore(currentDate) && voucher.equals(magiamgia.get(i).getVoucherid())) {
						thanhtien = totalprice - pricevoucher;
						tiencoc = thanhtien * 30 / 100;
						conlai = thanhtien - tiencoc;
						model.addAttribute("thongbaovoucher","Mã "+voucher+ " không áp dụng hôm nay");
						
						break;
					}
					
				}
			
				
			}
			model.addAttribute("pricevoucher", pricevoucher);
			model.addAttribute("conlai", conlai);
			model.addAttribute("tiencoc", tiencoc);
			model.addAttribute("thanhtien", thanhtien);
			model.addAttribute("listvoucher",listvoucher);
			model.addAttribute("nameShift",nameShift);
			model.addAttribute("dateselect",dateselect);
			model.addAttribute("nameSportype",nameSportype);
			model.addAttribute("InfoUser",loggedInUser);
			model.addAttribute("fieldListById",fieldListById);
		
		}else {
			// Người dùng không tồn tại hoặc thông tin đăng nhập không chính xác
			return "redirect:/sportify/login";
		}
		
		return "user/checkout-dat-san";
	}
	
	@PostMapping("/field/detail/check")
	public String searchShiftDefault(Model model ,@RequestParam("fieldid") int idField  ,@RequestParam("dateInput") String date) {
		dateselect = date;
		List<Field> fieldListById = fieldservice.findFieldById(idField); // Đổ sân theo id lấy giao diện về.
		String nameSportype = fieldservice.findNameSporttypeById(idField); // Tên môn thể thao để hiện thị trong các sân liên quan ở Detail
		String idSporttype = fieldservice.findIdSporttypeById(idField); // Lấy id môn thể thao dựa vào sân đang chọn Detail
		List<Field> fieldListByIdSporttype = fieldservice.findBySporttypeIdlimit3(idSporttype); // Danh sách 3 sân liên quan đến môn thể thao đang xem.
		List<Shifts> shiftsNull = shiftservice.findShiftDate(idField, date);
		// Format yyyy-mm-dd thành dd-mm-yyyy
		LocalDate dateformat = LocalDate.parse(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = dateformat.format(formatter);
		// Dữ liệu hiển thị trong trang Detail
		model.addAttribute("date",date);
		model.addAttribute("formattedDate",formattedDate);
		model.addAttribute("shiftsNull",shiftsNull);
		model.addAttribute("fieldListByIdSporttype",fieldListByIdSporttype);
		model.addAttribute("nameSportype",nameSportype);
		model.addAttribute("fieldListById",fieldListById);
		return "user/san-single";
	}
	

}
