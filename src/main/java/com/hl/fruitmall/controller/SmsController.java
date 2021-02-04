package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.impl.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hl
 * @create 2021/1/30 17:28
 */
@RestController
@RequestMapping("sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/withdraw/send")
    @PassToken
    public R withdrawSend(@RequestParam("phone") String phone) {
        return smsService.withdrawSend(phone);
    }

    @GetMapping("/login/send")
    @PassToken
    public R loginSend(@RequestParam("phone") String phone){
        return smsService.loginSend(phone);
    }


}
