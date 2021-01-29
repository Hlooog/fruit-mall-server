package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.GlobalUtils;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.bean.Shop;
import com.hl.fruitmall.entity.vo.ShopInfoVO;
import com.hl.fruitmall.entity.vo.ShopPageVO;
import com.hl.fruitmall.mapper.ShopMapper;
import com.hl.fruitmall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Shop getShop(String field, Object value) {
        Shop shop = shopMapper.selectByFiled(field,value);
        if (shop == null) {
            throw new GlobalException(ExceptionEnum.HAS_NOT_SHOP_RECORDS);
        }
        return shop;
    }

    @Override
    public R ban(Integer id, Integer days) {
        Shop shop = getShop("id", id);
        checkShop(shop);
        shop.addViolation();
        Date banTime = globalUtils.getBanTime(shop.getCreateTime(), shop.getViolation(), days);
        shopMapper.updateBanTime(id, banTime, shop.getViolation());
        return R.ok();
    }

    @Override
    public R get(Integer id) {
        Shop shop = getShop("id", id);
        checkShop(shop);
        ShopInfoVO shopInfoVO = ShopInfoVO.create(shop);
        return R.ok(shopInfoVO);
    }

    private void checkShop(Shop shop){
        if (shop.getCreateTime().after(shop.getBanTime())
                || shop.getBanTime().after(new Date())){
            throw new GlobalException(ExceptionEnum.SHOP_HAS_BEEN_BAN);
        }
    }
}