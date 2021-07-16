package com.atguigu.test;

import com.atguigu.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @ClassName JdbcUtilsTest
 * @Description: TODO
 * @Author stary
 * @Date 2021/4/30 13:08
 * @Version 1.0
 **/
public class JdbcUtilsTest {
    @Test
    public void testJdbcUtils(){
        for (int i=0;i<100;i++) {
            Connection connection = JdbcUtils.getConnection();
            System.out.println(connection);

        }


    }
}
