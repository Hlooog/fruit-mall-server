package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.Address;
import com.hl.fruitmall.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;

/**
 * 收货信息表(Address)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:34
 */
@RestController
@RequestMapping("address")
public class AddressController {
    /**
     * 服务对象
     */
    @Resource
    private AddressService addressService;

    @GetMapping("/list")
    @VerificationToken(roleType = RoleEnum.USER)
    public R list(HttpServletRequest request){
        return addressService.list(request);
    }

    @PostMapping("/add")
    @VerificationToken(roleType = RoleEnum.USER)
    public R add(@RequestBody Address address, HttpServletRequest request){
        return addressService.add(address, request);
    }

    @PutMapping("/edit")
    @VerificationToken(roleType = RoleEnum.USER)
    public R edit(@RequestBody Address address){
        return addressService.edit(address);
    }

    @DeleteMapping("/delete/{id}")
    @VerificationToken(roleType = RoleEnum.USER)
    public R delete(@PathVariable("id") Integer id){
        return addressService.delete(id);
    }
}