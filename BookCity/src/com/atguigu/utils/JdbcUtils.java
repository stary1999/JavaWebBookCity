package com.atguigu.utils;

import com.alibaba.druid.pool.DataSourceClosedException;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ClassName JdbcUtils
 * @Description: TODO
 * @Author stary
 * @Date 2021/4/29 21:43
 * @Version 1.0
 **/
public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns=new ThreadLocal<Connection>();



    static{

        try {
            Properties properties=new Properties();
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);

            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
//            com.mysql.cj.jdbc.ConnectionImpl@4f9a3314


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn=conns.get();

        if(conn==null){

            try  {
                //从数据库连接池获取连接
                conn = dataSource.getConnection();
                //保存到ThreadLocal对象中，供后面的jdbc操作使用
                conns.set(conn);
                //设置为手动管理
                conn.setAutoCommit(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }


        return conn;
    }

    //提交事务，并关闭释放连接
    public static void commitAndClose(){
        Connection connection=conns.get();
        //说明使用过连接操作过数据库
        if(connection!=null){

            try {
                connection.commit();//提交事务

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }
        //执行remove操作（因为tomcat服务器底层使用了线程池）
        conns.remove();
    }

    //回滚事务，并关闭释放连接
    public static void rollbackAndClose(){
        Connection connection=conns.get();
        //说明使用过连接操作过数据库
        if(connection!=null){

            try {
                connection.rollback();//提交事务

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }
        //执行remove操作（因为tomcat服务器底层使用了线程池）
        conns.remove();

    }


 /*   public static void close(Connection conn){
        if(conn!=null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }*/


}
