package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.OrdersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单表(Orders)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
@RestController
@RequestMapping("orders")
public class OrdersController {
    /**
     * 服务对象
     */
    @Resource
    private OrdersService ordersService;

}