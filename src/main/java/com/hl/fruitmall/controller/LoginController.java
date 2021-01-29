package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.LoginVO;
import com.hl.fruitmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hl
 * @create 2020/12/16 23:22
 */
@RestController
public class LoginController {


    @Autowired
    UserService userService;

    @PostMapping("/admin/login")
    @PassToken
    public R adminLogin(@RequestBody LoginVO loginVO){
        return userService.adminLogin(loginVO);
    }

    @GetMapping("/logout")
    @VerificationToken(roleType = RoleEnum.USER)
    public R logout(HttpServletRequest request){
        return userService.logout(request);
    }

    @PostMapping("/merchant/login")
    @PassToken
    public R merchantLogin(@RequestBody LoginVO loginVO){
        return userService.merchantLogin(loginVO);
    }
}
