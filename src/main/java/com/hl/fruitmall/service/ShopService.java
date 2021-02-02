package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.Shop;
import com.hl.fruitmall.entity.vo.ShopVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 店铺表(Shop)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
public interface ShopService {

    R page(Integer cur, String key, String startTime, String endTime, Integer cityId);

    R ban(Integer id, Integer days);

    R get(Integer id);

    R getInfo(Integer id);

    R createOrUpdate(ShopVO shopVO, HttpServletRequest request);

    Shop checkShop(String field, Object value);
}