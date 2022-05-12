package com.dk.stock.feign;

import com.dk.stock.pojo.StockFlow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 0:32
 */
@FeignClient(name = "stock")
@RequestMapping("/stockFlow")
public interface StockFlowFeign {

    @GetMapping("/findByFlag")
    public List<StockFlow> findByFlag(@RequestParam String flag);
}
