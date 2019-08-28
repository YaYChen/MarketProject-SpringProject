package com.springboot.project.mapper;

import com.springboot.project.entity.LoginHistory;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface LoginHistoryMapper {

    @Select("select * from login_history_table where user_id = #{user_id} order by login_date desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "loginUser",column = "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.EAGER)),
            @Result(property = "loginDate",column = "login_date")
    })
    List<LoginHistory> getAllLoginHistoryByUserId(int userId);

    @Insert("insert into login_history_table(user_id,login_date) " +
            " values(#{loginUser.id}," +
            " #{loginDate})" )
    void insertLoginHistory(LoginHistory history);

}
