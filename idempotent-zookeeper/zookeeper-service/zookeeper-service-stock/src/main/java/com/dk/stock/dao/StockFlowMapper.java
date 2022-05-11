package com.dk.stock.dao;

import com.dk.stock.pojo.StockFlow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 0:34
 */
public interface StockFlowMapper extends Mapper<StockFlow> {

    @Select("select * from tb_stock_flow where flag = #{flag}")
    List<StockFlow> findByFlag(@Param("flag") String flag);
}
