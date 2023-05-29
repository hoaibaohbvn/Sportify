package duan.sportify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class TestFEController {
	// view trang home
	@RequestMapping("/")
	public String viewHome() {
		return "user/index";
	}

	// view trang about
	@RequestMapping("about")
	public String viewAbout() {
		return "user/about";
	}

	// view trang blog
	@RequestMapping("blog")
	public String viewBlog() {
		return "user/blog";
	}

	// view trang blog-single
	@RequestMapping("blog-single")
	public String viewBlogSingle() {
		return "user/blog-single";
	}

	// view trang cart
	@RequestMapping("cart")
	public String viewCart() {
		return "user/cart";
	}

	// view trang checkout
	@RequestMapping("checkout")
	public String viewCheckout() {
		return "user/checkout";
	}

	// view trang contact
	@RequestMapping("contact")
	public String viewContact() {
		return "user/contact";
	}

	// view trang Đội
	@RequestMapping("doi")
	public String viewDoi() {
		return "user/doi";
	}

	// view trang product
	@RequestMapping("product")
	public String viewProduct() {
		return "user/product";
	}

	// view trang product-single
	@RequestMapping("product-single")
	public String viewProductSingle() {
		return "user/product-single";
	}

	// view trang san
	@RequestMapping("san")
	public String viewSan() {
		return "user/san";
	}
}
