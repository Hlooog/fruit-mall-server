package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2021/2/13 10:28
 */
@Data
public class UserOrderInfoPageVO{
    private String url;
    private Integer shopId;
    private Integer commodityId;
    private String shopName;
    private String commodityName;
    private BigDecimal price;
    private Integer quantity;
    private Integer status;
    private String specification;
    private Float weight;
    private String statusStr;
    private String trackNumber;
}