package duan.sportify.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
								@RequestParam("confirmpassword") String confirmpassword
								) {
			Users userChange = userService.findById(username);
			Users updateUser = new Users();
			updateUser.setUsername(username);
			if(newpassword.equals(confirmpassword)) {
				updateUser.setPasswords(newpassword);
			}
			updateUser.setFirstname(userChange.getFirstname());
			updateUser.setLastname(userChange.getLastname());
			updateUser.setEmail(email);
			updateUser.setPhone(userChange.getPhone());
			updateUser.setAddress(userChange.getAddress());
			updateUser.setImage(userChange.getImage());
			updateUser.setGender(userChange.getGender());
			updateUser.setStatus(userChange.getStatus());
			userService.update(updateUser);
			model.addAttribute("thongbao","Đổi mật khẩu thành công");
			return "security/login";
	}
}
