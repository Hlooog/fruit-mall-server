package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hl
 * @create 2021/2/10 21:37
 */
@Data
public class ShopCarVO {
    private Integer id;
    private Integer shopId;
    private Integer commodityId;
    private Integer userId;
    private Integer infoId;
    private String shopName;
    private String commodityName;
    private String specification;
    private Float weight;
    private BigDecimal price;
    private Integer quantity;
    private Date createTime;
    private String url;

}
