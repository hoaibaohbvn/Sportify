package duan.sportify.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import duan.sportify.service.OrderService;

@Controller
@RequestMapping("sportify")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@GetMapping("/order/cart")
	public String viewCart() {
		
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
		return "user/orderDetail";
	}
}
