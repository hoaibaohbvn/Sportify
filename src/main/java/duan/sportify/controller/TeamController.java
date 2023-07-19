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

import duan.sportify.entities.Users;
import duan.sportify.service.UserService;
import duan.sportify.dao.TeamDAO;
import duan.sportify.entities.Teams;
import duan.sportify.dao.TeamDetailDAO;
import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Sporttype;
import duan.sportify.service.SportTypeService;
import duan.sportify.service.TeamService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sportify")
public class TeamController {

	@Autowired
	private TeamDAO teamdao;

	@Autowired
	UserService userService;

	@Autowired
	SportTypeService sportTypeService;

	@Autowired
	TeamDetailDAO detailDAO;

	@Autowired
	UserDAO userDAO;

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

	// tìm kiếm team theo tên
	@PostMapping("/team/search")
	public String search(Model model, @RequestParam("searchText") String searchText, Pageable pageable) {
		// Xử lý tìm kiếm và chuyển hướng đến trang /team với query parameter searchText
		if (searchText == "") {
			return "redirect:/sportify/team";
		}
		return "redirect:/sportify/team?searchText=" + searchText;
	}

	// Điều hướng lọc
	@GetMapping("/team/{sporttypeid}")
	public String handleSportifyTeam(Model model, @PathVariable("sporttypeid") String sporttypeid) {
		// Xử lý Lọc và chuyển hướng đến trang /team với query parameter sporttypeid
		return "redirect:/sportify/team?sporttypeid=" + sporttypeid;
	}
@Autowired 
Teams teams;
@Autowired
TeamService teamService;

	// Điều hướng đến trang chi tiết
	@GetMapping("team/teamdetail/{teamId}")
	public String teamdetail(Model model, @PathVariable("teamId") String teamId, HttpSession session) {
		// kiểm tra đăng nhập
		Users loggedInUser = (Users) session.getAttribute("loggedInUser");
		//Người dùng đã đăng nhập
		if (loggedInUser != null) {
			// Người dùng tồn tại và thông tin đăng nhập chính xác
			// Kiểm tra user đã tồn tại trong team
			String username = loggedInUser.getUsername();
			Users user = detailDAO.checkTeamUser(username, teamId);
			Teams teams = teamService.findById(teamId);
			//username đã có trong team
			if (user != null) {
				// Show thông tin + thành viên trong team
				List<Object[]> listall = detailDAO.findByIdTeam(teamId);
				List<Object[]> userTeam = detailDAO.findUserByIdTeam(teamId);
				model.addAttribute("team", listall);
				model.addAttribute("user", userTeam);
				return "user/team-single";
			//Người dùng chưa có trong team
			}else {
			// Add vô team 
				System.out.println("Thêm thành viên vô team !");
			//show team chuyển hướng đến detail
				// Show thông tin + thành viên trong team
				List<Object[]> listall = detailDAO.findByIdTeam(teamId);
				List<Object[]> userTeam = detailDAO.findUserByIdTeam(teamId);
				model.addAttribute("team", listall);
				model.addAttribute("user", userTeam);
			return "user/team-single";
			}

			
		//Chưa đăng nhập trả về login
		} else {
			// Người dùng không tồn tại hoặc thông tin đăng nhập không chính xác
			return "redirect:/sportify/login";
		}
	}

}
