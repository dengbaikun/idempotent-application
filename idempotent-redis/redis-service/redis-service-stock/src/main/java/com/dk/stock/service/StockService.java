package com.dk.stock.service;

import com.dk.order.pojo.OrderDetail;
import com.dk.stock.pojo.Stock;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:44
 */
public interface StockService {

    Boolean reduceStock(List<OrderDetail> orderDetailList, String flag);

    Stock getStockInfo(String goodsId);
}
