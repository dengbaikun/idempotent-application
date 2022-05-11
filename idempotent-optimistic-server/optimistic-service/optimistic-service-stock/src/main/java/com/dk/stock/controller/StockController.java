package com.dk.stock.controller;

import com.dk.stock.pojo.Stock;
import com.dk.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:13
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/demo")
    public void demo() throws InterruptedException {
        System.out.println("stock demo info");
        TimeUnit.SECONDS.sleep(6000);

    }

    @PutMapping("/reduceStockNoLock/{goodsId}/{num}")
    public String reduceStockNoLock(@PathVariable("goodsId") String goodsId,
                                    @PathVariable("num") Integer num) throws InterruptedException {

        System.out.println("reduce stock");
        int result = stockService.reduceStockNoLock(goodsId, num);

        if (result != 1){
            return "reduce stock fail";
        }

        //延迟
        TimeUnit.SECONDS.sleep(6000);
        return "reduce stock success";
    }

    /**
     * 获取库存信息
     * @param goodsId
     * @return
     */
    @GetMapping("/getStockInfo/{goodsId}")
    public Stock getStockInfo(@PathVariable("goodsId") String goodsId){

        return stockService.getStockInfo(goodsId);
    }

    /**
     * 乐观锁扣减库存
     * @param goodsId
     * @param num
     * @param version
     * @return
     */
    @PutMapping("/reduceStock/{goodsId}/{num}/{version}")
    public int reduceStock(@PathVariable("goodsId") String goodsId,
                           @PathVariable("num") Integer num,
                           @PathVariable("version") Integer version) throws InterruptedException {

        System.out.println("exec reduce stock");
        int result = stockService.reduceStock(goodsId, num, version);
        if (result != 1){
            //扣减失败
            return result;
        }
        //延迟
        TimeUnit.SECONDS.sleep(6000);
        return result;
    }
}

