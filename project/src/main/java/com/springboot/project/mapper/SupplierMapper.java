package com.springboot.project.mapper;

import com.springboot.project.entity.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SupplierMapper {

    @Select("select * from supplier_table")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "picture",column = "picture")
    })
    List<Supplier> selectAll();

    @Select("select * from supplier_table where id = #{supplier_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "picture",column = "picture")
    })
    Supplier getSupplierByID(int supplier_id);

    @Insert("insert into supplier_table(name,brand,phone,picture) " +
            " values(#{name}," +
            " #{brand}," +
            " #{phone}," +
            " #{picture})" )
    @SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=int.class)
    int insert(Supplier supplier);

    @Update("update supplier_table set " +
            "name=#{name}," +
            "brand=#{brand}," +
            "phone=#{phone}," +
            "picture=#{picture}" +
            " where id =#{id}")
    void update(Supplier supplier);

    @Delete("delete from supplier_table where id =#{id}")
    void delete(int id);

}
