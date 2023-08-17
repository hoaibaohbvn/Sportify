package duan.sportify.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import duan.sportify.dao.CategoryDAO;
import duan.sportify.dao.ProductDAO;
import duan.sportify.entities.Categories;
import duan.sportify.entities.Products;
import duan.sportify.service.CategoryService;
import duan.sportify.service.ProductService;

@SuppressWarnings("unused")
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
	public String list(Model model, Model model2, @RequestParam("categoryid") Optional<Integer> categoryid) {
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

	@GetMapping("product-single/{productid}")
	public String detail(Model model, @PathVariable("productid") Integer productid) {
		Products product = productService.findById(productid);
		model.addAttribute("product", product);
		return "user/product-single";
	};
	
	@PostMapping("/product/search")
	public String search(Model model, @RequestParam("searchText") String searchText) {
		// Xử lý tìm kiếm và chuyển hướng đến trang /team với query parameter searchText
		if(searchText=="") {
			return "redirect:/sportify/product";
		}
		List<Products> findProduct = productDAO.findByName(searchText);
		model.addAttribute("productList", findProduct);
		if (findProduct.size()>0) {
		    model.addAttribute("FoundMessage", "Tìm thấy " + findProduct.size() + " kết quả tìm kiếm của '" + searchText + "'.");
		}
		if(findProduct.size()==0){
		    model.addAttribute("notFoundMessage", "Không có sản phẩm nào với từ khóa tìm kiếm là '" + searchText + "'.");
		}
//		System.out.println(searchText);
//		System.out.println(findProduct.size());
		return "user/product";
	}

}
