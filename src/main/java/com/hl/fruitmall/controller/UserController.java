package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户表(User)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:47
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    @GetMapping("/page/general")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R pageGeneral(@RequestParam("cur") Integer cur,
                         @RequestParam(value = "key", required = false) String key,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime) {
        return userService.page(cur, key, startTime, endTime);
    }

    @PutMapping("/ban/general")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R banUser(@RequestParam("id") Integer id,
                     @RequestParam("days") Integer days) {
        return userService.banGeneral(id, days);
    }

    @PutMapping("/service/{id}")
    @VerificationToken
    public R setService(@PathVariable("id") Integer id) {
        return userService.setService(id);
    }

    @GetMapping("/page/merchant")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R pageMerchant(@RequestParam("cur") Integer cur,
                          @RequestParam(value = "key", required = false) String key,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "endTime", required = false) String endTime) {
        return userService.pageMerchant(cur, key, startTime, endTime);
    }

    @PutMapping("/ban/merchant")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R banMerchant(@RequestParam("id") Integer id,
                         @RequestParam("days") Integer days) {
        return userService.banMerchant(id, days);
    }

    @PutMapping("/cancel/merchant/{id}")
    @VerificationToken
    public R cancelMerchant(@PathVariable("id") Integer id) {
        return userService.cancelMerchant(id);
    }

    @GetMapping("/page/service")
    @VerificationToken
    public R getService() {
        return userService.getList(RoleEnum.CUSTOMER_SERVICE.getCode());
    }

    @PutMapping("/cancel/service/{id}")
    @VerificationToken
    public R cancelService(@PathVariable("id") Integer id) {
        return userService.cancelService(id);
    }

    @GetMapping("/get")
    @VerificationToken(roleType = RoleEnum.USER)
    public R get(HttpServletRequest request){
       return userService.get(request);
    }

    @PutMapping("/edit")
    @VerificationToken(roleType = RoleEnum.USER)
    public R edit(@RequestBody Map<String,String> map, HttpServletRequest request){
        return userService.edit(map, request);
    }

    @PutMapping("/delete")
    @VerificationToken(roleType = RoleEnum.USER)
    public R delete(@RequestBody Map<String,String> map){
        return userService.delete(map);
    }
}