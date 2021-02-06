package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.CloseShopVO;
import com.hl.fruitmall.entity.vo.ShopVO;
import com.hl.fruitmall.service.ShopService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R page(@RequestParam("cur") Integer cur,
                  @RequestParam(value = "key", required = false) String key,
                  @RequestParam(value = "startTime", required = false) String startTime,
                  @RequestParam(value = "endTime", required = false) String endTime,
                  @RequestParam(value = "cityId", required = false) Integer cityId) {
        return shopService.page(cur, key, startTime, endTime, cityId);
    }

    @PutMapping("/ban")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R ban(@RequestParam("id") Integer id,
                 @RequestParam("days") Integer days) {
        return shopService.ban(id, days);
    }

    @GetMapping("/get/{id}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R get(@PathVariable("id") Integer id) {
        return shopService.get(id);
    }

    @GetMapping("/getInfo/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R getInfo(@PathVariable("id") Integer id) {
        return shopService.getInfo(id);
    }

    @PostMapping("/createOrUpdate")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R createOrUpdate(@RequestBody ShopVO shopVO, HttpServletRequest request) {
        return shopService.createOrUpdate(shopVO, request);
    }

    @PutMapping("/close")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R close(@RequestBody CloseShopVO vo, HttpServletRequest request){
        return shopService.close(vo,request);
    }
}