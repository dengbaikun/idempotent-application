package com.dk.stock.service.impl;

import com.dk.stock.dao.StockMapper;
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

    /**
     * 锁失效 锁的对象不是事务对象
     * @param goodsId
     * @param num
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int lessInventory(String goodsId, int num) {
        return stockMapper.lessInventory(goodsId, num);
    }

    /**
     * 版本控制
     * @param goodsId
     * @param num
     * @param version
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int lessInventoryByVersion(String goodsId, int num, int version) {
        return stockMapper.lessInventoryByVersion(goodsId, num, version);
    }

    /**
     * 防止超卖
     * @param goodsId
     * @param num
     * @return
     */
    @Override
    public int lessInventoryByVersionOut(String goodsId, int num) {
        return stockMapper.lessInventoryByVersionOut(goodsId, num);
    }


}
