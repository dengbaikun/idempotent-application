package com.dk.order.service.impl;

import com.dk.order.dao.OrderMapper;
import com.dk.order.pojo.Order;
import com.dk.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 21:06
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public int addOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }
}
