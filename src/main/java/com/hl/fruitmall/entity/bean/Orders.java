package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表(Orders)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
@SuppressWarnings("serial")
@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //订单id
    private String orderId;
    //用户id
    private Integer userId;
    // 地址id
    private Integer addressId;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}