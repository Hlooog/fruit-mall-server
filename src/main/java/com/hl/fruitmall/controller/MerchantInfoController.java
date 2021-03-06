package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.ApplyMerchantVO;
import com.hl.fruitmall.service.MerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hl
 * @create 2021/1/25 22:28
 */
@RestController
@RequestMapping("merchantInfo")
public class MerchantInfoController {
    @Autowired
    private MerchantInfoService merchantInfoService;

    @PutMapping("/review/{id}")
    @VerificationToken
    public R review(@PathVariable("id") Integer id) {
        return merchantInfoService.review(id);
    }

    @GetMapping("/list/review")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R getListReview(@RequestParam("cur") Integer cur,
                           @RequestParam("status") Integer status) {
        return merchantInfoService.getListReview(cur, status);
    }

    @PutMapping("/refuse/{id}")
    @VerificationToken
    public R refuse(@PathVariable("id") Integer id) {
        return merchantInfoService.refuse(id);
    }

    @PostMapping("/apply")
    @VerificationToken(roleType = RoleEnum.USER)
    public R apply(@RequestBody ApplyMerchantVO vo){
        return merchantInfoService.apply(vo);
    }

    @GetMapping("/get")
    @VerificationToken(roleType = RoleEnum.USER)
    public R get(HttpServletRequest request){
        return merchantInfoService.get(request);
    }
}
