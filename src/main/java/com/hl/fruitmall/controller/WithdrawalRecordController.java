package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.WithdrawalRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping("/page")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R page(@RequestParam("phone") String phone,
                  @RequestParam("cur") Integer cur,
                  @RequestParam(value = "startTime", required = false) String startTime,
                  @RequestParam(value = "endTime", required = false) String endTime) {
        return withdrawalRecordService.page(phone,cur,startTime,endTime);
    }
}