package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2021/1/31 9:25
 */
@Data
public class CommodityInfoVO {
    private Integer id;
    private String specification;
    private Float weight;
    private Integer stock;
    private BigDecimal price;
}
