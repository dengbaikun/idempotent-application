package com.dk.stock.service;

import com.dk.stock.pojo.Stock;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:44
 */
public interface StockService {

    int reduceStock(String goodsId,Integer num,Integer version);

    int reduceStockNoLock(String goodsId,Integer num);

    Stock getStockInfo(String goodsId);
}
