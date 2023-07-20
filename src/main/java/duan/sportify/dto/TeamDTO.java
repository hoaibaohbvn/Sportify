package duan.sportify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
	Integer teamid;
	Integer sporttypeid;
	String nameteam;
	Integer quantity;
	String avatar;
	String contact;
	String descriptions;
	String username;
	String createdate;
}
