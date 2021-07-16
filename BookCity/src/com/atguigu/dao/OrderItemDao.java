package com.atguigu.dao;

import com.atguigu.pojo.OrderItem;

import java.util.List;

/**
 * @author stary
 * @version 1.0
 * @classname OrderItemDao
 * @description
 * @create 2021/5/20-16:25
 */
public interface OrderItemDao {
    public int saveOrderItem(OrderItem orderItem);
    public List<OrderItem> queryOrderItemByOrderId(String orderId);


}
