package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.WithdrawalRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提现记录表(WithdrawalRecord)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:48
 */
@RestController
@RequestMapping("withdrawalRecord")
public class WithdrawalRecordController {
    /**
     * 服务对象
     */
    @Resource
    private WithdrawalRecordService withdrawalRecordService;

}