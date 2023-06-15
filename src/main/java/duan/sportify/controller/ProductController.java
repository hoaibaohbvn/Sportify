package duan.sportify.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.dao.ProductDAO;
import duan.sportify.entities.Products;
import duan.sportify.service.ProductService;

@Controller
@RequestMapping("sportify")
public class ProductController {
	
	
	@Autowired
	ProductDAO productDAO;
	
	@GetMapping("product")
	public String viewProduct(Model model) {
		List<Products> productList = productDAO.findAll();
		model.addAttribute("productList", productList);
		return "user/product";
	}
}
