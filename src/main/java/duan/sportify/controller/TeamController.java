package duan.sportify.controller;

import java.awt.SystemColor;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.net.URLEncoder;


@SuppressWarnings("unused")
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
	public String viewTeam(Model model, HttpSession session,@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
			@RequestParam(value = "sporttypeid", required = false, defaultValue = "") String sporttypeid
			) {
		int size = 4; // Đặt kích thước trang bạn muốn (số phần tử trên mỗi trang)

		List<Object[]> teamPage;

		int searchTextLength = searchText.length();// Kiểm tra xem ng dùng có nhập vào ô tìm kiếm không
		int sporttypeidLength = sporttypeid.length();// Kiểm tra xem ng dùng có chọn vào bộ lọc không

		if (searchTextLength > 0 && sporttypeidLength == 0) {// Kiểm tra nếu ng dùng có nhập vào ô tìm kiếm thì sẽ dỗ dữ
			
			// liệu theo SearchTeam
			teamPage = teamdao.SearchTeam(searchText);
			
		} else if (searchTextLength == 0 && sporttypeidLength > 0) {// Kiểm tra nếu ng dùng chọn vào lọc thì sẽ dỗ dữ
																	// liệu theo FilterTeam
			teamPage = teamdao.FilterTeam(sporttypeid);
		} else {
			teamPage = teamdao.findAllTeam();// Còn không nhập hay chọn gì thì sẽ đỗ toàn bộ
		}
		

		// Tạo một sublist dựa trên số trang và kích thước trang
		int startIndex = page * size;
		int endIndex = Math.min(startIndex + size, teamPage.size());
		List<Object[]> pageContent = teamPage.subList(startIndex, endIndex);
		Page<Object[]> teamPageObj = new PageImpl<>(pageContent, PageRequest.of(page, size), teamPage.size());
		model.addAttribute("team", teamPageObj);
		model.addAttribute("searchText", searchText);
		model.addAttribute("sporttypeid", sporttypeid);
		
		// Kiểm tra để hiển thị thông báo
		if (!searchText.isEmpty() && teamPage.size() > 0) {
			model.addAttribute("FoundMessage",
					"Tìm thấy " + teamPage.size() + " kết quả tìm kiếm của '" + searchText + "'.");
		}
		if (teamPage.size() == 0) {
			model.addAttribute("notFoundMessage",
					"Tìm thấy " + teamPage.size() + " kết quả tìm kiếm của '" + searchText + "'.");
		}

		return "user/doi";
	}

	@PostMapping("/team/search")
	public String search(Model model, @RequestParam("searchText") String searchText) {
		 try {
		        // Mã hóa searchText trước khi chuyển hướng
		        String encodedSearchText = URLEncoder.encode(searchText, "UTF-8");
		        if (encodedSearchText.isEmpty()) {
		            return "redirect:/sportify/team";
		        }
		        return "redirect:/sportify/team?searchText=" + encodedSearchText;
		    } catch (UnsupportedEncodingException e) {
		        // Xử lý ngoại lệ nếu encoding không được hỗ trợ
		        e.printStackTrace();
		        // Hoặc xử lý bằng cách trả về một trang lỗi
		        return "error"; // Ví dụ: "error.html"
		    }
	}

	@GetMapping("/team/{sporttypeid}")
	public String handleSportifyTeam(Model model, @PathVariable("sporttypeid") String sporttypeid) {
		return "redirect:/sportify/team?sporttypeid=" + sporttypeid;
	}

	@GetMapping("team/teamdetail/{teamId}/exit")
	public String exitteam(Model model,HttpServletRequest request, @PathVariable("teamId") Integer teamId,HttpSession session, RedirectAttributes redirectAttributes) {
		// Lấy username từ session
        String username = (String) request.getSession().getAttribute("username");
		detailDAO.deleteByUsernameAndTeamId(username,teamId);
        redirectAttributes.addFlashAttribute("message", "Bạn đã rời thành công!");
		return "redirect:/sportify/team";
	}
	
	// Điều hướng đến trang chi tiết
	@GetMapping("team/teamdetail/{teamId}")
	public String teamdetail(HttpServletRequest request,Model model, @PathVariable("teamId") Integer teamId, RedirectAttributes redirectAttributes) {
		// Lấy username từ session
        String username = (String) request.getSession().getAttribute("username");
	    model.addAttribute("teamId", teamId);
		// Người dùng đã đăng nhập

			// Người dùng tồn tại và thông tin đăng nhập chính xác
			// Kiểm tra user đã tồn tại trong team
			Teamdetails checkTeamUser = detailDAO.checkTeamUser(username, teamId);
			model.addAttribute("checkTeamUser", checkTeamUser);

			// Lấy số lượng thành viên trong team
			Teams team = teamService.findById(teamId);
			int quantity = team.getQuantity();
			// Đếm số lượng thành viên trong teamDetail
			Integer countMembers = detailDAO.countUser(teamId);

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
	}

	@PostMapping("/team/teamdetail/updateinfoUser")
	public String updateUser(Model model,HttpServletRequest request,@RequestParam("description") String description,@ModelAttribute("teamId") Integer teamId,HttpSession session,RedirectAttributes redirectAttributes) {
		// Lấy username từ session
        String username = (String) request.getSession().getAttribute("username");
		// Kiểm tra user đã tồn tại trong team
		Teamdetails checkTeamUser = detailDAO.checkTeamUser(username, teamId);
		checkTeamUser.setInfouser(description);
		detailDAO.save(checkTeamUser);
		if(description == null || description=="") {
			redirectAttributes.addFlashAttribute("message1", "Thêm giới thiệu thất bại !");

		}else {
			redirectAttributes.addFlashAttribute("message", "Thêm giới thiệu thành công !");

		}
		return "redirect:/sportify/team/teamdetail/" + teamId;
	}
	
	
}
