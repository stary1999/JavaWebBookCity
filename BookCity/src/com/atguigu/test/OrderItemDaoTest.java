package com.atguigu.test;

import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.dao.impl.OrderItemDaoImpl;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author stary
 * @version 1.0
 * @classname OrderItemDaoTest
 * @description
 * @create 2021/5/20-18:33
 */
public class OrderItemDaoTest {

    @Test
    public void saveOrderItem() {
        OrderItemDao orderItemDao=new OrderItemDaoImpl();
        orderItemDao.saveOrderItem(new OrderItem(null,"java从入门到精通",1,new BigDecimal(100),new BigDecimal(100),"12345"));
        orderItemDao.saveOrderItem(new OrderItem(null,"javascript从入门到精通",1,new BigDecimal(100),new BigDecimal(100),"12345"));
        orderItemDao.saveOrderItem(new OrderItem(null,"c++入门到精通",1,new BigDecimal(100),new BigDecimal(100),"12345"));

    }

    @Test
    public void queryOrderItemByOrderId() {
        OrderItemDao orderItemDao=new OrderItemDaoImpl();
        List<OrderItem> orderItems = orderItemDao.queryOrderItemByOrderId("16215083798471");
        for(OrderItem orderItem:orderItems){
            System.out.println(orderItem);
        }
    }
}