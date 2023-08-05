package duan.sportify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import duan.sportify.service.UserService;
import duan.sportify.dao.UserDAO;
import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.entities.Users;
import duan.sportify.entities.Authorized;

import java.util.Optional;

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
	
	@RequestMapping("/sportify/login")
	public String loginForm(Model model) {
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
	public String loginError(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "security/login";
	}

	@RequestMapping("/sportify/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất!");
		return "user/error";
	}

	@RequestMapping("/sportify/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất!");
		return "redirect:/sportify";
	}

	@PostMapping("/sportify/signup/process")
	public String processSignup(Model model,HttpServletRequest request, 
			@RequestParam("firstnameSignUp") String firstnameSignUp,
			@RequestParam("lastnameSignUp") String lastnameSignUp,
			@RequestParam("usernameSignUp") String usernameSignUp,
			@RequestParam("passwordSignUp") String passwordSignUp, 
			@RequestParam("phoneSignUp") String phoneSignUp,
	        @RequestParam("genderSignUp") String genderSignUp,
			@RequestParam("emailSignUp") String emailSignUp) {

	    

		Optional<Users> userOptional = Optional.ofNullable(userService.findById(usernameSignUp));

		// Kiểm tra xem tên đăng nhập đã tồn tại chưa
		if (userOptional.isPresent()) {
			model.addAttribute("message", "Tài khoản đã tồn tại !");
			return "security/signup";
		}else {
		boolean isMale = genderSignUp.equals("male");
		// Tạo một đối tượng User mới
		Users newUser = new Users();
		newUser.setLastname(lastnameSignUp);
		newUser.setFirstname(firstnameSignUp);
		newUser.setUsername(usernameSignUp);
		newUser.setPasswords(passwordSignUp);
		newUser.setPhone(phoneSignUp);
		newUser.setEmail(emailSignUp);
		newUser.setStatus(true);
		newUser.setGender(isMale);
		userDAO.save(newUser);
		
		//Tạo 1 đối tượng Authorized
		Authorized newAuthorized =new Authorized();
		newAuthorized.setUsername(usernameSignUp);
		newAuthorized.setRoleid("R03");
		authorizedDAO.save(newAuthorized);
		model.addAttribute("message", "Bạn đã đăng kí thành công !");
		
		}
		

//		// Lưu người dùng vào cơ sở dữ liệu thông qua UserDAO
//
//		// Đăng nhập ngay sau khi đăng kí thành công (tùy chỉnh theo yêu cầu của bạn)
//		// Chú ý: đây chỉ là một cách đơn giản để giữ phiên đăng nhập. Trong thực tế,
//		// bạn nên thực hiện cơ chế xác thực hợp lệ.
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String username = authentication.getName();
//		HttpSession session = request.getSession();
//		session.setAttribute("username", username);
		return "redirect:/sportify/login/form";
	}

	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/rest/security/authentication")
	public Object getAuthentication(HttpSession session) {
		return session.getAttribute("authentication");
	}
}
