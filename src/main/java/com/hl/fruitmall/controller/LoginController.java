package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public R adminLogin(@RequestParam("phone") String phone,
                        @RequestParam("password") String password){
        return userService.adminLogin(phone,password);
    }

    @GetMapping("/logout")
    @VerificationToken(roleType = RoleEnum.USER)
    public R logout(HttpServletRequest request){
        return userService.logout(request);
    }
}
