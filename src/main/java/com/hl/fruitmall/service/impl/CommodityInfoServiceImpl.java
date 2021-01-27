package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.mapper.CommodityInfoMapper;
import com.hl.fruitmall.entity.bean.CommodityInfo;
import com.hl.fruitmall.entity.vo.CommodityInfoVO;
import com.hl.fruitmall.service.CommodityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * (CommodityInfo)表服务实现类
 *
 * @author Hl
 * @since 2020-12-31 20:43:13
 */
@Service("commodityInfoService")
public class CommodityInfoServiceImpl implements CommodityInfoService {

    @Autowired
    CommodityInfoMapper commodityInfoMapper;

    @Override
    public R getInfo(Integer commodityId, Integer cur) {
        CommodityInfo commodityInfo = commodityInfoMapper.getInfo(cur, commodityId);
        Integer total = commodityInfoMapper.getTotal(commodityId);
        return R.ok(new HashMap<String,Object>(){
            {
                put("data", CommodityInfoVO.toCommodityInfoVO(commodityInfo));
                put("total", total);
            }
        });
    }
}