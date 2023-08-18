package duan.sportify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import duan.sportify.service.MailerService;
import duan.sportify.service.UserService;
import duan.sportify.dao.UserDAO;
import duan.sportify.DTO.MailInfo;
import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.entities.Users;

import duan.sportify.entities.Authorized;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class SecurityController {
	@Autowired
	UserService userService;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	AuthorizedDAO authorizedDAO;
		
	List<Users> listUser = new ArrayList<>();
	@RequestMapping("/sportify/login")
    public String loginForm(Model model) {
		 listUser = userService.findAll();
		 model.addAttribute("listUser",listUser);
		return "security/login";
	}
	@RequestMapping("/sportify/signup")
	public String signupForm(Model model) {
		return "security/signup";
	}

	@RequestMapping("/sportify/login/success")
	public String loginSuccess(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		session.setAttribute("username", username);
		return "redirect:/sportify";
	}

	@RequestMapping("/sportify/login/error")
	public String loginError(Model model, @RequestParam(name = "error", required = false) String error) {
	    		model.addAttribute("message", "Thông tin đăng nhập không hợp lệ hoặc tài khoản của bạn đã bị khóa");
			    return "security/login";
	}

	@RequestMapping("/sportify/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất!");
		return "user/error";
	}

	@RequestMapping("/sportify/logoff/success")
	public String logoffSuccess(Model model, HttpSession session) {
		model.addAttribute("message", "Bạn đã đăng xuất!");
        session.removeAttribute("username"); //Xóa Session người dùng đăng nhập
		return "redirect:/sportify";
	}

	@PostMapping("/sportify/signup/process")
	public String processSignup(Model model,HttpServletRequest request, HttpSession session,
			@RequestParam("firstnameSignUp") String firstnameSignUp,
			@RequestParam("lastnameSignUp") String lastnameSignUp,
			@RequestParam("usernameSignUp") String usernameSignUp,
			@RequestParam("passwordSignUp") String passwordSignUp, 
			@RequestParam("phoneSignUp") String phoneSignUp,
	        @RequestParam("genderSignUp") String genderSignUp,
	        @RequestParam("addressSignUp") String addressSignUp,
			@RequestParam("emailSignUp") String emailSignUp) {

//		Optional<Users> userOptional = Optional.ofNullable(userService.findById(usernameSignUp));
		Users userOptional = userDAO.findAcc(usernameSignUp);
		// Kiểm tra xem tên đăng nhập đã tồn tại chưa
		if (userOptional !=null ) {
			model.addAttribute("message", "Tài khoản đã tồn tại !");
			return "security/signup";
		}
		boolean isMale = genderSignUp.equals("male");
		// Tạo một đối tượng User mới
		Users newUser = new Users();
		newUser.setLastname(lastnameSignUp);
		newUser.setFirstname(firstnameSignUp);
		newUser.setUsername(usernameSignUp);
		newUser.setPasswords(passwordSignUp);
		newUser.setPhone(phoneSignUp);
		newUser.setEmail(emailSignUp);
		newUser.setAddress(addressSignUp);
		newUser.setStatus(isMale);
		newUser.setGender(true);
		userDAO.save(newUser);
		//Tạo 1 đối tượng Authorized
		Authorized newAuthorized =new Authorized();
		newAuthorized.setUsername(usernameSignUp);
		newAuthorized.setRoleid("R03");
		authorizedDAO.save(newAuthorized);
//		System.out.print("Bạn đã đăng kí thành công !");
		return "redirect:/sportify/login"; // hoặc trang bạn muốn
	}


	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/rest/security/authentication")
	public Object getAuthentication(HttpSession session) {
		return session.getAttribute("authentication");
	}
}
