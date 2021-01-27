package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;

/**
 * 店铺表(Shop)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
public interface ShopService {

    R page(Integer cur, Integer size, String key, String startTime, String endTime, Integer cityId);

    R mute(Integer shopId, Integer degree);
}