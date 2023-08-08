package duan.sportify.controller;



import java.sql.Date;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import duan.sportify.entities.Voucher;
import duan.sportify.service.OrderService;
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
	
	@GetMapping("/order/cart")
	public String viewCart(Model model, @RequestParam("voucherid") Optional<String> voucherid) {
		//tìm dữ liệu voucher
		int discountpercent = 0;
		
		if (voucherid.isPresent()) {
			List<Voucher> voucherList = voucherService.findByVoucherId(voucherid.get());
			model.addAttribute("voucherList", voucherList);
		}
		return "user/cart";
	}
	
	@GetMapping("/order/checkout")
	public String checkOutCart() {
		
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
//		System.out.println( id);
//		return "redirect:/sportify/order/historyList";
		
		Orders updateOrder = orderService.findById(id);
		updateOrder.setOrderstatus("Hủy Đặt");
		orderDAO.save(updateOrder);
		return "redirect:/sportify/order/historyList";
	}
}
