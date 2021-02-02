package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2021/2/2 19:25
 */
@Data
public class CommodityVO {
    private Integer id;
    private String name;
    private Integer varietyId;
    private String url;
    private String varietyName;
    private BigDecimal price;
    private String shopName;
}
