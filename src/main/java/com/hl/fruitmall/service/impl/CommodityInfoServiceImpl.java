package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.mapper.CommodityInfoMapper;
import com.hl.fruitmall.service.CommodityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}