package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车表(ShopCart)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:40
 */
@SuppressWarnings("serial")
@Data
public class ShopCart {
    //主键id
    private Integer id;
    //用户id
    private Integer userId;
    //购物车id
    private Integer shopId;
    //店铺名称
    private String shopName;
    //水果id
    private Integer fruitId;
    //水果名称
    private String fruitName;
    //价格
    private BigDecimal price;
    //数量
    private Integer quantity;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}