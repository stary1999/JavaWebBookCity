package com.atguigu.test;

import com.atguigu.dao.OrderDao;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author stary
 * @version 1.0
 * @classname OrderDaoTest
 * @description
 * @create 2021/5/20-18:37
 */
public class OrderDaoTest {

    @Test
    public void saveOrder() {
        OrderDao orderDao=new OrderDaoImpl();
        orderDao.saveOrder(new Order("12346",new Date(),new BigDecimal(100),0,1));

    }

    @Test
    public void queryOrders() {
        OrderDao orderDao =new OrderDaoImpl();

        List<Order> orderList= orderDao.queryOrders();
        for(Order order:orderList){
            System.out.println("order=="+order);

        }
    }

    @Test
    public void changeOrderStatus() {
        OrderDao orderDao =new OrderDaoImpl();
        orderDao.changeOrderStatus("2","16215090641871");
        List<Order> orderList = orderDao.queryOrdersByUserId(4);
        for(Order order:orderList){
            System.out.println(order);
        }

    }

    @Test
    public void queryOrdersByUserId() {
        OrderDao orderDao =new OrderDaoImpl();

        List<Order> orderList= orderDao.queryOrdersByUserId(2);
        for(Order order:orderList){
            System.out.println("order=="+order);

        }

    }
}