package com.springboot.project.mapper;

import com.springboot.project.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ProductMapper {

    @Select("select * from product_table where id = #{product_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "code",column = "code"),
            @Result(property = "name",column = "name"),
            @Result(property = "category",column= "category_id",
                    one=@One(select = "com.springboot.project.mapper.CategoryMapper.getCategoryById",fetchType = FetchType.EAGER)),
            @Result(property = "specification",column = "specification"),
            @Result(property = "productPicture",column = "productPicture"),
            @Result(property = "purchasePrice",column = "purchasePrice"),
            @Result(property = "price",column = "price"),
            @Result(property = "createUser",column = "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.EAGER))
    })
    Product getProductByID(int product_id);

    @Select("select * from product_table where code = #{code}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "code",column = "code"),
            @Result(property = "name",column = "name"),
            @Result(property = "category",column= "category_id",
                    one=@One(select = "com.springboot.project.mapper.CategoryMapper.getCategoryById",fetchType = FetchType.DEFAULT)),
            @Result(property = "specification",column = "specification"),
            @Result(property = "productPicture",column = "productPicture"),
            @Result(property = "purchasePrice",column = "purchasePrice"),
            @Result(property = "price",column = "price"),
            @Result(property = "createUser",column = "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.DEFAULT))
    })
    List<Product> getProductByCode(String code);

    @Select("select * from product_table where category_id = #{category_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "code",column = "code"),
            @Result(property = "name",column = "name"),
            @Result(property = "category",column= "category_id",
                    one=@One(select = "com.springboot.project.mapper.CategoryMapper.getCategoryById",fetchType = FetchType.DEFAULT)),
            @Result(property = "specification",column = "specification"),
            @Result(property = "productPicture",column = "productPicture"),
            @Result(property = "purchasePrice",column = "purchasePrice"),
            @Result(property = "price",column = "price"),
            @Result(property = "createUser",column = "user_id",
                    one=@One(select = "com.springboot.project.mapper.UserMapper.getUserByID",fetchType = FetchType.DEFAULT))
    })
    List<Product> getProductsByCategory(int category_id);

    @Insert("insert into product_table(code,name,category_id,specification,productPicture,purchasePrice,price,user_id) " +
            " values(#{code}," +
            "#{name}," +
            "#{category.id}," +
            "#{specification}," +
            "#{productPicture}," +
            "#{purchasePrice}," +
            "#{price},"+
            "#{createUser.id})")
    @SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=int.class)
    int insert(Product product);

    @Update("update product_table set " +
                    "code=#{code}," +
                    "name=#{name}," +
                    "specification=#{specification}," +
                    "productPicture=#{productPicture}," +
                    "purchasePrice=#{purchasePrice}," +
                    "price=#{price}," +
                    "category_id=#{category.id}," +
                    "user_id=#{createUser.id}," +
                    " where id =#{id}")
    void update(Product product);

    @Delete("delete from product_table where id =#{id}")
    void delete(int id);

}
