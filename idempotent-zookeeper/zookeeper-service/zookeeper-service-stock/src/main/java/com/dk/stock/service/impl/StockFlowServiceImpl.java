package com.dk.stock.service.impl;

import com.dk.stock.dao.StockFlowMapper;
import com.dk.stock.pojo.StockFlow;
import com.dk.stock.service.StockFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 0:35
 */
@Service
public class StockFlowServiceImpl implements StockFlowService {

    @Autowired
    private StockFlowMapper stockFlowMapper;

    @Override
    public List<StockFlow> findByFlag(String flag) {

        return stockFlowMapper.findByFlag(flag);
    }
}
