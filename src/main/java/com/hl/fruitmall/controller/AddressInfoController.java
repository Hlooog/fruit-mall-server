package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.AddressInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 收货信息表(AddressInfo)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:34
 */
@RestController
@RequestMapping("deliveryInformation")
public class AddressInfoController {
    /**
     * 服务对象
     */
    @Resource
    private AddressInfoService deliveryInformationService;

}