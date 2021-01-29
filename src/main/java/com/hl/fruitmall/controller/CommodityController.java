package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
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

    @GetMapping("/page/{id}")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R page(@PathVariable("id")Integer id){
        return commodityService.page(id);
    }

    @PutMapping("/off/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R off(@PathVariable("id") Integer id){
        return commodityService.off(id);
    }

}