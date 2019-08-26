package com.demo.controller;

import com.demo.dao.BookDao;
import com.demo.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookController {
    @Autowired
    BookDao bookDao;

    /**
     * id 查询
     * @param id
     */
    @RequestMapping("/book.do")
//    @LoginCheck
    public void getBook(int id){
        System.out.println("init");
        Book book = bookDao.findById(id);
        System.out.println(book);
    }

    @RequestMapping("/deleteBook.do")
    public void deleteById(int id) {
        bookDao.deleteById(id);
        System.out.println(id);
    }
}
