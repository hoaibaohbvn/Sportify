package duan.sportify.controller;

import java.awt.SystemColor;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import duan.sportify.entities.Users;
import duan.sportify.service.UserService;
import duan.sportify.dao.TeamDAO;
import duan.sportify.dao.TeamDetailDAO;
import duan.sportify.entities.Sporttype;
import duan.sportify.entities.Teamdetails;
import duan.sportify.entities.Teams;
import duan.sportify.service.SportTypeService;
import duan.sportify.service.TeamDetailService;
import duan.sportify.service.TeamService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	TeamDetailService teamDetailService;

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
	public String viewTeam(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
			@RequestParam(value = "sporttypeid", required = false, defaultValue = "") String sporttypeid,
			@RequestParam(value = "TeamUsername", required = false, defaultValue = "") String TeamUsername) {
		int size = 4; // Đặt kích thước trang bạn muốn (số phần tử trên mỗi trang)
		String usernameLogin = (String) request.getSession().getAttribute("username");

		List<Object[]> teamPage;

		int searchTextLength = searchText.length();// Kiểm tra xem ng dùng có nhập vào ô tìm kiếm không
		int sporttypeidLength = sporttypeid.length();// Kiểm tra xem ng dùng có chọn vào bộ lọc không
		int TeamUsernameLength = TeamUsername.length();// Kiểm tra xem ng dùng có chọn vào bộ lọc không

		if (searchTextLength > 0 && sporttypeidLength == 0 && TeamUsernameLength == 0) {// Kiểm tra nếu ng dùng có nhập
																						// vào ô tìm kiếm thì sẽ dỗ dữ

			// liệu theo SearchTeam
			teamPage = teamdao.SearchTeam(searchText);

		} else if (searchTextLength == 0 && sporttypeidLength > 0 && TeamUsernameLength == 0) {// Kiểm tra nếu ng dùng
																								// chọn vào lọc thì sẽ
																								// dỗ dữ
			// liệu theo FilterTeam
			teamPage = teamdao.FilterTeam(sporttypeid);
		} else if (searchTextLength == 0 && sporttypeidLength == 0 && TeamUsernameLength > 0) {

			if (usernameLogin != null) {
				teamPage = teamdao.findTeamUsername(usernameLogin);// Còn không nhập hay chọn gì thì sẽ đỗ toàn bộ
			} else {
				return "security/login";
			}

		} else {
			teamPage = teamdao.findAllTeam();// Còn không nhập hay chọn gì thì sẽ đỗ toàn bộ
		}

		if (usernameLogin != null) {
			List<Object[]> teamUsername = teamdao.findTeamUsername(usernameLogin);// Còn không nhập hay chọn gì thì sẽ
																					// đỗ toàn bộ
			model.addAttribute("teamUser", teamUsername);
		}

		// Tạo một sublist dựa trên số trang và kích thước trang
		int startIndex = page * size;
		int endIndex = Math.min(startIndex + size, teamPage.size());
		List<Object[]> pageContent = teamPage.subList(startIndex, endIndex);
		Page<Object[]> teamPageObj = new PageImpl<>(pageContent, PageRequest.of(page, size), teamPage.size());
		model.addAttribute("team", teamPageObj);
		model.addAttribute("searchText", searchText);
		model.addAttribute("TeamUsername", TeamUsername);
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
	public String exitteam(Model model, HttpServletRequest request, @PathVariable("teamId") Integer teamId,
			HttpSession session, RedirectAttributes redirectAttributes) {
		// Lấy username từ session
		String username = (String) request.getSession().getAttribute("username");
		Teams findTeamout = teamdao.findOneTeamUser(teamId, username);
		int count = detailDAO.countUser(teamId);

		if (findTeamout != null) {
			if (count <= 1) {
				detailDAO.deleteByUsernameAndTeamId(username, teamId);
				detailDAO.deleteTeamId(username, teamId);
				redirectAttributes.addFlashAttribute("message", "Bạn xóa nhóm thành công !");
			} else {
				redirectAttributes.addFlashAttribute("message1", "Bạn phải nhường đội trường mới được rời team !");
			}
			return "redirect:/sportify/team";
		} else {
			detailDAO.deleteByUsernameAndTeamId(username, teamId);
			redirectAttributes.addFlashAttribute("message", "Bạn đã rời thành công !");
			return "redirect:/sportify/team";
		}

//		if (findTeamout != null && count <= 1) {
//			detailDAO.deleteByUsernameAndTeamId(username, teamId);
//			detailDAO.deleteTeamId(username, teamId);
//			redirectAttributes.addFlashAttribute("message", "Bạn xóa nhóm thành công !");
//			return "redirect:/sportify/team";
//		}
//			if(count>1){
//			redirectAttributes.addFlashAttribute("message1", "Trong nhóm còn thành viên khác không thể xóa!");
//		}
//		detailDAO.deleteByUsernameAndTeamId(username, teamId);
//		redirectAttributes.addFlashAttribute("message", "Bạn đã rời thành công!");
//		return "redirect:/sportify/team";
	}

	// Điều hướng đến trang chi tiết
	@GetMapping("team/teamdetail/{teamId}")
	public String teamdetail(HttpServletRequest request, Model model, @PathVariable("teamId") Integer teamId,
			RedirectAttributes redirectAttributes) {
		// Lấy username từ session
		String username = (String) request.getSession().getAttribute("username");
		model.addAttribute("teamId", teamId);
		// Người dùng đã đăng nhập
		if (username == null) {
			// Người dùng không tồn tại hoặc thông tin đăng nhập không chính xác
			return "security/login";
		} else {
			// Người dùng tồn tại và thông tin đăng nhập chính xác
			// Kiểm tra user đã tồn tại trong team
			Teamdetails checkTeamUser = detailDAO.checkTeamUser(username, teamId);
			model.addAttribute("checkTeamUser", checkTeamUser);

			// Lấy số lượng thành viên trong team
			Teams team = teamService.findById(teamId);
			int quantity = team.getQuantity();
			Integer countMembers = detailDAO.countUser(teamId);
			// username đã có trong team
			if (checkTeamUser != null) {
				// Show thông tin + thành viên trong team
				List<Object[]> listall = detailDAO.findByIdTeam(teamId);
				List<Object[]> userTeam = detailDAO.findUserByIdTeam(teamId);
				List<Object[]> userCheckTeam = detailDAO.findUserCheckByIdTeam(teamId);
				Teams checkOneUser = teamdao.findOneTeam(teamId, username);
				Teams checkBoss = teamService.findById(teamId);
				model.addAttribute("oneUser", checkOneUser);
				model.addAttribute("team", listall);
				model.addAttribute("userCheckTeam", userCheckTeam);
				model.addAttribute("user", userTeam);
				model.addAttribute("checkBoss", checkBoss);
				String infouser = checkTeamUser.getInfouser();
				model.addAttribute("infouser", infouser);
				return "user/team-single";

				// Người dùng chưa có trong team
			} else if (countMembers < quantity) {
				Teamdetails checkAllTeamUser = detailDAO.checkAllTeamUser(username, teamId);
				model.addAttribute("checkAllTeamUser", checkAllTeamUser);
				if (checkAllTeamUser != null) {
					redirectAttributes.addFlashAttribute("message1", "Đợi đội trưởng xác nhận !");
					return "redirect:/sportify/team";
				}
				// gửi Thông báo về cho đội trưởng
				Teamdetails newUser = new Teamdetails();
				newUser.setJoindate(LocalDate.now());
				newUser.setTeamid(teamId);
				newUser.setUsername(username);
				newUser.setStatus(false);
				detailDAO.save(newUser);
				redirectAttributes.addFlashAttribute("message", "Bạn đã gửi yêu cầu tham gia thành công!");
				return "redirect:/sportify/team";
			} else {
				redirectAttributes.addFlashAttribute("message1", "Team đã đủ thành viên !");
				return "redirect:/sportify/team";
			}
		}
	}

	@PostMapping("/team/teamdetail/updateinfoUser")
	public String updateUser(Model model, HttpServletRequest request, @RequestParam("description") String description,
			@ModelAttribute("teamId") Integer teamId, HttpSession session, RedirectAttributes redirectAttributes) {
		// Lấy username từ session
		String username = (String) request.getSession().getAttribute("username");
		// Kiểm tra user đã tồn tại trong team
		Teamdetails checkTeamUser = detailDAO.checkTeamUser(username, teamId);
		if (description == null || description == "") {
			redirectAttributes.addFlashAttribute("message1", "Thêm giới thiệu thất bại !");
		} else {
			checkTeamUser.setInfouser(description);
			detailDAO.save(checkTeamUser);
			redirectAttributes.addFlashAttribute("message", "Thêm giới thiệu thành công !");
		}
		return "redirect:/sportify/team/teamdetail/" + teamId;
	}

	@PostMapping("/team/teamdetail/xacnhan")
	public String joinTeam(Model model, @ModelAttribute("teamId") Integer teamId,
			@ModelAttribute("username") String username, RedirectAttributes redirectAttributes) {

		// Lấy số lượng thành viên trong team
		Teams team = teamService.findById(teamId);
		int quantity = team.getQuantity();
		Integer countMembers = detailDAO.countUser(teamId);
		if (countMembers < quantity) {
			Teamdetails checkOneTeamUser = detailDAO.findOneUserCheckByIdTeam0(teamId, username);
			checkOneTeamUser.setStatus(true);
			checkOneTeamUser.setJoindate(LocalDate.now());
			detailDAO.save(checkOneTeamUser);
			redirectAttributes.addFlashAttribute("message", "Xác nhận thành viên thành công !");
			return "redirect:/sportify/team/teamdetail/" + teamId;
		} else {
			redirectAttributes.addFlashAttribute("message", "Team của bạn đã đủ thành viên !");
			return "redirect:/sportify/team/teamdetail/" + teamId;
		}
	}

	@PostMapping("/team/teamdetail/tuchoi")
	public String tuchoi(Model model, @ModelAttribute("teamId") Integer teamId,
			@ModelAttribute("username") String username, RedirectAttributes redirectAttributes) {
		Teamdetails checkOneTeamUser = detailDAO.findOneUserCheckByIdTeam0(teamId, username);
		detailDAO.delete(checkOneTeamUser);
		redirectAttributes.addFlashAttribute("message", "Từ chối thành viên !");
		return "redirect:/sportify/team/teamdetail/" + teamId;
	}

	@PostMapping("/team/teamdetail/kik")
	public String kik(Model model, @ModelAttribute("teamId") Integer teamId,
			@ModelAttribute("username") String username, RedirectAttributes redirectAttributes) {
		Teamdetails checkOneTeamUser = detailDAO.findOneUserCheckByIdTeam1(teamId, username);
		detailDAO.delete(checkOneTeamUser);
		redirectAttributes.addFlashAttribute("message", "Mời thành viên rời nhóm thành công !");
		return "redirect:/sportify/team/teamdetail/" + teamId;
	}

	@PostMapping("/team/teamdetail/phongdoitruong")
	public String phongdoitruong(HttpServletRequest request, Model model, @ModelAttribute("teamId") Integer teamId,
			@ModelAttribute("username") String username, RedirectAttributes redirectAttributes) {
		String usernameLogin = (String) request.getSession().getAttribute("username");
		Teams findTeamin = teamdao.findOneTeamUserin(username);
		if (findTeamin == null) {
			Teams findTeamout = teamdao.findOneTeamUser(teamId, usernameLogin);
			findTeamout.setUsername(username);
			teamdao.save(findTeamout);
			redirectAttributes.addFlashAttribute("message", "Phong đội trưởng thành công !");
			return "redirect:/sportify/team/teamdetail/" + teamId;
		}
		redirectAttributes.addFlashAttribute("message1", "Người này đang làm đội trưởng 1 nhóm khác !");
		return "redirect:/sportify/team/teamdetail/" + teamId;

	}

	@PostMapping("team/createTeam")
	public String createTeam(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes,
			@RequestParam("newNameteam") String newNameteam, @RequestParam("newAvatar") MultipartFile newAvatar,
			@RequestParam("newContact") String newContact, @RequestParam("newQuantity") Integer newQuantity,
			@RequestParam("newSporttypeid") String newSporttypeid,
			@RequestParam("newDescriptions") String newDescriptions) {
		String usernameLogin = (String) request.getSession().getAttribute("username");
		Teams findTeamUser = teamdao.findTeamUser(usernameLogin);
		if (usernameLogin == null) {
			return "security/login";
		} else {
			if (findTeamUser == null) {
				String fileName = newAvatar.getOriginalFilename();
				Teams newTeams = new Teams();
				try {
					// Thay đổi đường dẫn tới thư mục lưu trữ ảnh (thay thế "/" bằng đường dẫn thư
					// mục thực tế)
					String rootDir = System.getProperty("user.dir"); // Đường dẫn đến thư mục gốc của dự án
					String uploadDir = rootDir + "\\src\\main\\resources\\static\\user\\images\\team_img";
					Path filePath = Paths.get(uploadDir, fileName);
					Files.write(filePath, newAvatar.getBytes());

					newTeams.setAvatar(fileName);

				} catch (IOException e) {
					e.printStackTrace();
				}
				// Tạo đội
				newTeams.setNameteam(newNameteam);
				newTeams.setContact(newContact);
				newTeams.setSporttypeid(newSporttypeid);
				newTeams.setQuantity(newQuantity);
				newTeams.setDescriptions(newDescriptions);
				newTeams.setUsername(usernameLogin);
				newTeams.setCreatedate(LocalDate.now());
				teamdao.save(newTeams);
				// Thêm ng vô đội
//				// gửi Thông báo về cho đội trưởng
				int teamId = newTeams.getTeamid();
				Teamdetails newUser = new Teamdetails();
				newUser.setJoindate(LocalDate.now());
				newUser.setTeamid(teamId);
				newUser.setUsername(usernameLogin);
				newUser.setStatus(true);
				detailDAO.save(newUser);
				redirectAttributes.addFlashAttribute("message", "Tạo đội thành công");
				return "redirect:/sportify/team/teamdetail/" + teamId;
			} else {
				redirectAttributes.addFlashAttribute("message1", "Mỗi người chỉ tạo được 1 đội");
				return "redirect:/sportify/team";
			}
		}

	}

	@PostMapping("/team/findTeamUsername")
	public String FindTeamUsername(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam("TeamUsername") String TeamUsername) {
		return "redirect:/sportify/team?TeamUsername=" + TeamUsername;
	}

}
