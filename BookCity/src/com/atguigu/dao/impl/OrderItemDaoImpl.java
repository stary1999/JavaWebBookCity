package com.atguigu.dao.impl;

import com.atguigu.dao.OrderItemDao;
import com.atguigu.pojo.OrderItem;

import java.util.List;

/**
 * @author stary
 * @version 1.0
 * @classname OrderItemImpl
 * @description
 * @create 2021/5/20-17:00
 */
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql="insert into t_order_item(name,count,price,total_price,order_id) values(?,?,?,?,?)";
        return update(sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOrderId());


    }

    @Override
    public List<OrderItem> queryOrderItemByOrderId(String orderId) {
        String sql="select id id,name name,count count,price price,total_price totalPrice,order_id orderId from t_order_item where order_id=?";
        return queryForList(OrderItem.class,sql,orderId);


    }
}
