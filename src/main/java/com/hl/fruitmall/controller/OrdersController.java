package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.OrderCarVO;
import com.hl.fruitmall.entity.vo.OrderVO;
import com.hl.fruitmall.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 订单表(Orders)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
@RestController
@RequestMapping("orders")
public class OrdersController {
    /**
     * 服务对象
     */
    @Resource
    private OrdersService ordersService;

    @PostMapping("/create/car")
    @VerificationToken(roleType = RoleEnum.USER)
    public R createCar(@RequestBody OrderCarVO vo, HttpServletRequest request){
        return ordersService.createCar(vo,request);
    }

    @PostMapping("/create")
    @VerificationToken(roleType = RoleEnum.USER)
    public R create(@RequestBody OrderVO orderVO, HttpServletRequest request) {
        return ordersService.create(orderVO,request);
    }

    @GetMapping("/user/page/{cur}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R userPage(@PathVariable("cur") Integer cur, HttpServletRequest request) {
        return ordersService.userPage(cur,request);
    }

}