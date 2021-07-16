package com.atguigu.dao.impl;

import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.atguigu.utils.JdbcUtils;
import com.mysql.cj.QueryResult;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * @ClassName BaseDao
 * @Description: TODO
 * @Author stary
 * @Date 2021/4/30 13:16
 * @Version 1.0
 **/
public abstract class BaseDao {
    //使用dbUtils操作数据库
    private QueryRunner queryRunner=new QueryRunner();

    public int update(String sql,Object ...args){
        Connection connection= JdbcUtils.getConnection();

        try {
            return queryRunner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public <T> T queryForOne(Class<T>type,String sql,Object ...args){
        Connection connection=new JdbcUtils().getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    public <T> List<T> queryForList(Class<T>type, String sql, Object ...args){
        Connection connection=new JdbcUtils().getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Object queryForSingleValue(String sql,Object...args){
        Connection connection=JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
