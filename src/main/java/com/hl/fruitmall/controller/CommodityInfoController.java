package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.CommodityInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (CommodityInfo)表控制层
 *
 * @author Hl
 * @since 2020-12-31 20:43:13
 */
@RestController
@RequestMapping("commodityInfo")
public class CommodityInfoController {
    /**
     * 服务对象
     */
    @Resource
    private CommodityInfoService commodityInfoService;
}