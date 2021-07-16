package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stary
 * @version 1.0
 * @classname CartServlet
 * @description
 * @create 2021/5/16-15:11
 */
public class CartServlet extends BaseServlet{

    private BookService bookService=new BookServiceImpl();

    //添加
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求参数、商品编号
        int id= WebUtils.parseInt(req.getParameter("id"),0);



        //调用bookservice。queryBookById，得到图书信息
        Book book = bookService.queryBookById(id);
        //把图书信息转换为cartItem信息
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        //调用cart。addItem，添加商品项
        Cart cart=(Cart)req.getSession().getAttribute("cart");
        if(cart==null){
            cart=new Cart();
            req.getSession().setAttribute("cart",cart);

        }

        cart.addItem(cartItem);
        //保存最后一次添加的商品名称到session域
        req.getSession().setAttribute("lastName",cartItem.getName());

        System.out.println(cart);
        //重定向回商品列表页面
        resp.sendRedirect(req.getHeader("Referer"));


    }


    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求参数、商品编号
        int id= WebUtils.parseInt(req.getParameter("id"),0);



        //调用bookservice。queryBookById，得到图书信息
        Book book = bookService.queryBookById(id);
        //把图书信息转换为cartItem信息
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        //调用cart。addItem，添加商品项
        Cart cart=(Cart)req.getSession().getAttribute("cart");
        if(cart==null){
            cart=new Cart();
            req.getSession().setAttribute("cart",cart);

        }

        cart.addItem(cartItem);
        //保存最后一次添加的商品名称到session域
        req.getSession().setAttribute("lastName",cartItem.getName());
        //ajax返回
        Map<String ,Object> resultMap=new HashMap<String ,Object>();
        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());
        Gson gson=new Gson();
        String resultMapJsonString=gson.toJson(resultMap);
        resp.getWriter().write(resultMapJsonString);


    }



    //删除
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取商品编号
        int id=WebUtils.parseInt(req.getParameter("id"),0);
        //找到购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart!=null){
            //删除购物车商品项
            cart.deleteItem(id);
        }
        resp.sendRedirect(req.getHeader("Referer"));

    }
    //清空购物车
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart!=null){
            //删除购物车商品项
            cart.clear();
        }
        //重定向：从哪儿来，回哪儿去
        resp.sendRedirect(req.getHeader("Referer"));


    }
    //修改数量
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数：商品编号、商品数量
        int id=WebUtils.parseInt(req.getParameter("id"),0);
        int count=WebUtils.parseInt(req.getParameter("count"),1);
        Cart cart=(Cart) req.getSession().getAttribute("cart");

        if(cart!=null){
            //修改商品数量
            cart.updateCount(id,count);
            //重定向：从哪儿来，回哪儿去
            resp.sendRedirect(req.getHeader("Referer"));
        }


    }
}
