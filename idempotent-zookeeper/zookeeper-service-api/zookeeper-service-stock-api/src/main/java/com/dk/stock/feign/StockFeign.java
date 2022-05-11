package com.dk.stock.feign;

import com.dk.stock.pojo.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 22:55
 */
@FeignClient(name = "stock")
@RequestMapping("/stock")
public interface StockFeign {


    @PutMapping("/reduceStock/{flag}")
    public void reduceStock(@RequestParam String orderListValue, @PathVariable("flag") String flag);
}
