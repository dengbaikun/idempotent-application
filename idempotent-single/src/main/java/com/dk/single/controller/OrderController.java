package com.dk.single.controller;

import com.dk.single.pojo.Order;
import com.dk.single.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:07
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public String addOrder(@RequestBody Order order){

        if (order == null){
            return "illegal param";
        }

        return orderService.addOrder(order);
    }
}
