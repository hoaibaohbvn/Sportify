package duan.sportify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import duan.sportify.service.UserService;
import duan.sportify.entities.Users;

@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Kiểm tra thông tin người dùng
        Users loggedInUser = (Users) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // Lấy thông tin người dùng từ service
            Users users = userService.findById(loggedInUser.getUsername());
            request.setAttribute("users", users);
        }

        return true;
    }

}


