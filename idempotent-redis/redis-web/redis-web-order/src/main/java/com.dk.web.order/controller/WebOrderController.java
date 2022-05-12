package com.dk.web.order.controller;

import com.dk.order.feign.OrderFeign;
import com.dk.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:20
 */
@RestController
@RequestMapping("/worder")
public class WebOrderController {

    @Autowired
    private OrderFeign orderFeign;

    @GetMapping("/genToken")
    public String genToken(){
        return orderFeign.genToken();
    }

    @PostMapping("/genOrder")
    public String genToken(@RequestBody Order order){
        return orderFeign.genOrder(order);
    }
}
