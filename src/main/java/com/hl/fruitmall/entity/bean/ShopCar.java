package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车表(ShopCar)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:40
 */
@SuppressWarnings("serial")
@Data
public class ShopCar implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //用户id
    private Integer userId;
    //购物车id
    private Integer shopId;
    //店铺名称
    private String shopName;
    //水果id
    private Integer commodityId;
    //水果名称
    private String commodityName;
    //详情id
    private Integer infoId;
    //数量
    private Integer quantity;
    //创建时间
    private Date createTime;
    // 图片地址
    private String url;

}