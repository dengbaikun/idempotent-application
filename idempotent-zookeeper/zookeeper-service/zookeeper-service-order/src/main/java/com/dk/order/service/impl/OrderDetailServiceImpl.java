package com.dk.order.service.impl;

import com.dk.order.dao.OrderDetailMapper;
import com.dk.order.pojo.OrderDetail;
import com.dk.order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:10
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional
    public int addOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail);
    }
}
