package com.example.springboottoken.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springboottoken.pojo.User;
import com.example.springboottoken.service.TokenService;
import com.example.springboottoken.service.UserService;
import com.example.springboottoken.util.CookieUtils;
import com.example.springboottoken.util.TokenUtil;
import com.example.springboottoken.util.UserLoginToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserApi extends  BaseContrller{
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    // 登录
    @ApiOperation(value = "登陆", notes = "登陆")
    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public Object login(User user, HttpServletResponse response, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        User userForBase = new User();
        userForBase.setId(userService.findByUsername(user).getId());
        userForBase.setUsername(userService.findByUsername(user).getUsername());
        userForBase.setPassword(userService.findByUsername(user).getPassword());
        if (!userForBase.getPassword().equals(user.getPassword())) {
            jsonObject.put("message", "登录失败,密码错误");
            return jsonObject;
        } else {
            String token = tokenService.getToken(userForBase);
            jsonObject.put("token", token);

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            //将用户登录信息存入到session
          setUser(userForBase);
            //将登录信息存入到cookie中
            CookieUtils.setCookie(request, response, "JT_TICKET",userForBase.getId());

            return jsonObject;

        }
    }
    /***
     * 这个请求需要验证token才能访问
     *
     * @author: qiaoyn
     * @date 2019/06/14
     * @return String 返回类型
     */
    @UserLoginToken
    @ApiOperation(value = "获取信息", notes = "获取信息")
    @RequestMapping(value = "/getMessage" ,method = RequestMethod.GET)
    public String getMessage(HttpServletRequest request) {

        // 获取cookie中到数据

        System.out.println(CookieUtils.getCookieValue(request,"JT_TICKET"));
System.out.println("session"+getUser().getId());
        return TokenUtil.getTokenUserId();
    }
    //注解表示在访问此接口时要判断是否存在token即是否是登录状态
    @UserLoginToken
    @RequestMapping(value = "/getMessage1" ,method = RequestMethod.GET)
    public String getMessage1() {
        return "123";
    }

    @RequestMapping(value = "/getMessage2" ,method = RequestMethod.GET)
    public String getMessage2() {
        return "不加@UserLoginToken注解，说明不需要判断用户是否是登录状态";
    }
}

