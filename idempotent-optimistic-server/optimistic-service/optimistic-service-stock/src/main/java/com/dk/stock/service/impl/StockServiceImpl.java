package com.dk.stock.service.impl;

import com.dk.stock.dao.StockMapper;
import com.dk.stock.pojo.Stock;
import com.dk.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:44
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reduceStock(String goodsId, Integer num, Integer version) {
        return stockMapper.reduceStock(goodsId, num, version);
    }

    @Override
    @Transactional
    public int reduceStockNoLock(String goodsId, Integer num) {
        return stockMapper.reduceStockNoLock(goodsId,num);
    }

    @Override
    public Stock getStockInfo(String goodsId) {
        Stock stock = stockMapper.findByGoodsId(goodsId);
        return stock;
    }
}
