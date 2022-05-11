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
@Table(name = "tb_order")
public class Order {

    @Id
    private String id;

    private Integer totalNum;

    private Integer payMoney;

    private java.util.Date createTime;
    private java.util.Date updateTime;

    private String goodsIds;
}