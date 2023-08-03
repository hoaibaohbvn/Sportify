package duan.sportify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import duan.sportify.service.UserService;
import duan.sportify.entities.Users;
import duan.sportify.entities.Authorized;
import duan.sportify.dao.AuthorizedDAO;

@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthorizedDAO authorizedDAO; 

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy username từ session
        String username = (String) request.getSession().getAttribute("username");
        if (username != null) {
            // Lấy thông tin người dùng từ service
            Users users = userService.findById(username);
            // Đưa thông tin người dùng vào model
            request.setAttribute("users", users);
            //lấy role người dùng
            Authorized authorized= authorizedDAO.findAllAuthorized(username);
            // Đưa thông tin vào model
            request.setAttribute("authorized", authorized);
        }
        return true;
    }
}



