package com.dk.stock.service;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:44
 */
public interface StockService {

    /**
     * 扣减库存
     * @param goodsId
     * @param num
     * @return
     */
    int lessInventory(String goodsId,int num);

    int lessInventoryByVersion(String goodsId,int num,int version);

    int lessInventoryByVersionOut(String goodsId,int num);

}

