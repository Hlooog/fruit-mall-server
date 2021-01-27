package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;

/**
 * 水果商品表(Commodity)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:34
 */
public interface CommodityService {

    R getList(Integer shopId, Integer cur, Integer size, String key);

    R delete(Integer commodityId);

}