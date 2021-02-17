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
import java.util.Map;

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
    public R createCar(@RequestBody OrderCarVO vo, HttpServletRequest request) {
        return ordersService.createCar(vo, request);
    }

    @PostMapping("/create")
    @VerificationToken(roleType = RoleEnum.USER)
    public R create(@RequestBody OrderVO orderVO, HttpServletRequest request) {
        return ordersService.create(orderVO, request);
    }

    @GetMapping("/user/page/{cur}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R userPage(@PathVariable("cur") Integer cur, HttpServletRequest request) {
        return ordersService.userPage(cur, request);
    }

    @GetMapping("/merchant/page")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R merchantPage(@RequestParam("shopId") Integer shopId,
                          @RequestParam("cur") Integer cur,
                          @RequestParam(value = "userId", required = false) Integer userId,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @RequestParam("status") Integer status) {
        return ordersService.merchantPage(shopId, cur, userId, startTime, endTime, status);
    }

    @GetMapping("/get/{orderId}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R get(@PathVariable("orderId") String orderId) {
        return ordersService.get(orderId);
    }

    @GetMapping("/query/{orderId}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R queryOrder(@PathVariable("orderId") String orderId) {
        return ordersService.queryOrder(orderId);
    }

    @PutMapping("/apply/refund/{id}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R applyRefund(@PathVariable("id") Integer id) {
        return ordersService.applyRefund(id);
    }

    @PutMapping("/cancel/{orderId}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R cancel(@PathVariable("orderId") String orderId) {
        return ordersService.cancel(orderId);
    }

    @PutMapping("/ship")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R ship(@RequestBody Map<String, Object> map) {
        return ordersService.ship(map);
    }

    @GetMapping("/merchant/export")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R merchantExport(@RequestParam("shopId") Integer shopId,
                            @RequestParam(value = "startTime", required = false) String startTime,
                            @RequestParam(value = "endTime", required = false) String endTime,
                            @RequestParam("status") Integer status) {
        return ordersService.merchantExport(shopId, startTime, endTime, status);
    }

    @PutMapping("/agree/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R agree(@PathVariable("id") Integer id) {
        return ordersService.agree(id);
    }

    @PutMapping("/refuse/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R refuse(@PathVariable("id") Integer id) {
        return ordersService.refuse(id);
    }

    @GetMapping("/admin/export")
    @VerificationToken(roleType = RoleEnum.ADMIN)
    public R adminExport(@RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime) {
        return ordersService.adminExport(startTime, endTime);
    }

    @GetMapping("/admin/page")
    @VerificationToken(roleType = RoleEnum.ADMIN)
    public R adminPage(@RequestParam("id") Integer id,
                       @RequestParam("cur") Integer cur,
                       @RequestParam("type") Integer type,
                       @RequestParam("startTime") String startTime,
                       @RequestParam("endTime") String endTime) {
        return ordersService.adminPage(id, cur, type, startTime, endTime);
    }

    @PutMapping("/confirm/{id}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R confirm(@PathVariable("id") Integer id){
        return ordersService.confirm(id);
    }

    @GetMapping("/number/report/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R getNumberReport(@PathVariable("id") Integer id){
        return ordersService.getNumberReport(id);
    }

    @GetMapping("/price/report/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R getPriceReport(@PathVariable("id") Integer id){
        return ordersService.getPriceReport(id);
    }

    @GetMapping("/admin/number/report")
    @VerificationToken(roleType = RoleEnum.ADMIN)
    public R getAdminNumberReport(){
        return ordersService.getAdminNumberReport();
    }

    @GetMapping("/admin/price/report")
    @VerificationToken(roleType = RoleEnum.ADMIN)
    public R getAdminPriceReport(){
        return ordersService.getAdminPriceReport();
    }
}