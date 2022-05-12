package com.dk.stock.service.impl;

import com.dk.order.pojo.OrderDetail;
import com.dk.stock.dao.StockFlowMapper;
import com.dk.stock.dao.StockMapper;
import com.dk.stock.pojo.Stock;
import com.dk.stock.pojo.StockFlow;
import com.dk.stock.service.StockService;
import com.dk.redis.util.IdWorker;
import com.dk.stock.util.RedissonLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private StockFlowMapper stockFlowMapper;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedissonLock redissonLock;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reduceStock(List<OrderDetail> orderDetailList, String flag) {
        //加锁
        try {

            redissonLock.addLock(flag);

            orderDetailList.stream().forEach(orderDetail -> {

                //扣减库存
                int reduceStockResult = stockMapper.reduceStock(orderDetail.getGoodsId(), orderDetail.getGoodsNum());
                if (reduceStockResult != 1){
                    //扣减库存失败
                    throw new RuntimeException("扣减库存失败");
                }

                //新增库存流水
                StockFlow stockFlow = new StockFlow();
                stockFlow.setId(String.valueOf(idWorker.nextId()));
                stockFlow.setFlag(flag);
                stockFlow.setGoodsId(orderDetail.getGoodsId());
                stockFlow.setNum(orderDetail.getGoodsNum());
                stockFlowMapper.insert(stockFlow);
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            redissonLock.releaseLock(flag);
        }
        return false;
    }

    @Override
    public Stock getStockInfo(String goodsId) {
        Stock stock = stockMapper.findByGoodsId(goodsId);
        return stock;
    }
}
