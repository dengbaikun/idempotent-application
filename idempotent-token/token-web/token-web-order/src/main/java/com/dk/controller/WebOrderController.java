package com.dk.controller;
import com.dk.order.feign.OrderFeign;
import com.dk.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 21:03
 */
@RestController
@RequestMapping("worder")
public class WebOrderController {

    @Autowired
    private OrderFeign orderFeign;

    @GetMapping("/genToken")
    public String genToken(){

        return orderFeign.genToken();
    }

    @GetMapping("/selectAll")
    public List<Order> selectAll() {
        return orderFeign.selectAll();
    }
    /**
     * 新增订单
     */
    @PostMapping("/addOrder")
    public String addOrder(@RequestBody Order order){

        String result = orderFeign.addOrder(order);

        return result;
    }

    @PostMapping("/addOrder2")
    public String addOrder2(@RequestBody Order order){
        return orderFeign.addOrder2(order);
    }
}

