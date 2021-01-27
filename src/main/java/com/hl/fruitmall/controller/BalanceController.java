package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.BalanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 余额表(Balance)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:30
 */
@RestController
@RequestMapping("balance")
public class BalanceController {
    /**
     * 服务对象
     */
    @Resource
    private BalanceService balanceService;

}