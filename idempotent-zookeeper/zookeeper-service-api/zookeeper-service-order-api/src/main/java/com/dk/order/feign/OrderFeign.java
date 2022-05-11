package com.dk.order.feign;

import com.dk.order.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 22:52
 */
@FeignClient(name = "order")
@RequestMapping("/order")
public interface OrderFeign {

    @GetMapping("/genToken")
    public String genToken();

    @PostMapping("/genOrder")
    public String genOrder(@RequestBody Order order);
}