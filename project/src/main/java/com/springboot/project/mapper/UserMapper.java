package com.springboot.project.mapper;

import com.springboot.project.entity.LoginHistory;
import com.springboot.project.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Select("select * from login_history_table where user_id = #{userId} order by login_date desc limit 20")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "loginDate", column = "login_date")
    })
    List<LoginHistory> getAllLoginHistoryByUserId(int userId);

    @Insert("insert into login_history_table(user_id,login_date) " +
            " values(#{userId}," +
            " #{loginDate})" )
    void insertLoginHistory(LoginHistory history);

    @Select("select login_name,user_name,user_mobile,gen_time from user_table where id = #{userId}")
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userMobile", column = "user_mobile"),
            @Result(property = "genTime", column = "gen_time")
    })
    User getUserDetail(int userId);

    @Update("update user_table set " +
            "user_name=#{userName}," +
            "user_mobile=#{userMobile} " +
            "where id =#{id}")
    void updateUserDetail(User user);

}
