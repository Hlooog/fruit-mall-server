package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (CommodityInfo)表实体类
 *
 * @author Hl
 * @since 2020-12-31 20:43:11
 */
@SuppressWarnings("serial")
@Data
public class CommodityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //商品id
    private Integer commodityId;
    //规格
    private String specification;
    //重量 单位（kg)
    private Float weight;
    //库存
    private Integer stock;
    //单价
    private BigDecimal price;
}