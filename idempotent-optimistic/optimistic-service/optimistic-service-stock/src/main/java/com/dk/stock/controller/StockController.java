package com.dk.stock.controller;

import com.dk.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:43
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PutMapping("/lessInventory/{goodsId}/{num}")
    public String lessInventory(@PathVariable("goodsId") String goodsId, @PathVariable("num") int num){

        stockService.lessInventory(goodsId, num);

        return "success";
    }

    @PutMapping("/lessInventoryByVersion/{goodsId}/{num}/{version}")
    public String lessInventoryByVersion(@PathVariable("goodsId") String goodsId,@PathVariable("num") int num,@PathVariable("version") int version){
        stockService.lessInventoryByVersion(goodsId, num, version);
        return "success";
    }

    @PutMapping("/lessInventoryByVersionOut/{goodsId}/{num}")
    public String lessInventoryByVersionOut(@PathVariable("goodsId") String goodsId,@PathVariable("num") int num){

        int result = stockService.lessInventoryByVersionOut(goodsId, num);
        if (result == 1){
            System.out.println("购买成功");
            return "success";
        }

        System.out.println("购买失败");
        return "fail";
    }
}

