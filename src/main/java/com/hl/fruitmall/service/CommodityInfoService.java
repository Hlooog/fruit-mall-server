package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;

/**
 * (CommodityInfo)表服务接口
 *
 * @author Hl
 * @since 2020-12-31 20:43:12
 */
public interface CommodityInfoService {

    R getInfo(Integer commodityId, Integer cur);
}