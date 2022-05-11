package com.dk.stock.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 0:32
 */
@Data
@Table(name = "tb_stock_flow")
public class StockFlow {

    @Id
    private String id;

    private String goodsId;

    private Integer num;

    private String flag;
}
