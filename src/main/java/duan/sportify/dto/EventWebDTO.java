package duan.sportify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventWebDTO {
	Integer eventid;
	String nameevent;
	String datestart;
	String dateend;
	String image;
	String discriptions;
}
