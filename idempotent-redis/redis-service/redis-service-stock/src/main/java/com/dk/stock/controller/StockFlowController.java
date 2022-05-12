package com.dk.stock.controller;

import com.dk.stock.pojo.StockFlow;
import com.dk.stock.service.StockFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 0:34
 */
@RestController
@RequestMapping("/stockFlow")
public class StockFlowController {

    @Autowired
    private StockFlowService stockFlowService;


    @GetMapping("/findByFlag")
    public List<StockFlow> findByFlag(@RequestParam String flag){

        return stockFlowService.findByFlag(flag);
    }
}

