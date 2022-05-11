package com.dk.order.feign;

import com.dk.order.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 21:06
 */
@FeignClient(name = "order")
@RequestMapping("/order")
public interface OrderFeign {

    @GetMapping("/genToken")
    public String genToken();

    @PostMapping("/addOrder")
    public String addOrder(@RequestBody Order order);

    @PostMapping("/addOrder2")
    public String addOrder2(@RequestBody Order order);


    @GetMapping("/selectAll")
    public List<Order> selectAll() ;
}
