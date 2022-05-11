package com.dk.order.controller;

import com.dk.annotation.Idempotent;
import com.dk.order.pojo.Order;
import com.dk.order.service.OrderService;
import com.dk.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 21:05
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/genToken")
    public String genToken(){

        //生成token
        String token = String.valueOf(idWorker.nextId());

        //token存入到redis中
        redisTemplate.opsForValue().set(token,"0",30, TimeUnit.MINUTES);

        return token;
    }
    @GetMapping("/selectAll")
    public List<Order> selectAll() {
        return orderService.selectAll();
    }

    @PostMapping("/addOrder")
    public String addOrder(@RequestBody Order order, HttpServletRequest request){

        //获取令牌
        String token = request.getHeader("token");

        //校验令牌
        try{
            if (redisTemplate.delete(token)){
                //是正常请求
                order.setId(String.valueOf(idWorker.nextId()));
                order.setCreateTime(new Date());
                order.setUpdateTime(new Date());
                int result = orderService.addOrder(order);

                if (result == 1){
                    System.out.println("success");
                    return "success";
                }else {
                    System.out.println("fail");
                    return "fail";
                }
            }else {
                //重复请求
                System.out.println("repeat request");
                return "repeat request";
            }
        } catch (Exception e){
            throw new RuntimeException("系统异常，请重试");
        }
    }

    @Idempotent
    @PostMapping("/addOrder2")
    public String addOrder2(@RequestBody Order order){
        order.setId(String.valueOf(idWorker.nextId()));
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        int result = orderService.addOrder(order);

        if (result == 1){
            System.out.println("success");
            return "success";
        }else {
            System.out.println("fail");
            return "fail";
        }
    }

}
