package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.CommodityService;
import org.springframework.stereotype.Service;

/**
 * 水果商品表(Commodity)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:35
 */
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {
    @Override
    public R getList(Integer shopId, Integer cur, Integer size, String key) {
        return null;
    }

    @Override
    public R delete(Integer commodityId) {
        return null;
    }
}