package com.system.controller;

import com.system.po.Userlogin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {

    //登录跳转
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String loginUI() throws Exception {
        return "../../login";
    }

    //登录表单处理
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(Userlogin userlogin) throws Exception {

        //Shiro实现登录
        UsernamePasswordToken token = new UsernamePasswordToken(userlogin.getUsername(), userlogin.getPassword());
        Subject subject = SecurityUtils.getSubject();

        //获取用户名，若登录失败抛出异常
        subject.login(token);

        //加密成功
    // System.out.println("成功登陆!账号:"+token.getUsername()+"    密码:"+token.getPassword()+"  ");
    // System.out.println("     "+subject.getPrincipal());


        if (subject.hasRole("admin")) {
            return "redirect:/admin/showEvent";
        } else if (subject.hasRole("teacher")) {
            return "redirect:/teacher/showEvent";
        } else if (subject.hasRole("student")) {
            return "redirect:/student/showEvent";
        } else {
            return "/relogin";
        }

    }

    //登陆失败跳转
    @RequestMapping(value = "/relogin", method = {RequestMethod.GET})
    public String reloginUI() throws Exception {
        return "../../login";
    }
}
