package duan.sportify.controller;



import java.sql.Date;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.dao.OrderDAO;
import duan.sportify.dao.VoucherDAO;
import duan.sportify.entities.Orders;
import duan.sportify.entities.Products;
import duan.sportify.entities.Voucher;
import duan.sportify.service.OrderService;
import duan.sportify.service.UserService;
import duan.sportify.service.VoucherService;

@Controller
@RequestMapping("sportify")
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	VoucherDAO voucherDAO;
	@Autowired
	VoucherService voucherService;
	@Autowired
	UserService userService;
	
	String userlogin = null;
	
	@GetMapping("/order/cart")
	public String viewCart(Model model, HttpServletRequest request) {
		userlogin = (String) request.getSession().getAttribute("username");
		if (userlogin == null) {
			return "security/login";
		} else {
			return "user/cart";
		}
		
	}
	
	@GetMapping("/order/checkout")
	public String checkOutCart(Model model, HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		model.addAttribute("users", userService.findById(username));
		return "user/checkout";
	}
	
	@GetMapping("/order/historyList")
	public String list(Model model, HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		model.addAttribute("orders", orderService.findByUsername(username));
		return "user/orderList";
	}
	
	@GetMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("order", orderService.findById(id));
		model.addAttribute("id", id);
		return "user/orderDetail";
	}
	
	@GetMapping("/order/detail/cancelOrder/{id}")
	public String cancelOrder(Model model, @ModelAttribute("id") Integer id, RedirectAttributes redirectAttributes) {
		Orders updateOrder = orderService.findById(id);
		updateOrder.setOrderstatus("Hủy Đặt");
		orderDAO.save(updateOrder);
		return "redirect:/sportify/order/historyList";
	}
	
	//tìm kiếm voucher
	@PostMapping("/order/cart/voucher")
	public String cartVoucher(Model model, @RequestParam("voucherId") String voucherId) {
		if(voucherId == "") {
			return "redirect:/sportify/order/cart";
		}
		List<Voucher> voucherList = voucherDAO.findByVoucherId(voucherId);
		model.addAttribute("voucherList", voucherList);
		Integer discountPercent;
		if (voucherList.size()>0) {
			
			for (int i = 0; i < voucherList.size(); i++) {
				Date startDateSql = (Date) voucherList.get(i).getStartdate();
				LocalDate startDate = startDateSql.toLocalDate();
				Date endDateSql = (Date) voucherList.get(i).getEnddate();
				LocalDate endDate = endDateSql.toLocalDate();
				LocalDate currentDate = LocalDate.now();
				if(startDate.isBefore(currentDate) && endDate.isAfter(currentDate)) {
					discountPercent = voucherList.get(i).getDiscountpercent();
					model.addAttribute("discountPercent", discountPercent);
					model.addAttribute("voucherMsg", "Đã áp dụng thành công voucher '" + voucherId + "'. Bạn được giảm " + discountPercent + "% .");
					break;
				} else {
					discountPercent = 0;
					model.addAttribute("discountPercent", discountPercent);
					model.addAttribute("voucherMsg", "Voucher '" + voucherId + "' đã hết hạn sử dụng");
				}
				
			}
		}
		if(voucherList.size()==0){
			discountPercent = 0;
			model.addAttribute("discountPercent", discountPercent);
		    model.addAttribute("voucherMsg", "Không tìm thấy voucher '" + voucherId + "'.");
		}
		return "user/cart";
	}
	
}
