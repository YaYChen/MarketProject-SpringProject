package com.springboot.project.mapper;

import com.springboot.project.entity.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Date;

public interface OrderMapper {

    @Select("Select * from order_table where user_id = #{userId} order by create_time desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "serial",column = "serial"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "createUser",column= "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.EAGER)),
            @Result(property = "status",column = "status"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "totalNumber",column = "total_number"),
            @Result(property = "orderItems",column = "id",
                    many = @Many(select = "com.springboot.project.mapper.OrderItemMapper.getOrderItems",fetchType = FetchType.EAGER))
    })
    List<Order> getAllOrder(int userId);

    @Select("select * from order_table where id = #{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "serial",column = "serial"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "createUser",column= "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.EAGER)),
            @Result(property = "status",column = "status"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "totalNumber",column = "total_number"),
            @Result(property = "orderItems",column = "id",
                    many = @Many(select = "com.springboot.project.mapper.OrderItemMapper.getOrderItems",fetchType = FetchType.EAGER))
    })
    Order getOrderById(int id);

    @Select("select * from order_table where serial = #{serial} and user_id = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "serial",column = "serial"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "createUser",column= "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.EAGER)),
            @Result(property = "status",column = "status"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "totalNumber",column = "total_number"),
            @Result(property = "orderItems",column = "id",
                    many = @Many(select = "com.springboot.project.mapper.OrderItemMapper.getOrderItems",fetchType = FetchType.EAGER))
    })
    Order getOne(String serial, int userId);

    @Select("Select * from order_table where create_time > #{start} and create_time <= #{end} and user_id = #{userId} order by create_time desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "serial",column = "serial"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "createUser",column= "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.EAGER)),
            @Result(property = "status",column = "status"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "totalNumber",column = "total_number"),
            @Result(property = "orderItems",column = "id",
                    many = @Many(select = "com.springboot.project.mapper.OrderItemMapper.getOrderItems",fetchType = FetchType.EAGER))
    })
    List<Order> searchByDate(Date start,Date end,int userId);

    @Insert("insert into order_table(serial,create_time,user_id,status,total_price,total_number) " +
            " values(#{serial}," +
            "#{createTime}," +
            "#{createUser.id}," +
            "#{status}," +
            "#{totalPrice}," +
            "#{totalNumber})")
    void insert(Order order);

    @Update("update order_table set " +
            "serial=#{serial}," +
            "create_time=#{createTime}," +
            "user_id=#{createUser.id}," +
            "status=#{status}," +
            "total_price=#{totalPrice}," +
            "total_number=#{totalNumber}," +
            "trade_no=#{tradeNo}" +
            " where id =#{id}")
    void update(Order order);

    @Delete("delete from order_table where id =#{id}")
    void delete(long id);
}
