package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.AntiRefresh;
import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.WithdrawService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 提现记录表(Withdraw)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:48
 */
@RestController
@RequestMapping("withdraw")
public class WithdrawController {
    /**
     * 服务对象
     */
    @Resource
    private WithdrawService withdrawService;


    @GetMapping("/page")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R page(@RequestParam("phone") String phone,
                  @RequestParam("cur") Integer cur,
                  @RequestParam(value = "startTime", required = false) String startTime,
                  @RequestParam(value = "endTime", required = false) String endTime,
                  @RequestParam("status") Integer status) {
        return withdrawService.page(phone, cur, startTime, endTime, status);
    }

    @PutMapping("/review")
    @VerificationToken
    @AntiRefresh(logos = {"id","phone","shopId"}, time = 1, err = ExceptionEnum.REPEATED_SUBMIT)
    public R review(@RequestParam("id") Integer id,
                    @RequestParam("shopId") Integer shopId,
                    @RequestParam("phone") String phone) {
        return withdrawService.review(id, shopId,phone);
    }

    @PutMapping("/refuse")
    @VerificationToken
    @AntiRefresh(logos = {"id","phone","shopId"}, time = 1,err = ExceptionEnum.REPEATED_SUBMIT)
    public R refuse(@RequestParam("id") Integer id,
                    @RequestParam("shopId") Integer shopId,
                    @RequestParam("phone") String phone) {
        return withdrawService.refuse(id,shopId, phone);
    }

}