package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.impl.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hl
 * @create 2021/2/1 17:35
 */
@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/link/get")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R linkGet(){
        return chatService.getLinkUser();
    }

    @GetMapping("/record/get")
    @VerificationToken(roleType = RoleEnum.USER)
    public R recordGet(@RequestParam("phone") String phone,
                       @RequestParam("cur") Integer cur){
        return chatService.recordGet(phone,cur);
    }
}
