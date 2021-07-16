package com.atguigu.web;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import com.atguigu.pojo.User;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import com.atguigu.utils.JdbcUtils;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author stary
 * @version 1.0
 * @classname OrderServlet
 * @description
 * @create 2021/5/20-16:14
 */
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();


    //生成订单
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //调用orderService.createOrder(cart,userId)
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //获取用户id
        User user = (User) req.getSession().getAttribute("user");
        //用户未登陆
        if (user == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }
        Integer id = user.getId();
        String orderId = null;

        orderId = orderService.createOrder(cart, id);

        req.getSession().setAttribute("orderId", orderId);

        //请求转发到页面
//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req,resp);
        //避免表单重复提交，用重定向替换请求转发
        //重定向不支持resquest域保存数据
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");


    }

    //查看所有订单
    protected void showAllOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<Order> orderListAll = orderService.showAllOrders();

        req.setAttribute("orderListAll", orderListAll);
 /*       System.out.println("=========");
        for (Order order: orderListAll){
            System.out.println(order);
        }*/

        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req, resp);


    }

    //发货
    protected void sendOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderId =req.getParameter("orderId");
        System.out.println("orderId==" + orderId);

        orderService.sendOrder(orderId);
        List<Order> orderListAll = orderService.showAllOrders();

        req.setAttribute("orderListAll", orderListAll);

        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req, resp);


    }

    //查看订单详情
    protected void showOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String orderId =req.getParameter("orderId");
        System.out.println("orderId==" + orderId);

        List<OrderItem> orderItems= orderService.showOrderDetail(orderId);

        req.setAttribute("orderItems", orderItems);

        req.getRequestDispatcher("/pages/order/orderDetail.jsp").forward(req, resp);


    }

    //查看我的订单
    protected void showMyOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //  http://localhost:8080/BookCity/orderServlet?action=showAllOrder&&userId=4

        int userId = WebUtils.parseInt(req.getParameter("userId"), 0);
        /*   String userId = req.getParameter("userId");*/
        System.out.println("userId==" + userId);


        List<Order> orderList = orderService.showMyOrders(userId);

        req.setAttribute("orderList", orderList);

        req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);


    }

    //签收订单
    protected void receiveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderId =req.getParameter("orderId");
        int userId = WebUtils.parseInt(req.getParameter("userId"), 0);
        System.out.println("orderId==" + orderId);

        orderService.receiveOrder(orderId);
        List<Order> orderList = orderService.showMyOrders(userId);

        req.setAttribute("orderList", orderList);

        req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);


    }


}
