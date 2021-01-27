package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.ShopService;
import org.springframework.stereotype.Service;

/**
 * 店铺表(Shop)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Override
    public R page(Integer cur, Integer size, String key, String startTime, String endTime, Integer cityId) {
        return null;
    }

    @Override
    public R mute(Integer shopId, Integer degree) {
        return null;
    }
}