package duan.sportify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.ForeignKey;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;




import duan.sportify.VNPayConfig;
import duan.sportify.DTO.PaymentResDTO;
import duan.sportify.entities.Bookingdetails;
import duan.sportify.entities.Bookings;
import duan.sportify.entities.Users;
import duan.sportify.service.BookingDetailService;
import duan.sportify.service.BookingService;
import duan.sportify.service.UserService;
import net.bytebuddy.asm.Advice.OffsetMapping.Target.ForArray;

@Controller
@RequestMapping("sportify")
public class PaymentVNPayController {
	private RestTemplate restTemplate = new RestTemplate();
	String ipAddress = null;
	String paymentUrl;
	@Autowired
	UserService userservice;
	@Autowired
	BookingService bookingservice;
    @Autowired
    BookingDetailService bookingdetailservice;
    @Autowired
    public void ApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public static String getIpAddressFromJsonString(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            return (String) jsonObject.get("ip");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping("/getIp")
    public String getIpAddress() {
        String apiUrl = "https://api.ipify.org?format=json";

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseData = response.getBody();
             ipAddress = getIpAddressFromJsonString(responseData);
            return "IP Address: " + ipAddress;
        } else {
            return "Không thể lấy dữ liệu từ API.";
        }
    }
    Map<String, String> vnp_Params = new HashMap<>();
    String userlogin = null;
    String phone = null;
    Users userOn = null;
    int bookingidNew = 0;
    Bookings savebooking ;
    Bookingdetails savebookingdetail ;
    @PostMapping("/getIp/create")
	public RedirectView createPayment(
			@RequestParam("amount") String inputMoney, HttpServletRequest request,
			@RequestParam("thanhtien") Double bookingprice, @RequestParam("phone") String phone,
			@RequestParam("note") String note, @RequestParam("shiftid") int shiftid,
			@RequestParam("fieldid") int fieldid, @RequestParam("playdate") String playdateSt,
			@RequestParam("pricefield") Double priceField
			) throws Throwable {
			Bookings newbooking = new Bookings();
			Bookingdetails newbookingdetail = new Bookingdetails();
			
    		bookingidNew = bookingservice.countBooking();

			userlogin = (String) request.getSession().getAttribute("username");	
			Date currentDate = new Date();
		    String bookingstatus = "Đã Cọc";
		    
		    String pattern = "yyyy-MM-dd"; // Mẫu định dạng của chuỗi ngày tháng
	        SimpleDateFormat sdfDate = new SimpleDateFormat(pattern);
	        Date playdate = sdfDate.parse(playdateSt);
	        
		    newbooking.setUsername(userlogin);
		    newbooking.setBookingdate(currentDate);
		    newbooking.setBookingprice(bookingprice);
		    newbooking.setPhone(phone);
		    newbooking.setNote(note);
		    newbooking.setBookingstatus(bookingstatus);
		    
		    newbookingdetail.setBookingid(bookingidNew + 1);
		    newbookingdetail.setShiftid(shiftid);
		    newbookingdetail.setPlaydate(playdate);
		    newbookingdetail.setFieldid(fieldid);
		    newbookingdetail.setPrice(priceField);
		    savebooking = newbooking ;
		    savebookingdetail = newbookingdetail;
		 getIpAddress();
		
		 int amount = (int) Double.parseDouble(inputMoney)* 100;

		String vnp_TxnRef = VNPayConfig.getRandomNumber(8);

		
		vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
		vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
		vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
		vnp_Params.put("vnp_IpAddr", ipAddress);
		vnp_Params.put("vnp_OrderType", "250000");

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List<String> fieldNames = new ArrayList<String>(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator<String> itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

		PaymentResDTO paymentResDTO = new PaymentResDTO();
		paymentResDTO.setStatus("Ok");
		paymentResDTO.setMessage("Successfully");
		paymentResDTO.setURL(paymentUrl);
//		return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
		return new RedirectView(paymentUrl);
	}
	
	@GetMapping("/checkoutResult")
	public String viewResult(Model model, HttpServletRequest request) {
		// Begin process return from VNPAY
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        
        String signValue = VNPayConfig.hashAllFields(fields);
       
        // Add transactionStatus to the model
        String transactionStatus;
        
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                transactionStatus = "Thành công";
                try {
                	bookingservice.create(savebooking);
                	bookingdetailservice.create(savebookingdetail);
                }catch (Exception e) {
					e.printStackTrace();
				}
                
            } else {
                transactionStatus = "Không thành công";
            }
         // Get the vnp_Amount from the fields map
            String vnp_AmountStr = fields.get("vnp_Amount");

            // Parse the vnp_Amount to double
            double vnp_Amount = Double.parseDouble(vnp_AmountStr);

            // Divide by 100 to get the actual monetary value
            double amountInVND = vnp_Amount / 100;
           
        model.addAttribute("amountInVND",amountInVND);
        model.addAttribute("transactionStatus", transactionStatus);
		return "user/ketquathanhtoan";
	}
	
}
