package duan.sportify.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.DTO.MailInfo;
import duan.sportify.entities.Users;
import duan.sportify.service.MailerService;
import duan.sportify.service.UserService;
import duan.sportify.utils.mail_Contrain;

@Controller
@RequestMapping("/sportify")
public class ForgotpassController {
	@Autowired
	MailerService mailer;
	@Autowired
	UserService userService;
	public static int generateRandomCode(int length) {
        Random random = new Random();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return random.nextInt(max - min + 1) + min;
    }
	int randomCode;
	String username ;
	String email;
	@PostMapping("/forgotpassword")
	public String forgotpassword(Model model, HttpServletRequest request,
			@RequestParam("usernameforgot") String usernameinput,
			@RequestParam("emailforgot") String emailinput
			) throws IOException {
		username = usernameinput;
		email = emailinput;
		randomCode = generateRandomCode(6);
        
		String title = "Xác thực thay đổi mật khẩu";
		
		try {
			mailer.send(emailinput,title,mail_Contrain.mailChangePass(randomCode, usernameinput));
			model.addAttribute("thongbao","Mã OTP đã được qua Mail của bạn");

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return "security/checkCodeRandom";
	}
	@PostMapping("/checkOTP")
	public String checkOTP(Model model, HttpServletRequest request,
								@RequestParam("codeverification") int OTPinput
								) {
			if(OTPinput == randomCode) {
				return "security/newpassword";

			}else {
				model.addAttribute("message","Mã OTP không đúng, vui lòng thử lại...");
				return "security/checkCodeRandom";
			}
	}
	@PostMapping("/changePassword")
	public String changePassword(Model model, HttpServletRequest request, 
								@RequestParam("newpassword") String newpassword,
								@RequestParam("confirmpassword") String confirmpassword,
								@Valid Users user, BindingResult result 
								) {
			Users userChange = userService.findById(username);

			if (result.hasErrors()) {
//	            return "security/login"; // Return the form view to show validation errors
	        }
			user.setUsername(username);
			if(newpassword.equals(confirmpassword)) {
				user.setPasswords(newpassword);
			}
			user.setFirstname(userChange.getFirstname());
			user.setLastname(userChange.getLastname());
			user.setEmail(email);
			user.setPhone(userChange.getPhone());
			user.setAddress(userChange.getAddress());
			user.setImage(userChange.getImage());
			user.setGender(userChange.getGender());
			user.setStatus(userChange.getStatus());
			userService.update(user);
			model.addAttribute("thongbao","Đổi mật khẩu thành công");
			return "security/login";
	}
}
