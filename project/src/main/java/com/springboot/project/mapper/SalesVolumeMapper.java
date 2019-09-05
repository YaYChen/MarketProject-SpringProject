package com.springboot.project.mapper;

import com.springboot.project.entity.SalesVolume;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SalesVolumeMapper {


    @Select("select product_table.id,product_table.user_id,sum(number) as nums " +
            "from order_detail_table " +
            "left join product_table " +
            "on order_detail_table.product_id=product_table.id " +
            "where product_table.user_id = #{userId} "+
            "group by product_table.id " +
            "order by nums desc")
    @Results({
            @Result(property = "product",column = "id",
                    one=@One(select = "com.springboot.project.mapper.ProductMapper.getProductByID",fetchType = FetchType.LAZY)),
            @Result(property = "nums",column = "nums")
    })
    List<SalesVolume> getWholeSalesVolume(int userId);
}
