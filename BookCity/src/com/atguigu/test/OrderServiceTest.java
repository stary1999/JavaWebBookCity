package com.atguigu.test;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author stary
 * @version 1.0
 * @classname OrderServiceTest
 * @description
 * @create 2021/5/20-18:56
 */
public class OrderServiceTest {

    @Test
    public void createOrder() {
        Cart cart=new Cart();
        cart.addItem(new CartItem(1,"java从入门到头秃",1,new BigDecimal(1000),new BigDecimal(1000)));

        cart.addItem(new CartItem(1,"java从入门到头秃",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2,"数据结构与算法",1,new BigDecimal(100),new BigDecimal(100)));

        OrderService orderService=new OrderServiceImpl();
        System.out.println("订单号=："+orderService.createOrder(cart,1));


    }

    @Test
    public void showMyOrders(){
        OrderService orderService=new OrderServiceImpl();
        List<Order> orderList = orderService.showMyOrders(4);
        for(Order order:orderList){
            System.out.println(order);
        }

    }

    @Test
    public void showAllOrders(){
        OrderService orderService=new OrderServiceImpl();
        List<Order> orderList = orderService.showAllOrders();
        for(Order order:orderList){
            System.out.println(order);
        }

    }

    @Test
    public void showDetils(){
        OrderService orderService=new OrderServiceImpl();
        List<OrderItem> orderItems=orderService.showOrderDetail("16215083798471");
        for(OrderItem orderItem:orderItems){
            System.out.println(orderItem);
        }
    }
}