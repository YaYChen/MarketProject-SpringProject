package com.springboot.project.mapper;

import com.springboot.project.entity.SalesVolume;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SalesVolumeMapper {


    @Select("SELECT product_table.id,sum(number) AS nums " +
            "FROM order_detail_table " +
            "LEFT JOIN product_table " +
            "ON order_detail_table.product_id=product_t" +
            "able.id " +
            "GROUP BY product_table.id " +
            "order by nums desc")
    @Results({
            @Result(property = "product",column = "id",
                    one=@One(select = "com.springboot.project.mapper.ProductMapper.getProductByID",fetchType = FetchType.LAZY)),
            @Result(property = "nums",column = "nums")
    })
    List<SalesVolume> getWholeSalesVolume();
}
