package com.springboot.project.mapper;

import com.springboot.project.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user_table")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",  column = "name"),
            @Result(property = "password", column = "password")
    })
    List<User> selectAll();

    @Select("select * from user_table where id = #{user_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "name"),
            @Result(property = "password", column = "password")
    })
    User getUserByID(int user_id);

    @Select("select * from user_table where name = #{name}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "name"),
            @Result(property = "password", column = "password")
    })
    User getUserByName(String name);

    @Insert("insert into user_table(name,password) " +
            " values(#{username}," +
            " #{password})" )
    void insert(User user);

    @Update("update user_table set " +
            "name=#{username}," +
            "password=#{password}" +
            " where id =#{id}")
    void update(User user);

    @Delete("delete from user_table where id =#{id}")
    void delete(int id);
}
