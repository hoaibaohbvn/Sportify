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


import duan.sportify.entities.Bookings;
import duan.sportify.entities.Field;
import duan.sportify.entities.Shifts;
import duan.sportify.entities.Sporttype;
import duan.sportify.entities.Users;
import duan.sportify.entities.Voucher;
import duan.sportify.service.BookingDetailService;
import duan.sportify.service.BookingService;
import duan.sportify.service.FieldService;
import duan.sportify.service.ShiftService;
import duan.sportify.service.SportTypeService;
import duan.sportify.service.UserService;
import duan.sportify.service.VoucherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@SuppressWarnings("unused")
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
	UserService userService; // Service User
	@Autowired
	BookingService bookingservice; // Service booking
	@Autowired
	BookingDetailService bookingdetailservice; // Service bookingdetail
	@Autowired
	VoucherService voucherService; // Service mã giảm giá
	
	// Biến chứa ID kiểu sportype khi click vào chọn
	private String selectedSportTypeId; // Loại môn thể thao chọn để tìm kiếm
	private String dateselect; // Ngày được chọn 
	String userlogin = null; // username người dùng đã đăng nhập
	String phone = null; // SĐT
	
	// Tìm sân trống theo input: date, sportype, giờ chơi
	 @PostMapping("/field/search")
	 public String SreachData(@RequestParam("dateInput") String dateInput,
                @RequestParam("categorySelect") String categorySelect,
                @RequestParam("shiftSelect") int shiftSelect, Model model) {
		 	selectedSportTypeId = categorySelect; // môn thể thao được chọn ráng biến toàn cục
		 	// Lấy List sân thỏa mãn đầu vào người dùng tìm: date, môn thể thao, giờ chơi theo ID ca
		    List<Field> listsearch = fieldservice.findSearch(dateInput, categorySelect, shiftSelect); // List sân thỏa mãn giá trị truyền vào
		    List<Shifts> shiftById = shiftservice.findShiftById(shiftSelect); // List này để lấy tên ca đổ lên thông báo
		    List<Sporttype> sportypeById = sporttypeservice.findSporttypeById(categorySelect); // List này lấy tên môn thể thao đổ lên thông báo
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
			model.addAttribute("dateInput",dateInput); // Ngày nhập vào tìm kiếm
			model.addAttribute("namesporttype",namesporttype); // Tên môn thể thao
			model.addAttribute("nameshift",nameshift);	// Tên ca 
			model.addAttribute("formattedDate",formattedDate); // Ngày đã format thành dd-mm-yyyy
			model.addAttribute("thongbao",message); // Thông báo kết quả tìm kiếm
			model.addAttribute("cateNotAll",sporttypeListNotAll); // Môn thể thao không có Tất cả
			model.addAttribute("shift", shift); // Tất cả danh sách ca
			model.addAttribute("selectedSportTypeId",selectedSportTypeId); // ID Môn thể thao đã chọn tìm kiếm
			model.addAttribute("cates",sporttypeList);	// Tất cả các môn thể thao
			model.addAttribute("fieldList", listsearch); // Danh sách sân thỏa mản khi tìm kiếm
			// Chuyển hướng đến trang sân
	        return "user/san";
	    }
	 
	// View giao diện lên theo url http://localhost:8080/sportify/field
	@GetMapping("/field")
	public String viewField(Model model , HttpServletRequest request) {
		userlogin = (String) request.getSession().getAttribute("username"); // Kiểm tra xem có username đang đăng nhập 
		
		selectedSportTypeId = "tatca"; // Giá trị được chọn mặc định môn thể thao là tất cả
		String sportTypeId = null; // Id loại môn thể thao ban đầu rỗng
		List<Shifts> shift = shiftservice.findAll(); // Gọi tất cả danh sách ca
		List<Field> fieldList = fieldservice.findAll(); // Gọi tất cả sân thể thao
		List<Field> activeFields = new ArrayList<>(); // Danh sách sân đang hoạt động
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
		model.addAttribute("shift", shift); // Tất cả danh sách ca
		model.addAttribute("fieldList",activeFields); // Dách sách sân đang còn hoạt động
		model.addAttribute("selectedSportTypeId",selectedSportTypeId); // Danh mục môn thể thao được chọn
		model.addAttribute("cates",sporttypeList); // Category môn thể thao
		// Chuyển hướng đến trang sân
		return "user/san";
	}
	// View Field theo môn thể thao - input vào là ID sportype
	@GetMapping("/field/{cid}")
	public String list(Model model, @PathVariable("cid") String cid) {
			selectedSportTypeId = cid; // Giá trị id sporttype khi người dùng chọn
			List<Shifts> shift = shiftservice.findAll(); // Lấy tất cả ca
			List<Field> fieldList = fieldservice.findAll(); // Lấy tất cả sân
			List<Field> activeFields = new ArrayList<>(); // Danh sách sân đang còn hoạt động
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
			model.addAttribute("shift", shift); // Tất cả các ca
			model.addAttribute("cates",sporttypeList); // Tất cả môn thể thao đổ lên category
			model.addAttribute("selectedSportTypeId",selectedSportTypeId); // Môn thể thao được chọn ở danh mục
			// Chuyển hướng trang giao diện sân
			return "user/san";
	
	}
	
	//Chuyển hướng trang detail sân
	@GetMapping("/field/detail/{idField}")
	public String viewDetail(Model model, @PathVariable Integer idField, HttpServletRequest request) { // Lấy id sân về
		userlogin = (String) request.getSession().getAttribute("username"); // Kiểm tra xem có username đang đăng nhập 
		List<Field> fieldListById = fieldservice.findFieldById(idField); // Đổ sân theo id lấy giao diện về.
		String nameSportype = fieldservice.findNameSporttypeById(idField); // Tên môn thể thao để hiện thị trong các sân liên quan ở Detail
		String idSporttype = fieldservice.findIdSporttypeById(idField); // Lấy id môn thể thao dựa vào sân đang chọn Detail
		List<Field> fieldListByIdSporttype = fieldservice.findBySporttypeIdlimit3(idSporttype); // Danh sách 3 sân liên quan đến môn thể thao đang xem.
		// Dữ liệu hiển thị trong trang Detail
		model.addAttribute("fieldListByIdSporttype",fieldListByIdSporttype); // Sân liên quan
		model.addAttribute("nameSportype",nameSportype); // Tên môn thể thao
		model.addAttribute("fieldListById",fieldListById); // Thông tin sân đã chọn
		// Trả dữ liệu vào sân detail
		return "user/san-single";
		
	}
	
	LocalTime time = null; // giờ bắt đầu
	// Chuyển hướng sang checkout booking
	@GetMapping("/field/booking/{idField}")
	public String Booking(Model model, @RequestParam(value = "nameshift", required = false) String nameShift, 
			@PathVariable Integer idField, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam(value = "voucher", required = false) String voucher,
			@RequestParam(name = "note", required = false) String note,HttpServletRequest request) {
		int discountpercent = 0; // Biến phần trăm giảm giá
		double pricevoucher = 0; // Biến tiền được giảm
		double totalprice = 0 ; // Biến tạm tính
		double thanhtien = 0; // Biến thành tiền
		double tiencoc = 0; // Biến tiền cọc
		double conlai = 0; // Biến tiền còn lại
		int shiftid = 0; // Biến id ca
		List<Shifts> shift = shiftservice.findShiftByName(nameShift); // Ca được chọn ở detail
		for(int i = 0 ; i < shift.size();i++) {
				time = shift.get(i).getStarttime(); // giờ bắt đầu của ca được chọn
				shiftid = shift.get(i).getShiftid(); // id ca được chọn
		}
		// Khởi tạo giờ 17:00
        LocalTime timeToCompare = LocalTime.of(17, 0); // Khởi tạo 17h00
        LocalTime timeToSix = LocalTime.of(06, 0); // Khởi tạo 6h00
        LocalDate currentDate = LocalDate.now(); // Ngày hiện tại
        // Chuyển đổi chuỗi ngày thành đối tượng LocalDate
        LocalDate localDate = LocalDate.parse(dateselect); // chuyển String => LocalDate
        // Định dạng thành chuỗi "dd/MM/yyyy"
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double phuthu = 0; // giá phụ thu
		if (userlogin == null) {
			// Người dùng không tồn tại hoặc thông tin đăng nhập không chính xác
						return "redirect:/sportify/login";
		}else {
			
			 // Kiểm tra đã có tài khoản đăng nhập
			List<Field> fieldListById = fieldservice.findFieldById(idField); // Tìm sân theo id đã chọn
			double giasan = fieldListById.get(0).getPrice(); // giá tiền gốc của sân
			String nameSportype = fieldservice.findNameSporttypeById(idField); // Tìm tên môn thể thao của sân đã chọn
			List<Voucher> magiamgia = voucherService.findAll(); // List mã giảm giá
			if (time.isAfter(timeToCompare) || time.isBefore(timeToSix)) { // Kiểm tra điều kiện phụ thu nếu có
				phuthu = giasan * 30 / 100; // Tiền phụ thu
				totalprice = giasan + phuthu; // Tiền tạm tính
				model.addAttribute("totalprice", totalprice); 
				model.addAttribute("phuthu", phuthu);
			} else { // Không có phụ thu
				totalprice = giasan;
				model.addAttribute("totalprice", totalprice);
				model.addAttribute("phuthu", phuthu);
			}
			if (voucher == null) { // Nếu không có nhập
				thanhtien = totalprice;
				tiencoc = thanhtien * 30 / 100;
				conlai = thanhtien - tiencoc;
				
			} else { // Nếu có voucher
				model.addAttribute("voucher", voucher);
				for (int i = 0; i < magiamgia.size(); i++) { 
					// Trong đoạn mã của bạn
					Date startDateSql = (Date) magiamgia.get(i).getStartdate(); // Ngày bắt đầu mã giảm giá
					LocalDate startDate = startDateSql.toLocalDate(); // Chuyển về LocalDate
					Date endDateSql = (Date) magiamgia.get(i).getEnddate(); // Ngày kết thúc mã giảm giá
					LocalDate endDate = endDateSql.toLocalDate();		// Chuyển về LocalDate
					// Nếu mã giảm giá thỏa mãn điều kiện
					if(voucher.equals(magiamgia.get(i).getVoucherid()) && startDate.isBefore(currentDate) && endDate.isAfter(currentDate)) {
						discountpercent = magiamgia.get(i).getDiscountpercent(); // phần trăm được giảm
						pricevoucher = totalprice * discountpercent / 100; // tiền được giảm
						thanhtien = totalprice - pricevoucher; // thành tiền 
						tiencoc = thanhtien * 30 / 100; // tiền phải cọc đặt sân
						conlai = thanhtien - tiencoc; // tiền còn lại
						model.addAttribute("thongbaovoucher","Mã đã được áp dụng giảm " +discountpercent+"%"); // Thông báo giảm giá
						break;
					}
					else if(!voucher.equals(magiamgia.get(i).getVoucherid())) { // nếu mã giảm giá không hợp lệ
						thanhtien = totalprice - pricevoucher;
						tiencoc = thanhtien * 30 / 100;
						conlai = thanhtien - tiencoc;
						model.addAttribute("thongbaovoucher","Mã không hợp lệ");
					} // Mã giảm giá không đúng thời hạn
					else if(startDate.isAfter(currentDate) || endDate.isBefore(currentDate) && voucher.equals(magiamgia.get(i).getVoucherid())) {
						thanhtien = totalprice - pricevoucher;
						tiencoc = thanhtien * 30 / 100;
						conlai = thanhtien - tiencoc;
						model.addAttribute("thongbaovoucher","Mã không áp dụng hôm nay");				
						break;
					}		
				}
			}
			// Dữ liệu đổ lại giao diện
			model.addAttribute("note", note);
			model.addAttribute("shiftid",shiftid);
			model.addAttribute("pricevoucher", pricevoucher);
			model.addAttribute("conlai", conlai);
			model.addAttribute("tiencoc", tiencoc);
			model.addAttribute("thanhtien", thanhtien);
			model.addAttribute("listvoucher",magiamgia);
			model.addAttribute("nameShift",nameShift);
			model.addAttribute("dateselect",dateselect);
			model.addAttribute("nameSportype",nameSportype);
			model.addAttribute("formattedDate",formattedDate);
			model.addAttribute("fieldListById",fieldListById);
		
		}
		// Gọi trang checkout
		return "user/checkout-dat-san";
	}
	// Kiểm tra giờ trống của sân trong Detail
	@PostMapping("/field/detail/check")
	public String searchShiftDefault(Model model ,@RequestParam("fieldid") int idField  ,@RequestParam("dateInput") String date) {
		dateselect = date; // Ngày được chọn
		List<Field> fieldListById = fieldservice.findFieldById(idField); // Đổ sân theo id lấy giao diện về.
		String nameSportype = fieldservice.findNameSporttypeById(idField); // Tên môn thể thao để hiện thị trong các sân liên quan ở Detail
		String idSporttype = fieldservice.findIdSporttypeById(idField); // Lấy id môn thể thao dựa vào sân đang chọn Detail
		List<Field> fieldListByIdSporttype = fieldservice.findBySporttypeIdlimit3(idSporttype); // Danh sách 3 sân liên quan đến môn thể thao đang xem.
		List<Shifts> shiftsempty = shiftservice.findShiftDate(idField, date); // List ca trống thỏa mản điều kiện đầu vào
		
		LocalTime currentTime = LocalTime.now(); // Lấy giờ hiện tại
		LocalDate currentDate = LocalDate.now(); // lấy ngày hiện tại
		LocalDate selectedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE); // Parse date về kiểu LocalDate
		 if (selectedDate.equals(currentDate)) { // Nếu ngày chọn = ngày hiện tại
			    List<Shifts> shiftsNull = new ArrayList<>(); // Ca trống
			    for (Shifts shift : shiftsempty) {
			        LocalTime shiftStartTime = shift.getStarttime(); // Lấy thời gian bắt đầu của ca làm việc
			        if (shiftStartTime.isAfter(currentTime)) { // Kiểm tra thời gian bắt đầu của ca làm việc
			            shiftsNull.add(shift); // Thêm vào danh sách nếu thỏa mãn điều kiện
			        }
			    }
			    model.addAttribute("shiftsNull", shiftsNull);
			} else { // Đổ tất cả các ca trống
			    model.addAttribute("shiftsNull", shiftsempty);
			}
		// Format yyyy-mm-dd thành dd-mm-yyyy
		LocalDate dateformat = LocalDate.parse(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = dateformat.format(formatter);
		// Dữ liệu hiển thị trong trang Detail
		model.addAttribute("date",date);
		model.addAttribute("formattedDate",formattedDate);
		model.addAttribute("fieldListByIdSporttype",fieldListByIdSporttype);
		model.addAttribute("nameSportype",nameSportype);
		model.addAttribute("fieldListById",fieldListById);
		return "user/san-single";
	}
	// Lịch sử đặt sân
	@GetMapping("/field/profile/historybooking")
	public String viewHistoryField(Model model) {
		List<Object[]> listbooking = bookingservice.getBookingInfoByUsername(userlogin); // Danh sách booking theo tài khoản đang login
		model.addAttribute("listbooking",listbooking);
		return "user/lichsudatsan";
	}
	// Chi tiết lịch sử đặt sân
	@GetMapping("/field/profile/historybooking/detail")
	public String viewDetail(Model model,@RequestParam("bookingId") String bookingId,
            @RequestParam("bookingPrice") double bookingPrice) {
		double giamgia = 0.0; // giảm giá
		double phuthu = 0.0; // Phụ thu
		double tiencoc = 0.0; // tiền cọc	
		double tamtinh = 0.0; // tạm tính
		double conlai = 0.0; // tiền còn lại
		Object[] listbookingdetail = bookingservice.getBookingInfoByBookingDetail(bookingId); // Chi tiết phiếu bookingdetail theo bookingid		
		for (Object object : listbookingdetail) {
		    if (object instanceof Object[]) { // kiểm tra phần tử hiện tại có phải đối tượng object hay không
		        Object[] arrayObject = (Object[]) object; // ép kiểu phần tử hiện tại thành mảng đối tượng
		        if (arrayObject.length >= 9) { // kiểm tra xem mảng đối tượng có ít nhất 9 đối tượng
		            int shiftid = (int) arrayObject[3]; // trích giá trị thứ 3
		            double price = (double) arrayObject[5]; // trích giá trị thứ 5
		            if(shiftid > 10 || shiftid < 3) { // điều kiện có phụ thu
		            	phuthu = price * 30 / 100;
		            	tamtinh = phuthu+price;
		            	giamgia = bookingPrice - tamtinh;
		            	tiencoc = bookingPrice * 30 / 100;
		            	conlai = bookingPrice - tiencoc;
		            }else {
		            	tamtinh = price;
		            	giamgia = bookingPrice - tamtinh;
		            	tiencoc = bookingPrice * 30 / 100;
		            	conlai = bookingPrice - tiencoc;
		            }
		        } 
		    }
		}
		// Đổ dữ liệu lên giao diện bookingdetail
		model.addAttribute("conlai",conlai);
		model.addAttribute("thanhtien",bookingPrice);
		model.addAttribute("phuthu",phuthu);
		model.addAttribute("giamgia",giamgia);
		model.addAttribute("tamtinh",tamtinh);
		model.addAttribute("tiencoc",tiencoc);
		model.addAttribute("listbooking",listbookingdetail);
		return "user/lichsudatsandetail";
	}
}
