package com.springboot.project.mapper;

import com.springboot.project.entity.Supplier;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SupplierMapper {

    @Select("select * from supplier_table where user_id = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "picture",column = "picture"),
            @Result(property = "userId",column = "user_id")
    })
    List<Supplier> selectAll(int userId);

    @Select("select * from supplier_table where id = #{id} and user_id = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "picture",column = "picture"),
            @Result(property = "userId",column = "user_id")
    })
    Supplier getSupplierByID(int id,int userId);

    @Insert("insert into supplier_table(name,brand,phone,picture,user_id) " +
            " values(#{name}," +
            "#{brand}," +
            "#{phone}," +
            "#{picture},"+
            "#{userId})")
    void insert(Supplier supplier);

    @Update("update supplier_table set " +
            "name=#{name}," +
            "brand=#{brand}," +
            "phone=#{phone}," +
            "picture=#{picture}," +
            "user_id=#{userId}" +
            " where id =#{id}")
    void update(Supplier supplier);

    @Delete("delete from supplier_table where id =#{id}")
    void delete(int id);

}
