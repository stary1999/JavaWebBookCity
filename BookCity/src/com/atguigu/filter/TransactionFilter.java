package com.atguigu.filter;

import com.atguigu.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author stary
 * @version 1.0
 * @classname TransactionFilter
 * @description
 * @create 2021/5/21-13:23
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(servletRequest,servletResponse);
            JdbcUtils.commitAndClose();

        }catch (Exception e){
            e.printStackTrace();
            JdbcUtils.rollbackAndClose();
            throw new RuntimeException(e);//异常继续向上抛出给tomcat服务器


        }


    }

    @Override
    public void destroy() {

    }
}
