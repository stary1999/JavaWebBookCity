package com.atguigu.utils;

import com.atguigu.pojo.User;
//import com.sun.deploy.net.HttpResponse;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @ClassName WebUtils
 * @Description: TODO
 * @Author stary
 * @Date 2021/5/7 20:33
 * @Version 1.0
 **/
public class WebUtils {
    public static <T> T copyParamToBean(Map values, T bean){
        try {
            BeanUtils.populate(bean,values);

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return bean;
    }

    public static int parseInt(String strInt,int defaultValue){
        try {
            return Integer.parseInt(strInt);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return defaultValue;

    }
}
