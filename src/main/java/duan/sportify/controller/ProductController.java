package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import duan.sportify.entities.Products;
import duan.sportify.service.ProductService;

@Controller

public class ProductController {
	@Autowired 
	ProductService productService;
	
	@RequestMapping("/product")
	public String list(Model model) {
	List<Products> list = productService.findAll();
	model.addAttribute("items",list);
		return "user/product";
	}
}
