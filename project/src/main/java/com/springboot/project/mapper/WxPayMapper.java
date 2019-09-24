package com.springboot.project.mapper;

import com.springboot.project.entity.WxPay;
import org.apache.ibatis.annotations.*;

public interface WxPayMapper {

    @Select("select * from wxpay_table where user_id = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "userId",  column = "user_id"),
            @Result(property = "appId", column = "app_id"),
            @Result(property = "key", column = "key"),
            @Result(property = "mchId", column = "mch_id"),
            @Result(property = "certFile", column = "cert_file")
    })
    WxPay getWxPayByUserID(int userId);

    @Insert("insert into wxpay_table(user_id,app_id,key,mch_id,cert_file) " +
            " values(#{userId}," +
            " #{appId}," +
            " #{key}," +
            " #{mchId}," +
            " #{certFile})" )
    void insert(WxPay wxPay);

    @Delete("delete from wxpay_table where id =#{id}")
    void delete(int id);

    @Update("update wxpay_table set " +
            "app_id = #{user_id}," +
            "key = #{key}, " +
            "mch_id = #{mchId}, " +
            "cert_file = #{certFile} " +
            "where id = #{id}")
    void update(WxPay wxPay);
}
