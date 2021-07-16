package com.atguigu.service.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.dao.impl.OrderItemDaoImpl;
import com.atguigu.pojo.*;
import com.atguigu.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author stary
 * @version 1.0
 * @classname OrderServiceImpl
 * @description
 * @create 2021/5/20-18:44
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao=new OrderDaoImpl();
    private OrderItemDao orderItemDao=new OrderItemDaoImpl();
    private BookDao bookDao=new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {

        String orderId=System.currentTimeMillis()+""+userId;
        Order order=new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        orderDao.saveOrder(order);
        //遍历购物车中每一个商品项，转化为订单项保存到数据库
        for(Map.Entry<Integer, CartItem>entry:cart.getItems().entrySet()){
            //获取每个购物车中的商品项
            CartItem cartItem=entry.getValue();
            //转换为每一个订单项
            OrderItem orderItem=new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            //更新库存和销量
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales()+cartItem.getCount());
            book.setStock(book.getStock()-cartItem.getCount());
            bookDao.updateBook(book);


        }
        //清空购物车
        cart.clear();
        return orderId;

    }


    @Override
    public List<Order> showAllOrders() {

        return orderDao.queryOrders();
    }

    @Override
    public void sendOrder(String orderId) {
        int i = orderDao.changeOrderStatus("1", orderId);

    }

    //查询订单细节
    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        return orderItemDao.queryOrderItemByOrderId(orderId);

    }

    @Override
    public  List<Order> showMyOrders(Integer id) {

        return orderDao.queryOrdersByUserId(id);

    }

    //收货、对用户
    @Override
    public void receiveOrder(String orderId) {
        int i = orderDao.changeOrderStatus("2", orderId);

    }
}
