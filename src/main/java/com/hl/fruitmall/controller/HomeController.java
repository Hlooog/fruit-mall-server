package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.uitls.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hl
 * @create 2021/2/3 10:31
 */
@RestController
public class HomeController {

    @GetMapping("/test")
    @PassToken
    public R test(){
        return R.ok();
    }
}
