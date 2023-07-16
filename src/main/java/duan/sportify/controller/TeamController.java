package duan.sportify.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.dao.TeamDAO;
import duan.sportify.dao.TeamDetailDAO;
import duan.sportify.entities.Sporttype;
import duan.sportify.service.SportTypeService;

@Controller
@RequestMapping("/sportify")
public class TeamController {

	@Autowired
	private TeamDAO teamdao;

	@Autowired
	SportTypeService sportTypeService;

	// Đỗ lọc
	@ModelAttribute("sporttypeList")
	public List<Sporttype> getSporttypeList() {
		return sportTypeService.findAll();
	}

	// Đỗ toàn bộ dữ liệu liên quan đến team
	@GetMapping("/team")
	public String viewTeam(Model model,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
			Pageable pageable) {
		Page<Object[]> teamPage;
		if (searchText.isEmpty()) {
			teamPage = teamdao.findAllTeam(pageable);
		} else {
			teamPage = teamdao.SearchTeam(searchText, pageable);
		}
		List<Object[]> teams = teamPage.getContent();
		model.addAttribute("team", teams);
		model.addAttribute("page", teamPage);
		model.addAttribute("searchText", searchText);
		return "user/doi";
	}

	@PostMapping("/team/search")
	public String search(Model model, @RequestParam("searchText") String searchText, Pageable pageable) {
		// Xử lý tìm kiếm và chuyển hướng đến trang /team với query parameter searchText
		if (searchText == "") {
			return "redirect:/sportify/team";

		}
		return "redirect:/sportify/team?searchText=" + searchText;
	}

	@GetMapping("/team/{sporttypeid}")
	public String handleSportifyTeam(Model model, @PathVariable("sporttypeid") String sporttypeid) {
		String cid = sporttypeid;
		List<Object[]> filterName = teamdao.FilterTeam(sporttypeid);
		model.addAttribute("team", filterName);
		return "user/doi";
	}

	@Autowired
	TeamDetailDAO detailDAO;

	@GetMapping("team/teamdetail/{teamId}")
	public String teamdetail(Model model, @PathVariable("teamId") String teamId) {
		String Tid = teamId;
		System.out.println(Tid);
		List<Object[]> listall = detailDAO.findByIdTeam(Tid);
		model.addAttribute("team", listall);
		return "user/team-single";
	}
}
