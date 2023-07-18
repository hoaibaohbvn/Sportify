package duan.sportify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	Integer productid;
	Integer categoryid;
	String productname;
	String image;
	String descriptions;
	String discountprice;
	String datecreate;
	String price;
	Integer status;
	Integer quantity;
	
}
