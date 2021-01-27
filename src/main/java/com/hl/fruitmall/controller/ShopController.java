package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.ShopService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 店铺表(Shop)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
@RestController
@RequestMapping("shop")
public class ShopController {
    /**
     * 服务对象
     */
    @Resource
    private ShopService shopService;

    @GetMapping("/page")
    @VerificationToken
    public R page(@RequestParam("cur") Integer cur,
                  @RequestParam("size") Integer size,
                  @RequestParam(value = "key",required = false) String key,
                  @RequestParam(value = "startTime",required = false) String startTime,
                  @RequestParam(value = "endTime",required = false) String endTime,
                  @RequestParam(value = "cityId",required = false) Integer cityId) {
        return shopService.page(cur,size,key,startTime,endTime,cityId);
    }
    
    @PutMapping("/mute")
    @VerificationToken
    public R mute(@RequestParam("shopId") Integer shopId,
                  @RequestParam("degree") Integer degree){
        return shopService.mute(shopId,degree);
    }
}