package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.CommodityInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/info")
    @VerificationToken
    public R getInfo(@RequestParam("cur") Integer cur,
                     @RequestParam("commodityId") Integer commodityId){
        return commodityInfoService.getInfo(commodityId,cur);
    }
}