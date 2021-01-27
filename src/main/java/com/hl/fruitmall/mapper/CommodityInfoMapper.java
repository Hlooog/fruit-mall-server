package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.CommodityInfo;

/**
 * (CommodityInfo)表数据库访问层
 *
 * @author JiongFy
 * @since 2020-12-31 20:43:12
 */
public interface CommodityInfoMapper {
    CommodityInfo getInfo(Integer cur, Integer commodityId);

    Integer getTotal(Integer commodityId);
}