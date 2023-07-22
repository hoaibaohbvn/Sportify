package duan.sportify.controller;

import java.awt.SystemColor;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.entities.Users;
import duan.sportify.service.UserService;
import duan.sportify.dao.TeamDAO;
import duan.sportify.dao.TeamDetailDAO;
import duan.sportify.entities.Sporttype;
import duan.sportify.entities.Teamdetails;
import duan.sportify.entities.Teams;
import duan.sportify.service.SportTypeService;
import duan.sportify.service.TeamService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/sportify")
public class TeamController {

	@Autowired
	private TeamDAO teamdao;

	@Autowired
	UserService userService;
	
	@Autowired
	TeamDetailDAO detailDAO;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	SportTypeService sportTypeService;

	// Đỗ danh Sách bộ Lọc
	@ModelAttribute("sporttypeList")
	public List<Sporttype> getSporttypeList() {
		return sportTypeService.findAll();
	}

	// Đỗ toàn bộ dữ liệu liên quan đến team
	@GetMapping("/team")
	public String viewTeam(Model model, HttpSession session,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
			@RequestParam(value = "sporttypeid", required = false, defaultValue = "") String sporttypeid,
			Pageable pageable) {
		
		// Thực hiện show Team
		Page<Object[]> teamPage;

		int searchTextLength = searchText.length();// Kiểm tra xem ng dùng có nhập vào ô tìm kiếm không
		int sporttypeidLength = sporttypeid.length();// Kiểm tra xem ng dùng có chọn vào bộ lọc không

		if (searchTextLength > 0 && sporttypeidLength == 0) {// Kiểm tra nếu ng dùng có nhập vào ô tìm kiếm thì sẽ dỗ dữ
			
			// liệu theo SearchTeam
			teamPage = teamdao.SearchTeam(searchText, pageable);
			
		} else if (searchTextLength == 0 && sporttypeidLength > 0) {// Kiểm tra nếu ng dùng chọn vào lọc thì sẽ dỗ dữ
																	// liệu theo FilterTeam
			teamPage = teamdao.FilterTeam(sporttypeid, pageable);
		} else {
			teamPage = teamdao.findAllTeam(pageable);// Còn không nhập hay chọn gì thì sẽ đỗ toàn bộ
		}
		
		

		List<Object[]> teams = teamPage.getContent();
		
		model.addAttribute("team", teams);
		model.addAttribute("page", teamPage);
		model.addAttribute("searchText", searchText);
		model.addAttribute("sporttypeid", sporttypeid);
//		model.addAttribute("username", username);

		// Kiểm tra để hiển thị thông báo
		if (!searchText.isEmpty() && teamPage.getTotalElements() > 0) {
			model.addAttribute("FoundMessage",
					"Tìm thấy " + teamPage.getTotalElements() + " kết quả tìm kiếm của '" + searchText + "'.");
		}
		if (teamPage.getTotalElements() == 0) {
			model.addAttribute("notFoundMessage",
					"Tìm thấy " + teamPage.getTotalElements() + " kết quả tìm kiếm của '" + searchText + "'.");
		}

		return "user/doi";
	}

	@PostMapping("/team/search")
	public String search(Model model, @RequestParam("searchText") String searchText, Pageable pageable) {
		// Xử lý tìm kiếm và chuyển hướng đến trang /team với query parameter searchText
		if (searchText == "") {
			return "redirect:/sportify/team";
		}
		return "redirect:/sportify/team?searchText="+searchText;
	}

	@GetMapping("/team/{sporttypeid}")
	public String handleSportifyTeam(Model model, @PathVariable("sporttypeid") String sporttypeid) {
		return "redirect:/sportify/team?sporttypeid=" + sporttypeid;
	}

	@GetMapping("team/teamdetail/{teamId}/exit")
	public String exitteam(Model model, @PathVariable("teamId") Integer teamId,HttpSession session, RedirectAttributes redirectAttributes) {
		// kiểm tra đăng nhập
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		String username = loggedInUser.getUsername();
		detailDAO.deleteByUsernameAndTeamId(username,teamId);
        redirectAttributes.addFlashAttribute("message", "Bạn đã rời thành công!");
		return "redirect:/sportify/team";
	}
	
	// Điều hướng đến trang chi tiết
	@GetMapping("team/teamdetail/{teamId}")
	public String teamdetail(Model model, @PathVariable("teamId") Integer teamId, HttpSession session, RedirectAttributes redirectAttributes) {
		// kiểm tra đăng nhập
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
	    model.addAttribute("teamId", teamId);
		// Người dùng đã đăng nhập
		if (loggedInUser != null) {

			// Người dùng tồn tại và thông tin đăng nhập chính xác
			// Kiểm tra user đã tồn tại trong team
			String username = loggedInUser.getUsername();
			Teamdetails checkTeamUser = detailDAO.checkTeamUser(username, teamId);
			model.addAttribute("checkTeamUser", checkTeamUser);

			// Lấy số lượng thành viên trong team
			Teams team = teamService.findById(teamId);
			int quantity = team.getQuantity();
			// Đếm số lượng thành viên trong teamDetail
			Integer countMembers = detailDAO.countUser(teamId);
//			System.out.println(countMembers);

			// username đã có trong team
			if (checkTeamUser != null) {
				// Show thông tin + thành viên trong team
				List<Object[]> listall = detailDAO.findByIdTeam(teamId);
				List<Object[]> userTeam = detailDAO.findUserByIdTeam(teamId);
				model.addAttribute("team", listall);
				model.addAttribute("user", userTeam);
				String infouser = checkTeamUser.getInfouser();
				model.addAttribute("infouser", infouser);
				return "user/team-single";

				// Người dùng chưa có trong team
			} else if (countMembers < quantity) {

				// Add vô team
				Teamdetails newUser = new Teamdetails();
				newUser.setJoindate(LocalDate.now());
				newUser.setTeamid(teamId);
				newUser.setUsername(username);
				detailDAO.save(newUser);

				// show team chuyển hướng đến detail
				// Show thông tin + thành viên trong team
				List<Object[]> listall = detailDAO.findByIdTeam(teamId);
				List<Object[]> userTeam = detailDAO.findUserByIdTeam(teamId);
				model.addAttribute("team", listall);
				model.addAttribute("user", userTeam);
				// Thêm thông báo
				redirectAttributes.addFlashAttribute("message", "Bạn đã tham gia thành công!");
	            return "redirect:/sportify/team/teamdetail/" + teamId; // Chuyển hướng đến trang team-single
			} else {
	            redirectAttributes.addFlashAttribute("message1", "Team đã đủ thành viên !");
				return "redirect:/sportify/team";
			}

			// Chưa đăng nhập trả về login
		} else {
			// Người dùng không tồn tại hoặc thông tin đăng nhập không chính xác
			return "redirect:/sportify/login";
		}
	}

	@PostMapping("/team/teamdetail/updateinfoUser")
	public String updateUser(Model model,@RequestParam("description") String description,@ModelAttribute("teamId") Integer teamId,HttpSession session,RedirectAttributes redirectAttributes) {
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		System.out.print(description);
		// Kiểm tra user đã tồn tại trong team
		String username = loggedInUser.getUsername();
		Teamdetails checkTeamUser = detailDAO.checkTeamUser(username, teamId);
		if(checkTeamUser != null) {
			checkTeamUser.setInfouser(description);
			detailDAO.save(checkTeamUser);
			redirectAttributes.addFlashAttribute("message", "Thêm giới thiệu thành công !");
		}else {
			redirectAttributes.addFlashAttribute("message1", "Thêm giới thiệu thất bại !");

		}
		return "redirect:/sportify/team/teamdetail/" + teamId;
	}
	
	@GetMapping("/team/teamdetail/edit/{teamId}")
	public String updateUser1(Model model, @PathVariable("teamId") Integer teamId,HttpSession session) {
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		// Kiểm tra user đã tồn tại trong team
		String username = loggedInUser.getUsername();
		Teamdetails checkTeamUser = detailDAO.checkTeamUser(username, teamId);
		if(checkTeamUser != null) {
			checkTeamUser.setInfouser(null);
			detailDAO.save(checkTeamUser);
		}
		return "redirect:/sportify/team/teamdetail/" + teamId;
	}
	
	
}
