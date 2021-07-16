package com.atguigu.dao;

/**
 * @ClassName UserDao
 * @Description: TODO
 * @Author stary
 * @Date 2021/4/30 13:39
 * @Version 1.0
 **/

import com.atguigu.pojo.User;

public interface UserDao {
    //查询
    public User queryUserByUsername(String username);

    public User queryUserByUsernameAndPassword(String username,String password);


    //保存用户信息
    public int saveUser(User user);



}
