package com.atguigu.test;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author stary
 * @version 1.0
 * @classname CartTest
 * @description
 * @create 2021/5/16-15:41
 */
public class CartTest {

    @Test
    public void addItem() {

        Cart cart=new Cart();
        cart.addItem(new CartItem(1,"java从入门到头秃",1,new BigDecimal(1000),new BigDecimal(1000)));

        cart.addItem(new CartItem(1,"java从入门到头秃",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2,"数据结构与算法",1,new BigDecimal(100),new BigDecimal(100)));

        System.out.println(cart.getTotalCount());
        System.out.println(cart.getTotalPrice());
        System.out.println(cart);
    }

    @Test
    public void deleteItem() {

        Cart cart=new Cart();
        cart.addItem(new CartItem(1,"java从入门到头秃",1,new BigDecimal(1000),new BigDecimal(1000)));

        cart.addItem(new CartItem(1,"java从入门到头秃",1,new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2,"数据结构与算法",1,new BigDecimal(100),new BigDecimal(100)));

        cart.deleteItem(1);
        System.out.println(cart);
    }

    @Test
    public void clear() {
    }

    @Test
    public void updateCount() {
    }
}