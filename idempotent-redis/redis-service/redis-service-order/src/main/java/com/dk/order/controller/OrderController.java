package com.dk.order.controller;

import com.alibaba.fastjson.JSON;
import com.dk.order.pojo.Order;
import com.dk.order.pojo.OrderDetail;
import com.dk.order.service.OrderDetailService;
import com.dk.order.service.OrderService;
import com.dk.stock.feign.StockFeign;
import com.dk.stock.feign.StockFlowFeign;
import com.dk.redis.annotation.Idempotent;
import com.dk.redis.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:08
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

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private StockFeign stockFeign;

    @Autowired
    private StockFlowFeign stockFlowFeign;

    /**
     * 获取令牌
     * @return
     */
    @GetMapping("/genToken")
    public String genToken(){
        String token = String.valueOf(idWorker.nextId());
        redisTemplate.opsForValue().set(token,0,30, TimeUnit.MINUTES);
        return token;
    }

    ExecutorService executor = Executors.newFixedThreadPool(100);

    /**
     * 生成订单
     * @param order
     * @return
     */
    @Idempotent
    @PostMapping("/genOrder")
    public String genOrder(@RequestBody Order order) throws InterruptedException {

        //新增订单
        String orderId = String.valueOf(idWorker.nextId());
        order.setId(orderId);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.addOrder(order);

        //新增订单明细
        List<String> goodsIds = JSON.parseArray(order.getGoodsIds(), String.class);
        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (String goodsId : goodsIds) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(String.valueOf(idWorker.nextId()));
            orderDetail.setOrderId(orderId);
            orderDetail.setGoodsId(goodsId);
            orderDetail.setGoodsPrice(1);
            orderDetail.setGoodsNum(1);
            orderDetailService.addOrderDetail(orderDetail);

            orderDetailList.add(orderDetail);
        }

        //生成操作标识并存入redis
        redisTemplate.opsForValue().set(orderId,"orderId",30,TimeUnit.MINUTES);

        //扣减库存
        stockFeign.reduceStock(JSON.toJSONString(orderDetailList),orderId);

        //数据回查
        if (stockFlowFeign.findByFlag(orderId).size() >0){
            //操作成功
            return "success";
        }else {
            TimeUnit.SECONDS.sleep(3);
            //异步查询处理结果
            CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()-> stockFlowFeign.findByFlag(orderId).size(),executor);
            try {
                if (future1.get()>0){
                    return "success";
                }
            }catch (Exception e){
                throw new RuntimeException("执行有误");
            }

            TimeUnit.SECONDS.sleep(5);
            CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(()-> stockFlowFeign.findByFlag(orderId).size(),executor);
            try {
                if (future2.get()>0){
                    return "success";
                }
            }catch (Exception e){
                throw new RuntimeException("执行有误");
            }

            TimeUnit.SECONDS.sleep(10);
            CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(()-> stockFlowFeign.findByFlag(orderId).size(),executor);
            try {
                if (future3.get()>0){
                    return "success";
                }
            }catch (Exception e){
                throw new RuntimeException("执行有误");
            }
            return "false";
        }
    }
}
