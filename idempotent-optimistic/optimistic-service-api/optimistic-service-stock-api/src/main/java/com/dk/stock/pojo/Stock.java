package com.dk.stock.pojo;

import lombok.Data;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 21:37
 */
@Data
public class Stock {

    private String id;

    private String goodsId;

    private int amount;
}