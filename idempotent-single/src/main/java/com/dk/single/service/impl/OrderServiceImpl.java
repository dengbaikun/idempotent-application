package com.dk.single.service.impl;

import com.dk.single.dao.OrderMapper;
import com.dk.single.pojo.Order;
import com.dk.single.service.OrderService;
import com.dk.single.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:12
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;


    /**
     * synchronized 锁失效 当前锁是对象本身 而事务是增强型代理对象 事务是之外
     * @param order
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized String addOrder(Order order) {

        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        //根据订单id查询订单信息
        Order orderResult = orderMapper.selectByPrimaryKey(order.getId());

        if (orderResult != null){

            return "repeat request";
        }

        int result = orderMapper.insert(order);
        if (result != 1){
            return "fail";
        }

        return "success";
    }
}
