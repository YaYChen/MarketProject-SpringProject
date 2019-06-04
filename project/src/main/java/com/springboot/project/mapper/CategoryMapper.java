package com.springboot.project.mapper;

import com.springboot.project.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CategoryMapper {

    @Select("select * from category_table")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name")
    })
    List<Category> getAll();

    @Select("select * from category_table where id = #{id} ")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",  column = "name")
    })
    Category getCategory(int id);

    @Insert("insert into category_table(name)" +
            " values(#{name})")
    @SelectKey(statement="call identity()", keyProperty="id", before=false, resultType=int.class)
    int insert(Category category);

    @Update("update category_table set " +
            "name=#{name}," +
            " WHERE id =#{id}")
    void update(Category category);

    @Delete("delete from category_table where id =#{id}")
    void delete(int id);
}
