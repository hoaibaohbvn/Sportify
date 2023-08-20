package duan.sportify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import duan.sportify.entities.Users;
import duan.sportify.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Users users = userService.findById(username);
	        
	        if (!users.getStatus()) {
	            throw new BadCredentialsException("Tài khoản đã bị khóa");
	        }

	        return new org.springframework.security.core.userdetails.User(
	            users.getUsername(),
	            users.getPasswords(),
	            new ArrayList<>()
	        );
	    }

}
