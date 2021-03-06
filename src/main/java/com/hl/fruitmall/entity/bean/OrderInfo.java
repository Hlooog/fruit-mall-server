package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单详情表(OrderInfo)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:37
 */
@SuppressWarnings("serial")
@Data
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //订单id
    private String orderId;
    // 店铺id
    private Integer shopId;
    // 店铺名
    private String shopName;
    //水果id
    private Integer commodityId;
    //水果名字
    private String commodityName;
    //数量
    private Integer quantity;
    // 规格id
    private Integer infoId;
    // 状态
    private Integer status;
    // 订单号
    private String trackNumber;

}