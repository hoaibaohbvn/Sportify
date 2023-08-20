package duan.sportify.controller;

import java.util.Arrays;
import java.util.List;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.User;
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

import duan.sportify.dao.ContactDAO;
import duan.sportify.entities.Contacts;
import duan.sportify.service.UserService;

@Controller
@RequestMapping("/sportify")
public class ContactController {
	@Autowired
	ContactDAO contactDAO;
	@Autowired
	UserService userService;
	String userlogin;

	@GetMapping("contact")
	public String view(Model model, HttpServletRequest request) {
		userlogin = (String) request.getSession().getAttribute("username");
		model.addAttribute("contacts", new Contacts());
		return "user/contact";
	}

	@PostMapping("/submit-contact")
	public String processContactForm(Model model, HttpSession session, RedirectAttributes redirectAttributes,
			@RequestParam("contactType") String contactType, @RequestParam String title,
			@RequestParam String meesagecontact, @Valid Contacts contacts, BindingResult result) {
		if (result.hasErrors()) {
			return "user/contact"; // Return the form view to show validation errors
		}
//		LocalDateTime lastSubmissionTime = getLastSubmissionTimeForUser(userlogin); // Get the last submission time for the user from your storage
//		LocalDateTime currentTime = LocalDateTime.now();
//		Duration timeSinceLastSubmission = Duration.between(lastSubmissionTime, currentTime);
//
//		if (timeSinceLastSubmission.toHours() < 24) {
//			// User cannot submit a new response within 24 hours
//			redirectAttributes.addFlashAttribute("message", "Bạn chỉ có thể gửi phản hồi mới sau 24 giờ.");
//			return "redirect:/sportify/contact";
//		}
		List<String> listcontacted = contactDAO.contactedInDay();
		String loggedInUsername = userlogin;
		if(listcontacted.contains(loggedInUsername)) {
			 redirectAttributes.addFlashAttribute("message", "Để hạn chế spam. Bạn chỉ có thể gửi phản hồi mới vào ngày tiếp theo.");
		        return "redirect:/sportify/contact";
		}else {
		// Lưu thông tin
			contacts.setUsername(userlogin);
			contacts.setTitle(title);
			contacts.setMeesagecontact(meesagecontact);
			contacts.setCategory(contactType);
			LocalDate localDate = LocalDate.now();
			Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			contacts.setDatecontact(date);
			contactDAO.save(contacts);
			redirectAttributes.addFlashAttribute("message", "Sportify Cảm ơn bạn đã phản hồi");
			return "redirect:/sportify/contact"; // Chuyển đến trang contact khi đã lưu thành công.
		}
	}

}
