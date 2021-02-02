package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.WithdrawVO;
import com.hl.fruitmall.service.BalanceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/get")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R get(HttpServletRequest request) {
        return balanceService.get(request);
    }

    @PostMapping("/withdraw")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R withdraw(@RequestBody WithdrawVO withdrawVO, HttpServletRequest request) {
        return balanceService.withdraw(withdrawVO, request);
    }

}