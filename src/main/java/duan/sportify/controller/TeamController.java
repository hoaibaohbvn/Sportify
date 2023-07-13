package duan.sportify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import duan.sportify.dao.TeamDAO;
import duan.sportify.entities.Sporttype;
import duan.sportify.service.SportTypeService;

@Controller
@RequestMapping("/sportify")
public class TeamController {

	@Autowired
	private TeamDAO teamdao;
	
	@Autowired
	SportTypeService sportTypeService;

	// Đỗ toàn bộ dữ liệu liên quan đến team
	@GetMapping("/team")
	public String viewTeam(Model model) {
		List<Object[]> listall = teamdao.findAllTeam();
		model.addAttribute("team", listall);
		List<Sporttype> sporttypeList = sportTypeService.findAll();
		model.addAttribute("sporttype",sporttypeList);
		System.out.println(sporttypeList.size());
		return "user/doi";
	}
	// Đỗ dự liệu đã tìm kiếm 
	@PostMapping("team/search")
	public String search(Model model, @RequestParam("SearchText") String searchText) {
		//kiểm tra xem đã lấy dc searchText
		//System.out.println(searchText);
	    model.addAttribute("searchText", searchText);
		//Tìm kiếm theo searchText
	    List<Object[]> teams = teamdao.SearchTeam(searchText);
	    model.addAttribute("team", teams);
	    //Kiểm tra tìm kiếm được kết quả bao nhiêu team
	    if (searchText=="") {
	    	return "user/doi";
		}else 
			
	    if (teams.isEmpty()) {
	        model.addAttribute("notFoundMessage", "Không tìm thấy đội nào.");
	    }else {
	    	model.addAttribute("FoundMessage","Tìm thấy "+teams.size()+" kết quả tìm kiếm của '"+searchText+"'");
	    }
		return "user/doi";	}
}
