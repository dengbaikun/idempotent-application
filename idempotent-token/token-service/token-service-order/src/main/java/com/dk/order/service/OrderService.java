package com.dk.order.service;

import com.dk.order.pojo.Order;

import java.util.List;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-10 21:06
 */
public interface OrderService {

    int addOrder(Order order);


    List<Order> selectAll();
}
