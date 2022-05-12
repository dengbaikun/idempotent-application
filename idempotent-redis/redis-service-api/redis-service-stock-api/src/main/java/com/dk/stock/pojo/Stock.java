package com.dk.stock.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 22:55
 */
@Data
@Table(name = "tb_stock")
public class Stock {

    @Id
    private String id;

    @Column(name = "goods_id")
    private String goodsId;

    @Column(name = "amount")
    private int amount;

    @Column(name="version")
    private int version;
}
