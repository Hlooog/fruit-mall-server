package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2021/1/31 23:01
 */
@Data
public class EditCommodityInfoVO {
    private Integer id;
    private Integer commodityId;
    private Integer shopId;
    private String specification;
    private Float weight;
    private Integer stock;
    private BigDecimal price;
}
