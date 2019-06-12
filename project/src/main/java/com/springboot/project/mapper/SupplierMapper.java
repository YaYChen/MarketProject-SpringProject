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
            @Result(property = "createUser",column = "user_id",
                    one=@One(select = "com.springboot.project.mapper.CategoryMapper.getCategory",fetchType = FetchType.EAGER))
    })
    List<Supplier> selectAll(int userId);

    @Select("select * from supplier_table where id = #{supplier_id} and user_id = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "picture",column = "picture"),
            @Result(property = "createUser",column = "user_id",
                    one=@One(select = "com.springboot.project.mapper.CategoryMapper.getCategory",fetchType = FetchType.EAGER))
    })
    Supplier getSupplierByID(int supplier_id,int userId);

    @Insert("insert into supplier_table(name,brand,phone,picture,user_id) " +
            " values(#{name}," +
            "#{brand}," +
            "#{phone}," +
            "#{picture},"+
            "#{createUser.id})")
    @SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=int.class)
    int insert(Supplier supplier);

    @Update("update supplier_table set " +
            "name=#{name}," +
            "brand=#{brand}," +
            "phone=#{phone}," +
            "picture=#{picture}," +
            "user_id=#{createUser.id}" +
            " where id =#{id}")
    void update(Supplier supplier);

    @Delete("delete from supplier_table where id =#{id}")
    void delete(int id);

}
