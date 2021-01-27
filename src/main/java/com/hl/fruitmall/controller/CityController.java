package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * 城市表(City)表控制层
 *
 * @author hl
 * @since 2020-12-26 21:55:48
 */
@RestController
@RequestMapping("city")
public class CityController {
    /**
     * 服务对象
     */
    @Resource
    private CityService cityService;

    @GetMapping("info")
    @PassToken
    public R getInfo(){
        return cityService.getInfo();
    }

}