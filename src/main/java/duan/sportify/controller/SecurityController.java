package duan.sportify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Users;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/sportify")
public class SecurityController {

	@Autowired
	UserDAO userDAO;
	
    @GetMapping("/login")
    public String Login() {
        return "security/login";
    }
    
    @PostMapping("/login/check")
    public String checkLogin(Model model,@RequestParam("usernameLogin") String usernameLogin, 
                              @RequestParam("passwordLogin") String passwordLogin,  HttpSession session) {
        // Xử lý đăng nhập với username và password ở đây
        Users users = userDAO.findAcc(usernameLogin,passwordLogin);
        if (users != null) {
            // Thực hiện các thao tác tiếp theo
    		model.addAttribute("username", usernameLogin);
            session.setAttribute("loggedInUser", users);
            return "redirect:/sportify";
        } else {
            // Người dùng không tồn tại hoặc thông tin đăng nhập không chính xác
            return "redirect:/sportify/login";
        }

    }
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa thông tin đăng nhập khỏi session
        session.removeAttribute("loggedInUser");
        
        // Điều hướng người dùng đến trang chủ hoặc trang đăng nhập (tuỳ chọn)
        return "redirect:/sportify";
    }
}
