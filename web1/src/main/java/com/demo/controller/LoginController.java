package com.demo.controller;

import com.demo.dao.UserDao;
import com.demo.pojo.User;
import com.demo.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class LoginController {
    @Autowired
    UserDao userDao;
    @RequestMapping("/toLogin.do")
    public ModelAndView toLogin(){

        return new ModelAndView("/WEB-INF/login.jsp");
    }
    @RequestMapping("/login.do")
    public void login(String userName,String pwd){
        User user = new User();
        user.setUserName(userName);
        user.setPwd(pwd);
        Date date = new Date();
        user.setAddDate(Tool.fmtDate(date));
        user.setAddTime(Tool.fmtTime(date));

        int result = userDao.insert(user);
        System.out.println(result);
    }

}
