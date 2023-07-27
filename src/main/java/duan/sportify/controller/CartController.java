package duan.sportify.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("sportify")
public class CartController {
	@GetMapping("cart")
	public String viewCart() {
		
		return "user/cart";
	}
	
	@GetMapping("checkout")
	public String checkOutCart() {
		
		return "user/checkout";
	}
}
