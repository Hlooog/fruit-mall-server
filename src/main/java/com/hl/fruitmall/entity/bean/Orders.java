package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.util.Date;

/**
 * 订单表(Orders)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
@SuppressWarnings("serial")
@Data
public class Orders{
    //主键id
    private Integer id;
    //订单id
    private String orderId;
    //用户id
    private Integer userId;
    // 用户名
    private String userName;
    // 地址id
    private Integer addressId;
    // 订单状态
    private Integer status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}