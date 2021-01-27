package com.hl.fruitmall.entity.bean;

import lombok.Data;

/**
 * 水果图片表(CommodityImage)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:35
 */
@SuppressWarnings("serial")
@Data
public class CommodityImage{
    //主键id
    private Integer id;
    // 商品id
    private Integer commodityId;
    //图片地址
    private String url;




}