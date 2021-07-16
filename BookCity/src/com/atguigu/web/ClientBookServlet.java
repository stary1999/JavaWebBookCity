package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author stary
 * @version 1.0
 * @classname ClientBookServlet
 * @description
 * @create 2021/5/14-20:55
 */
public class ClientBookServlet extends BaseServlet {

    private BookService bookService=new BookServiceImpl();
    //分页
    protected void page(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

        //获取请求参数pageNo、pageSize
        int pageNo= WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize=WebUtils.parseInt(req.getParameter("pageSize"), Page.page_size);
        //调用bookservice。page
        Page<Book> page=bookService.page(pageNo,pageSize);
        page.setUrl("client/clientbookServlet?action=page");
        //保存page对象到request域中
        req.setAttribute("page",page);
        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);

    }

    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

        //获取请求参数pageNo、pageSize
        int pageNo= WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize=WebUtils.parseInt(req.getParameter("pageSize"), Page.page_size);
        int min=WebUtils.parseInt(req.getParameter("min"),0);
        int max=WebUtils.parseInt(req.getParameter("max"),Integer.MAX_VALUE);

        //调用bookservice。page
        Page<Book> page=bookService.pageByPrice(pageNo,pageSize,min,max);

        StringBuilder sb=new StringBuilder("client/clientbookServlet?action=pageByPrice");
        //若有min请求参数，追加到分页条地址请求参数中
        if(req.getParameter("min")!=null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        //若有min请求参数，追加到分页条地址请求参数中
        if(req.getParameter("max")!=null){
            sb.append("&max=").append(req.getParameter("max"));
        }
        page.setUrl(sb.toString());
        //保存page对象到request域中
        req.setAttribute("page",page);
        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);

    }

}
