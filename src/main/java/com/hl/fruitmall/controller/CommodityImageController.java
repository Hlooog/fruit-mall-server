package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.CommodityImageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 水果图片表(CommodityImage)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:36
 */
@RestController
@RequestMapping("commodityImage")
public class CommodityImageController {
    /**
     * 服务对象
     */
    @Resource
    private CommodityImageService fruitImageService;

}