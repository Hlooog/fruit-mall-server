package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.CreateCarVO;
import com.hl.fruitmall.service.ShopCarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 购物车表(ShopCar)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:40
 */
@RestController
@RequestMapping("shopCar")
public class ShopCarController {
    /**
     * 服务对象
     */
    @Resource
    private ShopCarService shopCarService;

    @GetMapping("/list")
    @VerificationToken(roleType = RoleEnum.USER)
    public R list(HttpServletRequest request) {
        return shopCarService.list(request);
    }


    @PostMapping("/add")
    @VerificationToken(roleType = RoleEnum.USER)
    public R add(@RequestBody CreateCarVO carVO) {
        return shopCarService.create(carVO);
    }

    @PutMapping("/increase/{id}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R increase(@PathVariable("id") Integer id, HttpServletRequest request) {
        return shopCarService.increase(id, request);
    }

    @PutMapping("/decrease/{id}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R decrease(@PathVariable("id") Integer id, HttpServletRequest request) {
        return shopCarService.decrease(id, request);
    }

    @DeleteMapping("/moveOut/{id}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R moveOut(@PathVariable("id") Integer id, HttpServletRequest request) {
        return shopCarService.moveOut(id, request);
    }
    
}