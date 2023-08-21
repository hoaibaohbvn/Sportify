package duan.sportify;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import duan.sportify.dao.UserDAO;
import duan.sportify.entities.Users;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	BCryptPasswordEncoder pe;
	@Autowired
	UserDAO userDAO;
	@Autowired
	HttpSession session;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	// Cung cấp nguồn dữ liệu đăng nhập
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserDetailsService);
			auth.userDetailsService(username -> {
				try {
					Users user = userDAO.findById(username).get();
					String password = pe.encode(user.getPasswords());
					String[] roles = user.getListOfAuthorized().stream()
							.map(er -> er.getRoles().getRoleid())
							.collect(Collectors.toList())
							.toArray(new String[0]);
					Map<String, Object> authentication = new HashMap<>();
					authentication.put("user", user);
					byte[] token = (username + ":" + user.getPasswords()).getBytes();
					authentication.put("token", "Basic " + Base64.getEncoder().encodeToString(token));
					session.setAttribute("authentication", authentication);
					return User.withUsername(username).password(password).roles(roles).build();
				} catch (NoSuchElementException e) {
					throw new UsernameNotFoundException(username + " not found!");
				}
			});
			
		}
		
		
		// Phân quyền sử dụng
		@Override
		protected void configure(HttpSecurity http) throws Exception {	
			http.csrf().disable().cors().disable();
			http.authorizeRequests()
				.antMatchers("/sportify/field/booking/**","/sportify/field/profile/**","/sportify/team/teamdetail/**", "/sportify/order/**" ).authenticated()
				.antMatchers("/sportify/login/success").authenticated()
				.antMatchers("/admin/**").hasAnyRole("R01", "R02")
				.antMatchers("/rest/authorities").hasRole("R01")
				.anyRequest().permitAll();
			
			http.formLogin()
				.loginPage("/sportify/login")		
				.loginProcessingUrl("/sportify/login")
				.defaultSuccessUrl("/sportify/login/success", false)
				.failureUrl("/sportify/login/error")
				.usernameParameter("username").passwordParameter("password");
				

			http.rememberMe()
				.tokenValiditySeconds(86400);
			
			http.exceptionHandling()
				.accessDeniedPage("/sportify/unauthoried");

			http.logout()
				.logoutUrl("/sportify/logoff")
				.logoutSuccessUrl("/sportify/logoff/success");		
		}
		
		// Cơ chế mã hóa mật khẩu
		@Bean
		public BCryptPasswordEncoder getPasswordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		// Cho phép truy xuất REST API từ domain khác
		@Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	    }
}
