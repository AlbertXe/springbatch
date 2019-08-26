package com.demo.dao;

import com.demo.pojo.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface BookDao {
    //    @Delete("delete from book where id =#{id}")
    void deleteById(@Param("id") int id);
    @Select("select * from Book where id = #{id}")

    Book findById(@Param("id") int id);
}
