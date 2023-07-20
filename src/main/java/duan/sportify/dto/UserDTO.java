package duan.sportify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	String username;
	String passwords;
	String firstname;
	String lastname;
	String phone;
	String email;
	String address;
	String image;
	Integer gender;
}
