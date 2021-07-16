package com.atguigu.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stary
 * @version 1.0
 * @classname Cart
 * @description 购物车
 * @create 2021/5/16-15:24
 */
public class Cart {
    private Integer totalCount;
    private BigDecimal totalPrice;
    private Map<Integer,CartItem> items=new LinkedHashMap<Integer,CartItem>();

    //添加商品项
    public void addItem(CartItem cartItem){
        CartItem item = items.get(cartItem.getId());
        if(item==null){
            items.put(cartItem.getId(),cartItem);
        }
        else {
            //已经添加了
            item.setCount(item.getCount()+1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }


    }
    //删除商品项
    public void deleteItem(Integer id){
        items.remove(id);


    }
    //清空购物车
    public void clear(){
        items.clear();

    }
    //修改商品数
    public void updateCount(Integer id,Integer count){
        //
        CartItem cartItem = items.get(id);
        if(cartItem!=null){
            cartItem.setCount(count);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }


    }




    public Cart() {
    }


    public Cart(Integer totalCount, BigDecimal totalPrice, Map<Integer, CartItem> items) {
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Integer getTotalCount() {

        totalCount=0;
        for(Map.Entry<Integer,CartItem>entry:items.entrySet()){
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }



    public BigDecimal getTotalPrice() {

        totalPrice=new BigDecimal(0);
        for(Map.Entry<Integer,CartItem>entry:items.entrySet()){
            totalPrice =totalPrice.add(entry.getValue().getTotalPrice()) ;
        }

        return totalPrice;
    }



    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
