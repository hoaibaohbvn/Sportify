package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import duan.sportify.dao.ProductDAO;
import duan.sportify.entities.Products;
@Controller
@RequestMapping("/sportify")
public class ProductController {
	@Autowired 
	ProductDAO productDAO;
	
	@GetMapping("product")
	public String list(Model model) {
	List<Products> list = productDAO.findAll();
	model.addAttribute("items",list);
		return "user/product";
	}
}
