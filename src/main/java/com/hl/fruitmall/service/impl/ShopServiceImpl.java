package com.hl.fruitmall.service.impl;

import com.auth0.jwt.JWT;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.Shop;
import com.hl.fruitmall.entity.vo.ShopInfoVO;
import com.hl.fruitmall.entity.vo.ShopPageVO;
import com.hl.fruitmall.entity.vo.ShopVO;
import com.hl.fruitmall.mapper.ShopMapper;
import com.hl.fruitmall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 店铺表(Shop)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private GlobalUtils globalUtils;

    @Override
    public R page(Integer cur,
                  String key,
                  String startTime,
                  String endTime,
                  Integer cityId) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0],end = dates[1];
        List<ShopPageVO> list = shopMapper.selectPage((cur - 1) * 10, key, start,end, cityId);
        Integer total = shopMapper.getTotal(key,start,end,cityId);
        return R.ok(new HashMap<String,Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }
    @Override
    public R ban(Integer id, Integer days) {
        Shop shop = checkShop("id", id);
        shop.addViolation();
        Date banTime = globalUtils.getBanTime(shop.getCreateTime(), shop.getViolation(), days);
        shopMapper.updateBanTime(id, banTime, shop.getViolation());
        return R.ok();
    }

    @Override
    public R get(Integer id) {
        Shop shop = checkShop("id", id);
        ShopInfoVO shopInfoVO = ShopInfoVO.create(shop);
        return R.ok(shopInfoVO);
    }

    @Override
    public R getInfo(Integer id) {
        Shop shop = checkShop("owner_id", id);
        ShopVO shopVO = new ShopVO(shop.getId(),
                shop.getName(),
                shop.getDescription(),
                shop.getCityId());
        return R.ok(shopVO);
    }

    @Override
    public R createOrUpdate(ShopVO shopVO, HttpServletRequest request) {
        Integer id = Integer.valueOf(JWT.decode(request.getHeader("X-Token")).getAudience().get(0));
        if (shopVO.getId() == null) {
            // 创建
            shopMapper.create(shopVO,id);
        } else {
            // 修改
            shopMapper.update(shopVO);
        }
        return R.ok();
    }

    private Shop checkShop(String field,Object value){
        Shop shop = shopMapper.selectByFiled(field,value);
        if (shop == null) {
            throw new GlobalException(ExceptionEnum.HAS_NOT_SHOP_RECORDS);
        }
        if (shop.getCreateTime().after(shop.getBanTime())
                || shop.getBanTime().after(new Date())){
            throw new GlobalException(ExceptionEnum.SHOP_HAS_BEEN_BAN);
        }
        return shop;
    }
}