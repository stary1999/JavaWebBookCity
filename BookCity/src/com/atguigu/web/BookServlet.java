package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.pojo.User;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName BookServlet
 * @Description: TODO
 * @Author stary
 * @Date 2021/5/8 14:33
 * @Version 1.0
 **/
public class BookServlet extends BaseServlet {
    private BookService bookService=new BookServiceImpl();



    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
           int pageNo=WebUtils.parseInt(req.getParameter("pageNo"),0)+1;


            bookService.addBook(book);
            System.out.println(book);
//        req.getRequestDispatcher("/manager/bookServlet?action=list").forward(req,resp);
            resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo="+pageNo);

    }
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id=WebUtils.parseInt(req.getParameter("id"),0);
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        book.setId(id);
        bookService.updateBook(book);

        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page");


    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       int id=WebUtils.parseInt(req.getParameter("id"),0);

        bookService.deleteBookById(id);
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+req.getParameter("pageNo"));

    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.通过bookservice查询全部图书
        List<Book> books = bookService.queryBooks();
        //2.把全部图书保存到request域中
        req.setAttribute("books",books);
        //3.请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);

    }

    //获取要修改的图书信息
    protected void getBook(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        int id=WebUtils.parseInt(req.getParameter("id"),0);
        Book book = bookService.queryBookById(id);
        req.setAttribute("book",book);
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);

    }

    //分页
    protected void page(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //获取请求参数pageNo、pageSize
        int pageNo=WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize=WebUtils.parseInt(req.getParameter("pageSize"), Page.page_size);

        //调用bookservice。page
        Page<Book> page=bookService.page(pageNo,pageSize);
        page.setUrl("manager/bookServlet?action=page");
        //保存page对象到request域中
        req.setAttribute("page",page);
        //请求转发
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);

    }

}
