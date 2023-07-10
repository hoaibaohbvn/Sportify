package duan.sportify.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import duan.sportify.dao.CategoryDAO;
import duan.sportify.dao.ProductDAO;
import duan.sportify.entities.Categories;
import duan.sportify.entities.Products;
import duan.sportify.service.CategoryService;
import duan.sportify.service.ProductService;

@Controller
@RequestMapping("/sportify")
public class ProductController {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ProductService productService;
	// phần category
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	CategoryService categoryService;

	@GetMapping("product")
	public String list(Model model, Model model2, @RequestParam("categoryid") Optional<String> categoryid) {
		if (categoryid.isPresent()) {
			List<Products> productList = productService.findByCategoryId(categoryid.get());
			model.addAttribute("productList", productList);
		} else {
			List<Products> productList = productDAO.findAll();
			model.addAttribute("productList", productList);
		}
		//category list
		List<Categories> categoryList = categoryDAO.findAll();
		model2.addAttribute("categoryList", categoryList);
		return "user/product";
	};

//	@GetMapping("product/")
//	public String category(Model model2) {
//		List<Categories> categoryList = categoryDAO.findAll();
//		model2.addAttribute("categoryList", categoryList);
//		return "user/product";
//	};

	@GetMapping("product-single/{productid}")
	public String detail(Model model, @PathVariable("productid") Integer productid) {
		Products product = productService.findById(productid);
		model.addAttribute("product", product);
		return "user/product-single";
	};

}
