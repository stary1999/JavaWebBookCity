package com.atguigu.service;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;

import java.util.List;

/**
 * @author stary
 * @version 1.0
 * @classname OrderService
 * @description
 * @create 2021/5/20-16:18
 */
public interface OrderService {
    //生成订单
    public String createOrder(Cart cart,Integer userId);
    //查询全部订单
    public List<Order> showAllOrders();
    //发货
    public void sendOrder(String orderId);
    //查看订单详情
    public List<OrderItem> showOrderDetail(String orderId);
    //查看我的订单
    public List<Order> showMyOrders(Integer id);
    //签收订单、确认收货
    public void receiveOrder(String orderId);

}
