package com.wpg.wpgblog.dao;

import com.wpg.wpgblog.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from tb_user where username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("select * from tb_user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from tb_user where email = #{email}")
    User findByEmail(@Param("email") String email);

    @Insert("insert into tb_user(email, password, username) values(#{email}, #{password}, #{nickname})")
    int addUser(User user);
}
