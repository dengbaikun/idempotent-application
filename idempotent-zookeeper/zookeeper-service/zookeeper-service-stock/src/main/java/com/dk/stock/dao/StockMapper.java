package com.dk.stock.dao;

import com.dk.stock.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:14
 */
public interface StockMapper extends Mapper<Stock> {

    @Update("update tb_stock set amount=amount-#{num} where goods_id=#{goodsId} and amount-#{num}>=0")
    int reduceStock(@Param("goodsId") String goodsId,@Param("num") Integer num);

    @Select("select * from tb_stock where goods_id = #{goodsId}")
    Stock findByGoodsId(@Param("goodsId") String goodsId);
}
