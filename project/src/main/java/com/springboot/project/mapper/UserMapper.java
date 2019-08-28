package com.springboot.project.mapper;

import com.springboot.project.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface UserMapper {

    @Select("select * from user_table")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "loginName",  column = "login_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userMobile", column = "user_mobile"),
            @Result(property = "genTime", column = "gen_time")
    })
    List<User> selectAll();

    @Select("select * from user_table where id = #{user_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "loginName",  column = "login_name"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userMobile", column = "user_mobile"),
            @Result(property = "genTime", column = "gen_time")
    })
    User getUserByID(int user_id);

    @Select("select * from user_table where login_name = #{loginName}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "loginName",  column = "login_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userMobile", column = "user_mobile"),
            @Result(property = "genTime", column = "gen_time")
    })
    User getUserByLoginName(String loginName);

    @Insert("insert into user_table(login_name,password,user_name,user_mobile,gen_time) " +
            " values(#{loginName}," +
            " #{password}," +
            " #{userName}," +
            " #{userMobile}," +
            " #{genTime})" )
    void insert(User user);

    @Update("update user_table set " +
            "login_name=#{userName}," +
            "password=#{password}," +
            "user_name=#{userName}," +
            "user_mobile=#{userMobile}," +
            "gen_time=#{genTime}" +
            " where id =#{id}")
    void update(User user);

    @Delete("delete from user_table where id =#{id}")
    void delete(int id);

}
