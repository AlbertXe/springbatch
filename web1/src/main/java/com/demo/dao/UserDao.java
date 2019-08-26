package com.demo.dao;

import com.demo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    @Insert("insert into tb_user_inf (user_name,pwd,mobile,card_num,add_date,add_time) values (#{userName},#{pwd},#{mobile},#{cardNum},#{addDate},#{addTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
}
