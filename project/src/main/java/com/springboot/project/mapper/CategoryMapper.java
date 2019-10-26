package com.springboot.project.mapper;

import com.springboot.project.entity.Category;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface CategoryMapper {

    @Select("select * from category_table where user_id = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "userId",column = "user_id")
    })
    List<Category> getAll(int userId);

    @Select("select * from category_table where id = #{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "userId",column = "user_id")
    })
    Category getCategoryById(int id);

    @Insert("insert into category_table(name,user_id)" +
            " values(#{name},#{userId})")
    void insert(Category category);

    @Update("update category_table set " +
            "name=#{name}," +
            "user_id=#{userId}" +
            " WHERE id =#{id}")
    void update(Category category);

    @Delete("delete from category_table where id =#{id}")
    void delete(int id);
}
