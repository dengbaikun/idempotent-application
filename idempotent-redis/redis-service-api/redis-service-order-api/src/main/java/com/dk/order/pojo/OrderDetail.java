package com.dk.order.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 22:53
 */
@Data
@Table(name = "tb_order_detail")
public class OrderDetail {

    @Id
    private String id;

    private String orderId;

    private String goodsId;

    private String goodsName;

    private Integer goodsNum;

    private Integer goodsPrice;
}
