package com.dk.stock.service;

import com.dk.stock.pojo.StockFlow;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 0:35
 */
public interface StockFlowService {

    List<StockFlow> findByFlag(String flag);
}
