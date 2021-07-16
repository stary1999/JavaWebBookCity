package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;

/**
 * @author stary
 * @version 1.0
 * @classname OrderDao
 * @description
 * @create 2021/5/20-16:23
 */
public interface OrderDao {
    public int saveOrder(Order order);
    public List<Order> queryOrders();
    public int changeOrderStatus(String status,String orderId);
    public List<Order> queryOrdersByUserId(Integer userId);

}
