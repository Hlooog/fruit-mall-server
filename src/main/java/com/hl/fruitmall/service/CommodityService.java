package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.EditCommodityInfoVO;
import com.hl.fruitmall.entity.vo.EditCommodityVO;

/**
 * 水果商品表(Commodity)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:34
 */
public interface CommodityService {

    R list(Integer id);

    R off(Integer id);

    R page(Integer id, Integer cur);

    R getInfoList(Integer id);

    R get(Integer id);

    R up(Integer id);

    R delete(Integer id);

    R getVariety();

    R edit(EditCommodityVO vo);

    R create(EditCommodityVO vo);

    R insertVariety(String name);

    R infoCreate(EditCommodityInfoVO vo);

    R infoEdit(EditCommodityInfoVO vo);

    R infoDelete(Integer id);

    R infoGet(Integer id);
}