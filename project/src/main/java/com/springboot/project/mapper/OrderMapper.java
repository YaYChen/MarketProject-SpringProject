package com.springboot.project.mapper;

import com.springboot.project.entity.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Date;

public interface OrderMapper {

    @Select("select * from order_table where serial = #{serial}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "serial",column = "serial"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "createUserID",column= "user_id"),
            @Result(property = "status",column = "status"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "totalNumber",column = "total_number"),
            @Result(property = "orderItems",column = "id",
                    many = @Many(select = "com.springboot.project.mapper.OrderItemMapper.getOrderItems",fetchType = FetchType.EAGER))
    })
    Order getOne(String serial);

    @Select("Select * from order_table where create_time > #{start} and create_time <= #{end} order by create_time desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "serial",column = "serial"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "createUserID",column= "user_id"),
            @Result(property = "status",column = "status"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "totalNumber",column = "total_number"),
            @Result(property = "orderItems",column = "id",
                    many = @Many(select = "com.springboot.project.mapper.OrderItemMapper.getOrderItems",fetchType = FetchType.EAGER))
    })
    List<Order> searchByDate(Date start,Date end);

    @Select("select * from order_table where user_id = #{user_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "serial",column = "serial"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "createUserID",column= "user_id"),
            @Result(property = "status",column = "status"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "totalNumber",column = "total_number"),
            @Result(property = "orderItems",column = "id",
                    many = @Many(select = "com.springboot.project.mapper.OrderItemMapper.getOrderItems",fetchType = FetchType.EAGER))
    })
    List<Order> searchByUser(int user_id);

    @Insert("insert into order_table(serial,create_time,user_id,status,total_price,total_number) " +
            " values(#{serial}," +
            " #{createTime}," +
            " #{createUserID}," +
            " #{status}," +
            " #{totalPrice}," +
            " #{totalNumber})")
    void insert(Order order);

    @Update("update order_table set " +
            "serial=#{serial}," +
            "create_time=#{createTime}," +
            "user_id=#{createUserID}," +
            "status=#{status}," +
            "total_price=#{totalPrice}," +
            "total_number=#{totalNumber}" +
            " where id =#{id}")
    void update(Order order);

    @Delete("delete form order_table where id =#{id}")
    void delete(long id);
}
