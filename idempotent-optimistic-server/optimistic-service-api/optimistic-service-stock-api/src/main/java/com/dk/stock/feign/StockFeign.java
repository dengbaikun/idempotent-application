package com.dk.stock.feign;

import com.dk.stock.pojo.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 22:55
 */
@FeignClient(name = "stock")
@RequestMapping("/stock")
public interface StockFeign {


    @PutMapping("/reduceStockNoLock/{goodsId}/{num}")
    public String reduceStockNoLock(@PathVariable("goodsId") String goodsId,
                                    @PathVariable("num") Integer num);


    @PutMapping("/reduceStock/{goodsId}/{num}/{version}")
    public int reduceStock(@PathVariable("goodsId") String goodsId,
                           @PathVariable("num") Integer num,
                           @PathVariable("version") Integer version);

    @GetMapping("/getStockInfo/{goodsId}")
    public Stock getStockInfo(@PathVariable("goodsId") String goodsId);

    @GetMapping("/demo")
    public void demo();
}
