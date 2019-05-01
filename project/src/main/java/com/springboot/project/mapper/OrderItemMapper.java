package com.springboot.project.mapper;

import com.springboot.project.entity.OrderItem;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface OrderItemMapper {

    @Select("select * from order_detail_table where order_id = #{order_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "orderID",column = "order_id"),
            @Result(property = "product",column = "product_id",
                    one=@One(select = "com.springboot.project.mapper.ProductMapper.getProductByID",fetchType = FetchType.EAGER)),
            @Result(property = "quantity",column = "number"),
            @Result(property = "totalPrice",column = "total_price")
    })
    List<OrderItem> getOrderItems(int order_id);

    @Insert("insert into order_detail_table(order_id,product_id,number,total_price) " +
            " values(#{orderID}," +
            " #{product.id}," +
            " #{quantity}," +
            " #{totalPrice})")
    void insert(OrderItem orderItem);

    @Update("update order_detail_table set " +
            "order_id=#{orderID}," +
            "product_id=#{product.id}," +
            "number=#{quantity}" +
            "total_Price=#{totalPrice}" +
            " where id =#{id}")
    void update(OrderItem orderItem);

    @Delete("delete from order_detail_table where id =#{id}")
    void delete(long id);
}
