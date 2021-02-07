package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.EditCommodityInfoVO;
import com.hl.fruitmall.entity.vo.EditCommodityVO;
import com.hl.fruitmall.service.CommodityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 水果商品表(Commodity)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:35
 */
@RestController
@RequestMapping("commodity")
public class CommodityController {
    /**
     * 服务对象
     */
    @Resource
    private CommodityService commodityService;

    @GetMapping("/list/{id}")
    @VerificationToken(roleType = RoleEnum.CUSTOMER_SERVICE)
    public R list(@PathVariable("id") Integer id) {
        return commodityService.list(id);
    }

    @PutMapping("/off/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R off(@PathVariable("id") Integer id) {
        return commodityService.off(id);
    }

    @PutMapping("/up/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R up(@PathVariable("id") Integer id, HttpServletRequest request) {
        return commodityService.up(id, request);
    }

    @GetMapping("/page")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R page(@RequestParam("id") Integer id,
                  @RequestParam("cur") Integer cur) {
        return commodityService.page(id, cur);
    }

    @GetMapping("/info/list/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R getInfoList(@PathVariable("id") Integer id) {
        return commodityService.getInfoList(id);
    }

    @GetMapping("/get/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R get(@PathVariable("id") Integer id) {
        return commodityService.get(id);
    }

    @DeleteMapping("/delete/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R delete(@PathVariable("id") Integer id) {
        return commodityService.delete(id);
    }

    @GetMapping("/variety/get")
    @PassToken
    public R getVariety() {
        return commodityService.getVariety();
    }

    @PutMapping("/edit")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R edit(@RequestBody EditCommodityVO vo) {
        return commodityService.edit(vo);
    }

    @PostMapping("/create")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R create(@RequestBody EditCommodityVO vo) {
        return commodityService.create(vo);
    }

    @PostMapping("/variety/insert")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R insertVariety(@RequestParam("name") String name) {
        return commodityService.insertVariety(name);
    }

    @PostMapping("/info/create")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R infoCreate(@RequestBody EditCommodityInfoVO vo) {
        return commodityService.infoCreate(vo);
    }

    @PutMapping("/info/edit")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R infoEdit(@RequestBody EditCommodityInfoVO vo) {
        return commodityService.infoEdit(vo);
    }

    @DeleteMapping("/info/delete/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R infoDelete(@PathVariable("id") Integer id) {
        return commodityService.infoDelete(id);
    }

    @GetMapping("/info/get/{id}")
    @VerificationToken(roleType = RoleEnum.MERCHANT)
    public R infoGet(@PathVariable("id") Integer id) {
        return commodityService.infoGet(id);
    }

    @GetMapping("/variety/list")
    @PassToken
    public R varietyList() {
        return commodityService.varietyList();
    }

    @PostMapping("/list")
    @PassToken
    public R getList(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        return commodityService.getList(map, request);
    }

    @GetMapping("/home")
    @PassToken
    public R getHot(HttpServletRequest request) {
        return commodityService.getHome(request);
    }
}