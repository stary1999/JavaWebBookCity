package com.atguigu.test;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BookDaoTest {
    private BookDao bookDao=new BookDaoImpl();

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null,"树上的","85a55",new BigDecimal(5888),1555,21,null));

    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(21);

    }

    @Test
    public void updateBook() {
        //记得改id为相应的id
        bookDao.updateBook(new Book(21,"一本测试的书","stary",new BigDecimal(5888),1555,21,null));


    }


    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(20));
    }

    @Test
    public void queryBooks() {
        for(Book queryBook:bookDao.queryBooks()){
            System.out.println(queryBook);
        }
    }
    @Test
    public void queryForPageItems() {
        System.out.println(bookDao.queryForPageItems(1,8));


    }
    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());


    }

    @Test
    public void queryForPageItemsByPrice() {
        for (Book book:bookDao.queryForPageItemsByPrice(0, Page.page_size,10,50)) {
            System.out.println(book);
        }


    }
    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10,50));


    }



}