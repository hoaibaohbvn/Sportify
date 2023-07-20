package duan.sportify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO {
	Integer fieldid;
	Integer sporttypeid;
	String namefield;
	String descriptionfield;
	String price;
	String image;
	String address;
}
