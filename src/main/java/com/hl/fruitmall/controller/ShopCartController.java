package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.ShopCartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 购物车表(ShopCart)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:40
 */
@RestController
@RequestMapping("shopCart")
public class ShopCartController {
    /**
     * 服务对象
     */
    @Resource
    private ShopCartService shopCartService;

}