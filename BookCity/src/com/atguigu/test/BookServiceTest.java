package com.atguigu.test;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BookServiceTest {
    private BookService bookService=new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"bookservice测试","stary",new BigDecimal(41),55,21,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(22,"bookservice测试2","stary",new BigDecimal(41),55,21,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(22));
    }

    @Test
    public void queryBooks() {
        for(Book book:bookService.queryBooks())
        System.out.println(book);
    }
    @Test
    public void page(){
        System.out.println(bookService.page(1, Page.page_size));
    }

    @Test
    public void pageByPrice(){
        System.out.println(bookService.pageByPrice(1, Page.page_size,10,50));
    }
}