package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.CommodityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 水果商品表(Commodity)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:35
 */
@RestController
@RequestMapping("commodity")
public class CommodityController {
    /**
     * 服务对象
     */
    @Resource
    private CommodityService commodityService;

    @GetMapping("/list")
    @VerificationToken
    public R getList(@RequestParam("shopId") Integer shopId,
                     @RequestParam("cur") Integer cur,
                     @RequestParam("size") Integer size,
                     @RequestParam(value = "key",required = false) String key){
        return commodityService.getList(shopId,cur,size,key);
    }

    @DeleteMapping("/delete/{commodityId}")
    @VerificationToken
    public R delete(@PathVariable("commodityId") Integer commodityId) {
        return commodityService.delete(commodityId);
    }

}