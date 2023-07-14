package duan.sportify.controller;

import java.util.List;

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
	
	//Đỗ lọc
	@ModelAttribute("sporttypeList")
	public List<Sporttype> getSporttypeList() {
	    return sportTypeService.findAll();
	}

	// Đỗ toàn bộ dữ liệu liên quan đến team
	@GetMapping("/team")
	public String viewTeam(Model model) {
		List<Object[]> listall = teamdao.findAllTeam();
		model.addAttribute("team", listall);
		return "user/doi";
	}
	// tìm kiếm 
	@PostMapping("team/search")
	public String search(Model model, @RequestParam("SearchText") String searchText) {
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
	
	
	 @GetMapping("/team/{sporttypeid}")
	    public String handleSportifyTeam(Model model, @PathVariable("sporttypeid") String sporttypeid) {
		 	String cid=sporttypeid;
	        List<Object[]> filterName = teamdao.FilterTeam(sporttypeid);
		    model.addAttribute("team", filterName);		    
	        return "user/doi";
	    }
	 
	 
	 @Autowired
	 TeamDetailDAO detailDAO;
	 
	 @GetMapping("team/teamdetail/{teamId}")
		public String teamdetail(Model model, @PathVariable("teamId") String teamId) {
		 	String Tid=teamId;
	        System.out.println(Tid);
	        List<Object[]> listall = detailDAO.findByIdTeam(Tid);
			model.addAttribute("team", listall);
			return "user/team-single";	}
}

	
