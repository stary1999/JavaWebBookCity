package com.atguigu.test;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService userService=new UserServiceImpl();
    @Test
    public void registUser() {
        userService.registUser(new User(null,"ajsnz","6666666","aisianz@qq.com"));

    }

    @Test
    public void login() {
        System.out.println(userService.login(new User(null,"ajsnz","6666666","aisianz@qq.com")));
    }

    @Test
    public void existsUsername() {
        if(userService.existsUsername("ajsnz"))
            System.out.println("用户名已存在");
        else
            System.out.println("用户名可用");
    }
}