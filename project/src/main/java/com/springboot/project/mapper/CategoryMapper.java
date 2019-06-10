package com.springboot.project.mapper;

import com.springboot.project.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CategoryMapper {

    @Select("select * from category_table where userId = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "userId",column = "userId")
    })
    List<Category> getAll(int userId);

    @Select("select * from category_table where id = #{id} and userId = #{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name"),
            @Result(property = "userId",column = "userId")
    })
    Category getCategory(int id,int userId);

    @Insert("insert into category_table(name,userId)" +
            " values(#{name,userId})")
    @SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=int.class)
    int insert(Category category);

    @Update("update category_table set " +
            "name=#{name}," +
            " WHERE id =#{id}")
    void update(Category category);

    @Delete("delete from category_table where id =#{id}")
    void delete(int id);
}
