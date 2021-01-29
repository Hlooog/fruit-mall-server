package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.Shop;

/**
 * 店铺表(Shop)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
public interface ShopService {

    R page(Integer cur, String key, String startTime, String endTime, Integer cityId);

    Shop getShop(String field, Object value);

    R ban(Integer id, Integer days);

    R get(Integer id);
}