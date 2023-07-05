package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import duan.sportify.dao.ProductDAO;
import duan.sportify.entities.Products;
import duan.sportify.service.ProductService;
@Controller
@RequestMapping("/sportify")
public class ProductController {
	@Autowired 
	ProductDAO productDAO;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("product")
	public String list(Model model) {
	List<Products> list = productDAO.findAll();
	model.addAttribute("productList",list);
		return "user/product";
	}
	
	@GetMapping("product-single")
	public String view() {
		return"user/product-single";
	}
	
	@GetMapping("product-single/{productid}")
	public String detail(Model model, @PathVariable("productid") Integer productid) {
		Products product = productService.findById(productid);
		model.addAttribute("product", product);
		return"user/product-single";
	}
}
