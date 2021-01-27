package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.OrderInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单详情表(OrderInfo)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:37
 */
@RestController
@RequestMapping("orderInfo")
public class OrderInfoController {
    /**
     * 服务对象
     */
    @Resource
    private OrderInfoService orderInfoService;

}