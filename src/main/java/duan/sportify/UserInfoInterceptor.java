package duan.sportify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import duan.sportify.service.UserService;
import duan.sportify.entities.Users;

@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy username từ session
        String username = (String) request.getSession().getAttribute("username");

        if (username != null) {
            // Lấy thông tin người dùng từ service
            Users users = userService.findById(username);
            // Đưa thông tin người dùng vào model
            request.setAttribute("users", users);
        }

        return true;
    }
}



