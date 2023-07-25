package duan.sportify.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Users;
import duan.sportify.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("sportify")
public class ProfilesController {
	@Autowired
	UserService userService;
	@Autowired
	UserDAO userDAO;

	@GetMapping("/profile")
	public String view(Model model, HttpSession session) {
		// Kiểm tra đăng nhập
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		if (loggedInUser != null) {
			String userName = loggedInUser.getUsername();
			Users profiles = userService.findById(userName);
			model.addAttribute("prodiles", profiles);
			return "/user/profiles";
		} else {
			return "redirect:/sportify/login";
		}
	}

	@PostMapping("/profile/save-profile")
	public String saveProfiles(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam("avatarFile") MultipartFile avatarFile, @RequestParam String firstname,
			@RequestParam String lastname, @RequestParam String passwords, @RequestParam String phone,
			@RequestParam String email, @RequestParam Boolean gender, @RequestParam String address) throws IOException {

		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		String userName = loggedInUser.getUsername();
		Users updateUser = userService.findById(userName);
		// Xử lý tải lên ảnh
		if (!avatarFile.isEmpty()) {
			try {
				// Thay đổi đường dẫn tới thư mục lưu trữ ảnh (thay thế "/" bằng đường dẫn thư
				// mục thực tế)
				String uploadDir = "C:\\Users\\MSI GAMING\\Documents\\GitHub\\Sportify\\src\\main\\resources\\static\\user\\images";
				String fileName = avatarFile.getOriginalFilename();
				Path filePath = Paths.get(uploadDir, fileName);
				Files.write(filePath, avatarFile.getBytes());
				updateUser.setImage(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		updateUser.setFirstname(firstname);
		updateUser.setLastname(lastname);
		updateUser.setPasswords(passwords);
		updateUser.setPhone(phone);
		updateUser.setEmail(email);
		updateUser.setGender(gender);
		updateUser.setAddress(address);
		userDAO.save(updateUser);
		redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
		return "redirect:/sportify/profile";
	}
}
