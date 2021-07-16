package com.atguigu.utils;

import javax.servlet.http.Cookie;

/**
 * @author stary
 * @version 1.0
 * @classname CookieUtils
 * @description
 * @create 2021/5/16-12:07
 */
public class CookieUtils {

    public static Cookie findCookie(String name,Cookie[] cookies){
        if(name==null||cookies==null||cookies.length==0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if(name.equals(cookie.getName())){
                return cookie;
            }
        }
        return  null;
    }

}
