# JavaWebBookCity
oldWay

---
title: javaWeb书城项目传统版
tags:
  - java
  - web
  - jsp
categories:
  - java
date: 2021-07-14 21:57:09
updated:
author:

---

# 概述

javaWeb书城项目传统版是在尚硅谷的web培训教程的基础上，进一步完善的，具体资源以及教程都来自/参考尚硅谷课程。

本次复盘，是在老师原有上课的基础上，补充一些未完成的细节，完善功能，使整个项目看起来更合理一些。因时间原因，项目仍有很多缺点不足，有待改进。加之jsp技术过于老旧，后面可能采取SpringBoot的方式实现类似功能的项目。



## 总体设计

整个项目主要由以下几个部分组成：

* 注册登录
* 图书模块
* 订单模块
* 数据库交互
* MVC架构

### 注册登录

注册登录模块主要是实现用户的管理，用户应该分为两种类型，即：常规买家用户和管理员用户，二者权限不同，对于非管理员用户，登录后，应该只能查看自己的订单，与执行签收操作，后台管理部分对其而言应该是隐藏的。

普通用户的操作有：查看商品，加入购物车，下单付款，确认收货。

管理员用户还需要维护图书模块和订单模块（发货等操作），所有的权限对管理员开放

注册与登录的实现：

#### 注册

注册的时候需要用户输入用户名称、用户密码、确认密码、电子邮箱以及验证码等信息

其中，用户名和密码、邮箱等，采用正则表达式在前端jsp页面进行检测，验证码使用的是google提供的验证码生成包。

若注册时用户名已存在，则采用ajax的形式部分刷新前端页面，重新注册。

注册成功后，将注册数据写入数据库表

#### 登录

登录的凭据是用户名和密码，采用cookie缓存登录信息（待补充），经由servlet处理后，返回登录结果



### 图书模块

图书模块主要是向用户展示图书商品项

#### 后台图书管理

首先，在后台图书管理模块，需要实现以下功能：图书修改、图书删除、图书添加、以及最重要的图书查询。

这部分主要是与数据库进行交互，基于CRUD实现

#### 图书信息展示

不管是在后台还是在首页，都需要将图书信息展示出来，并且采用分页的形式输出

分页部分需要新建一个page对象，里面存储分页的相关信息，然后根据分页情况进行查询并返回查询结果到展示页面。

注意分页条的设计实现

也可以利用其它分页插件实现

此外，在首页，还需要设计一个根据价格区间查询图书的功能，底层原理是SQL的条件查询

### 订单模块

订单模块包括了订单查询、订单管理、以及购物车几个部分，底层也是数据库

#### 购物车

购物车部分采用的是session域来保存数据，避免页面跳转、重定向时丢失数据，理论上应该将数据存到数据库。这部分功能有待完善。

#### 订单管理

用户将商品添加到购物车后，可以选择付款，付款后，会由系统自动生成一个订单号，并将订单数据保存到数据库

用户可以在“我的订单”处查看已有的订单，并执行“签收”操作

管理员可以在后台查看所有的订单，并执行“发货”操作

#### 订单查询

订单查询类似图书查询功能，但对普通用户而言，只能查看自己的订单项。

需要采用分页设计，待改进

### 数据库交互

使用Druid数据库连接池服务。通过jdbcUtils连接数据库，所有的SQL操作类都继承自BaseDao类，所有的CRUD都归结于update和“查询”两个操作。考虑到查询的特殊性，查询又分为返回单个值的和返回对象列表两个种类。这三个基本类几乎涵盖了所有的基础的dao操作。后续需要实现的所有数据库操作，都可以由此得到。

#### jdbcUtils

~~~java
package com.atguigu.utils;

import com.alibaba.druid.pool.DataSourceClosedException;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ClassName JdbcUtils
 * @Description: TODO
 * @Author stary
 * @Date 2021/4/29 21:43
 * @Version 1.0
 **/
public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns=new ThreadLocal<Connection>();



    static{

        try {
            Properties properties=new Properties();
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);

            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
//            com.mysql.cj.jdbc.ConnectionImpl@4f9a3314


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn=conns.get();

        if(conn==null){

            try  {
                //从数据库连接池获取连接
                conn = dataSource.getConnection();
                //保存到ThreadLocal对象中，供后面的jdbc操作使用
                conns.set(conn);
                //设置为手动管理
                conn.setAutoCommit(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }


        return conn;
    }

    //提交事务，并关闭释放连接
    public static void commitAndClose(){
        Connection connection=conns.get();
        //说明使用过连接操作过数据库
        if(connection!=null){

            try {
                connection.commit();//提交事务

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }
        //执行remove操作（因为tomcat服务器底层使用了线程池）
        conns.remove();
    }

    //回滚事务，并关闭释放连接
    public static void rollbackAndClose(){
        Connection connection=conns.get();
        //说明使用过连接操作过数据库
        if(connection!=null){

            try {
                connection.rollback();//提交事务

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }
        //执行remove操作（因为tomcat服务器底层使用了线程池）
        conns.remove();

    }


 /*   public static void close(Connection conn){
        if(conn!=null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }*/

}

~~~

#### BaseDao

~~~java
package com.atguigu.dao.impl;

import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.atguigu.utils.JdbcUtils;
import com.mysql.cj.QueryResult;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * @ClassName BaseDao
 * @Description: TODO
 * @Author stary
 * @Date 2021/4/30 13:16
 * @Version 1.0
 **/
public abstract class BaseDao {
    //使用dbUtils操作数据库
    private QueryRunner queryRunner=new QueryRunner();

    public int update(String sql,Object ...args){
        Connection connection= JdbcUtils.getConnection();

        try {
            return queryRunner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public <T> T queryForOne(Class<T>type,String sql,Object ...args){
        Connection connection=new JdbcUtils().getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    public <T> List<T> queryForList(Class<T>type, String sql, Object ...args){
        Connection connection=new JdbcUtils().getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Object queryForSingleValue(String sql,Object...args){
        Connection connection=JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}

~~~



### MVC架构

mvc三层架构：dao、service、servlet，三层。dao层负责数据库交互，service层负责业务处理，servlet层负责页面分发。

基本逻辑是：前端页面访问servlet获取服务。servlet通过service处理请求。service调用dao层获取数据。

dao层与数据库保持一致，service层负责需要处理的各项业务，实现例如查询全部图书、修改订单状态之类的功能。而servlet层从前端获取数据，调用service获得返回值，然后将结果以页面的形式展现



#### BaseServlet

BaseServlet是servlet层最核心的一个类，其他子类通过继承他，可以简化其他类的操作。通过反射实现对前端请求的响应。

~~~java
package com.atguigu.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @ClassName BaseServlet
 * @Description: TODO
 * @Author stary
 * @Date 2021/5/7 20:16
 * @Version 1.0
 **/
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //解决中文乱码
       req.setCharacterEncoding("UTF-8");
       resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action");
        //反射实现
        try {
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);//把异常抛给servlet程序
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       doPost(req,resp);

    }
}

~~~



#### WebUtils

WebUtils是一个工具类，其主要具有两项功能：

* 包装，即将前端传输过来的数据包装成bean类
* 转换，将前端传送过来的String类型变量转化为Integer类型，并设置默认值（0），避免空指针异常

~~~java
import com.atguigu.pojo.User;
//import com.sun.deploy.net.HttpResponse;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @ClassName WebUtils
 * @Description: TODO
 * @Author stary
 * @Date 2021/5/7 20:33
 * @Version 1.0
 **/
public class WebUtils {
    public static <T> T copyParamToBean(Map values, T bean){
        try {
            BeanUtils.populate(bean,values);

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return bean;
    }

    public static int parseInt(String strInt,int defaultValue){
        try {
            return Integer.parseInt(strInt);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return defaultValue;

    }
}

~~~



#### 过滤器与事务处理

在实际应用中，考虑到会出现许多问题，所以在对数据库进行操作的时候，需要进行事务管理，避免因为意外导致数据库错误，出现脏读、幻读等

其次，采用过滤器对权限进行管理。



# 设计实现

## 1. 准备

### 前端

打开idea，新建一个项目，注意命名方式，任何时候，命名方式都尽量保持无空格无中文

在新的项目下，新建一个模块，作为开发的主体

右键模块名，为模块添加框架支持

勾选web application ，为模块添加web支持。记得勾选创建 web.xml，这是web工程的配置文件，点击OK完成


导入前端文件，前端文件包括了页面（存储在page目录下），静态资源（jQuery、css、image等，存储在static下），首页index

至此，前端准备工作基本完成，后期根据需求进行修改

### 运行环境

接下来需要配置运行环境，用的是tomcat


先配置artifact，修改项目名称


接下来配置服务器，根据需求配置，建议修改为热部署，其他默认就好。


### 导入jar包

本次是在之前的基础上复盘，这里直接导入所有需要使用的jar包，在WEB-INF目录下，新建lib文件夹，导入需要使用的jar包，这里包括了后端前端所需要使用的jar包

选中全体jar包，右键，添加到库


最后，jar包出现可以展开的符号，表示添加成功


也可以使用File、Project Structure的方式导入jar包

运行tomcat，浏览器正确打开首页，


至此，前期准备工作完成



### mysql

前期准备基本完成，但是考虑到后面需要使用到数据库，也一并配置数据库

包括一个用户表和商品表

sql参考

~~~sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(11, 2) NULL DEFAULT NULL,
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sales` int NULL DEFAULT NULL,
  `stock` int NULL DEFAULT NULL,
  `img_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, 'java从入门到放弃', 80.00, '国哥', 10009, 9, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (2, '数据结构与算法', 78.50, '严敏君', 6, 15, 'static/img/default.jpg');

SET FOREIGN_KEY_CHECKS = 1;

~~~

~~~sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'admin', 'admin@atguigu.com');

SET FOREIGN_KEY_CHECKS = 1;
~~~

此外还需要存储购物车和订单等的数据表，此处略，后面添加



## 2. 登录与注册

登录注册功能，当前端点击登录、注册的按钮时，会触发相应的功能，详情见总体设计

### Dao

User类的主要字段，后期需要修改增加权限控制字段

~~~java
    private Integer id;

    private String username;
    private String password;
    private String email;
~~~

数据库中存在相同的表单项，保存用户的信息。注册是将前端传来的数据写入，登录则是比较前端数据与数据库是否一致。

Dao层需要实现的接口：

~~~java
public interface UserDao {
    //查询
    public User queryUserByUsername(String username);

    public User queryUserByUsernameAndPassword(String username,String password);

    //保存用户信息
    public int saveUser(User user);

}
~~~

### Service

service层需要实现的接口：

~~~java
    public void registUser(User user);    public User login(User user);    public boolean existsUsername(String username);
~~~

### servlet

servlet层，对于UserServlet，继承自BaseDao，提供三个服务，regist、login、loginOut。注意servlet需要在web.xml中注册。

判断验证码是否正确

~~~java
 //获取并删除验证码        String token=(String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);        String code = req.getParameter("code");            System.out.println("code="+code);            System.out.println("token="+token);
~~~

注意登录成功后，需要将数据回传给前端，登录注册失败、登录错误也需要回传数据



## 3. 页面优化

将前端的页面进行优化，抽取相同的页面元素放在common，便于后期维护

这部分主要是前端的工作，目前对前端技术掌握不是太好，只是有个基本了解，此处就不再具体赘述

## 4. 图书管理

### dao

Book的pojo类，数据库中同样要存在类似的表，用来存储图书信息

~~~java
public class Book {
    private Integer id;
    private String name;
    private String author;
    private BigDecimal price;
    private Integer sales;
    private Integer stock;
    private String imgPath="static/img/default.jpg";
}
~~~

Page的pojo类，用来实现分页功能

~~~java
public class Page <T>{

    public static final Integer page_size=4;
    //当前页码
    private Integer pageNo;
    //总页码
    private Integer pageTotal;
    //当前页显示数量
    private  Integer pageSize=page_size;
    //总记录数
    private Integer pageTotalCount;
    //当前页数据
    private List<T> items;
    //分页条地址
    private String url;
}
~~~



需要实现的dao接口

~~~java
import com.atguigu.pojo.Book;import java.util.List;public interface BookDao {    //添加图书    public int addBook(Book book);    //删除图书    public int deleteBookById(Integer id);    //更新书籍信息    public int updateBook(Book book);    //根据id查询    public Book queryBookById(Integer id);    //查询所有图书    public List<Book> queryBooks();    //查询分页数    Integer queryForPageTotalCount();    //查询指定区间的图书    List<Book> queryForPageItems(int begin, int pageSize);    //按价格区间查询分页数    Integer queryForPageTotalCountByPrice(int min, int max);    //查询指定价格区间中指定分页区间的图书    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);}
~~~

### service

~~~java
public interface BookService {    public void addBook(Book book);    public void deleteBookById(Integer id);    public void updateBook(Book book);    public Book queryBookById(Integer id);    public List<Book> queryBooks();    Page<Book> page(int pageNo, int pageSize);    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);}
~~~

### servlet

add、update、delete、list、getBook、page几个功能。其中page实现分页功能，实际应用的过程中，会替换掉list（查询全部书籍），getBook是获取要修改的图书的信息。

分页实现：

~~~java
//分页    protected void page(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {        //获取请求参数pageNo、pageSize        int pageNo= WebUtils.parseInt(req.getParameter("pageNo"),1);        int pageSize=WebUtils.parseInt(req.getParameter("pageSize"), Page.page_size);        //调用bookservice。page        Page<Book> page=bookService.page(pageNo,pageSize);        page.setUrl("client/clientbookServlet?action=page");        //保存page对象到request域中        req.setAttribute("page",page);        //请求转发        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);    }    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {        //获取请求参数pageNo、pageSize        int pageNo= WebUtils.parseInt(req.getParameter("pageNo"),1);        int pageSize=WebUtils.parseInt(req.getParameter("pageSize"), Page.page_size);        int min=WebUtils.parseInt(req.getParameter("min"),0);        int max=WebUtils.parseInt(req.getParameter("max"),Integer.MAX_VALUE);        //调用bookservice。page        Page<Book> page=bookService.pageByPrice(pageNo,pageSize,min,max);        StringBuilder sb=new StringBuilder("client/clientbookServlet?action=pageByPrice");        //若有min请求参数，追加到分页条地址请求参数中        if(req.getParameter("min")!=null){            sb.append("&min=").append(req.getParameter("min"));        }        //若有min请求参数，追加到分页条地址请求参数中        if(req.getParameter("max")!=null){            sb.append("&max=").append(req.getParameter("max"));        }        page.setUrl(sb.toString());        //保存page对象到request域中        req.setAttribute("page",page);        //请求转发        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);    }
~~~







## 5. 订单管理

订单管理模块又细分为购物车和订单管理

### dao

dao层有四个pojp类，分别归属于购物车和订单。

其中Cart记录购物车的基础信息，CartItem保存购物车中的详细商品项。

同理，Order记录订单基础信息，OrderItem保存订单中的详细商品项。

Cart

~~~java
public class Cart {
    private Integer totalCount;
    private BigDecimal totalPrice;
    private Map<Integer,CartItem> items=new LinkedHashMap<Integer,CartItem>();
~~~

CartItem

~~~java
public class CartItem {
    private Integer id;
    private String name;
    private Integer count;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
~~~

Order

~~~java
public class Order {
    private String orderId;
    private Date createTime;
    private BigDecimal price;
    //0未发货，1已发货，2表示已签收
    private Integer status=0;
    private Integer userId;
~~~

OrderItem

~~~java
 public class OrderItem {
    private Integer id;
    private String name;
    private Integer count;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String orderId;
 }
~~~

需要实现的接口：

由于购物车模块没有保存到数据库中，所以这里的购物车就不需要与数据库进行交互

OrderDao

~~~java
public interface OrderDao {
    public int saveOrder(Order order);
    public List<Order> queryOrders();
    public int changeOrderStatus(String status,String orderId);
    public List<Order> queryOrdersByUserId(Integer userId);

}
~~~

OrderItemDao

~~~java
public interface OrderItemDao {
    public int saveOrderItem(OrderItem orderItem);
    public List<OrderItem> queryOrderItemByOrderId(String orderId);


}

~~~

### service

service层也只有订单项一种服务

~~~java
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
~~~



注意生成订单的服务：

生成订单项，需要将购物车中的数据，转化为订单项的数据，保存到数据库后，清空购物车。

~~~java
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
~~~



### servlet

OrderServlet和CartServlet两个servlet，分别处理购物车和订单的请求

* CartServlet：addItem添加（被ajaxAddItem替换）、deleteItem删除、clear清空、updateCount修改数量
* OrderServlet：createOrder生成订单、showAllOrder查看所有订单、sendOrder发货、showOrderDetail查看订单详情、showMyOrder查看我的订单、receiveOrder签收订单

ajaxAddItem：

~~~java
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求参数、商品编号
        int id= WebUtils.parseInt(req.getParameter("id"),0);
        //调用bookservice。queryBookById，得到图书信息
        Book book = bookService.queryBookById(id);
        //把图书信息转换为cartItem信息
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

        //调用cart。addItem，添加商品项
        Cart cart=(Cart)req.getSession().getAttribute("cart");
        if(cart==null){
            cart=new Cart();
            req.getSession().setAttribute("cart",cart);

        }

        cart.addItem(cartItem);
        //保存最后一次添加的商品名称到session域
        req.getSession().setAttribute("lastName",cartItem.getName());
        //ajax返回
        Map<String ,Object> resultMap=new HashMap<String ,Object>();
        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());
        Gson gson=new Gson();
        String resultMapJsonString=gson.toJson(resultMap);
        resp.getWriter().write(resultMapJsonString);


    }

~~~





# 小结

## 总结

1. 泛型的使用。
2. 反射的应用。
3. 面向接口编程
4. 空指针异常
5. 前后端交互。json数据流与对象
6. MVC三层架构
7. 事务处理与权限管理（拦截器）
8. “导包”。使用功能前，必“导包”，包括jar包和html、jsp的引用


## 问题

### 时区报错，

修改配置文件：

~~~properties
url=jdbc:mysql://localhost:3306/book?serverTimezone=GMT%2B8
~~~



### tomcat乱码

修改配置文件。找到tomcat安装目录conf下logging.properties文件，打开后搜索：java.util.logging.ConsoleHandler.encoding修改为GBK


### 查询为空

查询时出现查出数据为空的情况：大概率是pojo类与数据表项名字不一致，解决方式：在sql语句中添加别名

foreach查询不出数据，在jsp中添加<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

