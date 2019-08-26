package com.demo.interceptor;

import com.demo.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private final String LOGIN = "/login.html";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            LoginCheck loginCheck = ((HandlerMethod) handler).getMethodAnnotation(LoginCheck.class);
            if (loginCheck == null) {
                return true;
            }else {
                User user = (User) request.getSession().getAttribute("user");
                if (user == null) {
                    response.sendRedirect("/toLogin.do");
                    return false;
                }
            }
        }
        return true;
    }
}
