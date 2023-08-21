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
	MailerService mailer; // Service gửi mail
	@Autowired
	UserService userService; // Service user
	// Hàm random OTP 6 ký tự
	public static int generateRandomCode(int length) {
        Random random = new Random();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return random.nextInt(max - min + 1) + min;
    }
	int randomCode; // mã OTP để set body mail
	String username ; // user name để set body mail
	String email; // email cần gửi OTP
	// Thực hiện quên mật khẩu
	@PostMapping("/forgotpassword")
	public String forgotpassword(Model model, HttpServletRequest request,
			@RequestParam("usernameforgot") String usernameinput,
			@RequestParam("emailforgot") String emailinput
			) throws IOException {
		username = usernameinput; // Ráng username nhập vào cho biến toàn cục
		email = emailinput; // Ráng email nhập vào cho biến toàn cục
		randomCode = generateRandomCode(6); // sinh ra 6 số ngẫu nhiên
		String title = "Xác thực thay đổi mật khẩu"; // Title gửi mail	
		try {
			mailer.send(emailinput,title,mail_Contrain.mailChangePass(randomCode, usernameinput)); // Thực hiện gửi mail
			model.addAttribute("thongbao","Mã OTP đã được qua Mail của bạn"); // Thông báo
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		// Gọi trang check OTP
		return "security/checkCodeRandom";
	}
	// Thực hiện kiểm tra OTP hợp lệ hay không
	@PostMapping("/checkOTP")
	public String checkOTP(Model model, HttpServletRequest request,
								@RequestParam("codeverification") int OTPinput
								) {
			if(OTPinput == randomCode){ // Nếu OTP hợp lệ
				return "security/newpassword";

			}else { // Không hợp lệ
				model.addAttribute("message","Mã OTP không đúng, vui lòng thử lại...");
				return "security/checkCodeRandom";
			}
	}
	// Đặt lại mật khẩu mới
	@PostMapping("/changePassword")
	public String changePassword(Model model, HttpServletRequest request, 
								@RequestParam("newpassword") String newpassword,
								@RequestParam("confirmpassword") String confirmpassword,
								@Valid Users user, BindingResult result 
								) {
			Users userChange = userService.findById(username); // Tìm tài khoản cần đổi mật khẩu
			if (result.hasErrors()) {
	        }
			user.setUsername(username); // Lưu giá trị username cũ
			if(newpassword.equals(confirmpassword)) { // nếu mật khẩu và xác nhận mật khẩu giống nhau 
				user.setPasswords(newpassword); // Set mật khẩu mới cho tài khoản
			}
			// Lưu đối tượng user cần đổi mật khẩu
			user.setFirstname(userChange.getFirstname());
			user.setLastname(userChange.getLastname());
			user.setEmail(email);
			user.setPhone(userChange.getPhone());
			user.setAddress(userChange.getAddress());
			user.setImage(userChange.getImage());
			user.setGender(userChange.getGender());
			user.setStatus(userChange.getStatus());
			userService.update(user);
			model.addAttribute("thongbao","Đổi mật khẩu thành công"); // Thông báo
			// Chuyển về trang Login
			return "security/login";
	}
}
