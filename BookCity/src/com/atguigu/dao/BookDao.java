package com.atguigu.dao;

import com.atguigu.pojo.Book;

import java.util.List;

public interface BookDao {
    //添加图书
    public int addBook(Book book);
    //删除图书
    public int deleteBookById(Integer id);
    //更新书籍信息
    public int updateBook(Book book);
    //根据id查询
    public Book queryBookById(Integer id);
    //查询所有图书
    public List<Book> queryBooks();
    //查询分页数
    Integer queryForPageTotalCount();
    //查询指定区间的图书
    List<Book> queryForPageItems(int begin, int pageSize);
    //按价格区间查询分页数
    Integer queryForPageTotalCountByPrice(int min, int max);

    //查询指定价格区间中指定分页区间的图书
    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);
}
