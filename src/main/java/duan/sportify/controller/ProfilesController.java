package duan.sportify.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Users;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("sportify")
public class ProfilesController {
	@Autowired
	UserService userService;
	@Autowired
	UserDAO userDAO;
	@Autowired
	AuthorizedService authorzizedservice;
	
	String username = null;
	Users profiles ;
	@GetMapping("/profile")
	public String view(Model model, HttpSession session) {
		// Kiểm tra đăng nhập
		username = (String) session.getAttribute("username");
		String roleuserOnl ;
		if (username != null) {
			profiles = userService.findById(username);
			Authorized role = authorzizedservice.findAllAuthorized(username);
			if(role.getRoleid().equals("R01")) {
				roleuserOnl = "Quản Trị Viên";
			}else if(role.getRoleid().equals("R02")) {
				roleuserOnl = "Nhân viên nội bộ";
			}else {
				roleuserOnl = "Khách hàng";
			}
			model.addAttribute("roleuserOnl",roleuserOnl);
	        model.addAttribute("profiles", profiles);
			return "/user/profiles";
		} else {
			return "redirect:/sportify/login/form";
		}
	}
	
	@PostMapping("/profile/save-profile")
	public String saveProfiles(Model model, 
			HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam("avatarFile") MultipartFile avatarFile,
			@RequestParam("avatarProfile") String avatarProfile,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("passwords") String passwords,
			@RequestParam("address") String address, @RequestParam("gender") String gender,
			@RequestParam("newpassword") String newpassword, @RequestParam("confirmpassword") String confirmpassword
			) throws IOException {
		
		Users updateUser = new Users();
		
		// Xử lý tải lên ảnh
		updateUser.setUsername(username);
		String fileName = avatarFile.getOriginalFilename();

		if (!fileName.isEmpty()) {
		try {
				// Thay đổi đường dẫn tới thư mục lưu trữ ảnh (thay thế "/" bằng đường dẫn thư
			// mục thực tế)
		//	String uploadDir = "C:\\Users\\MSI GAMING\\Documents\\GitHub\\Sportify\\src\\main\\resources\\static\\user\\images";
			String rootDir = System.getProperty("user.dir"); // Đường dẫn đến thư mục gốc của dự án
			String uploadDir = rootDir + "\\src\\main\\resources\\static\\user\\images";
//			String fileName = avatarFile.getOriginalFilename();
	        Path filePath = Paths.get(uploadDir, fileName);
	        Files.write(filePath, avatarFile.getBytes());
	        
			updateUser.setImage(fileName);
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			updateUser.setImage(avatarProfile);
		}
		boolean gioitinh = true;
		if (gender.equals("true")) {
			gioitinh = true;
	    } else if (gender.equals("false")) {
	    	gioitinh = false;
	    }
		
		if (username != null) {
		    if (newpassword.isEmpty() || confirmpassword.isEmpty() || !newpassword.equals(confirmpassword)) {
		        updateUser.setPasswords(passwords); // Sử dụng mật khẩu cũ để cập nhật
		    } else {
		        updateUser.setPasswords(newpassword); // Cập nhật mật khẩu mới
		    }
		} else {
		    // Xử lý khi không tìm thấy người dùng
		}
		

		
		updateUser.setFirstname(firstname);
		updateUser.setLastname(lastname);
		updateUser.setPhone(phone);
		updateUser.setGender(gioitinh);
		updateUser.setEmail(email);
		updateUser.setAddress(address);
		updateUser.setStatus(true);
		userService.update(updateUser);
		redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
		model.addAttribute("profiles", profiles);
		
		
		return "redirect:/sportify/profile";
	}
}
