package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.entity.bean.Shop;
import lombok.Data;

/**
 * @author Hl
 * @create 2021/1/28 22:32
 */
@Data
public class ShopInfoVO {
    private Integer id;
    private String name;
    private String description;
    private Integer heat;

    public static ShopInfoVO create(Shop shop) {
        ShopInfoVO shopInfoVO = new ShopInfoVO();
        shopInfoVO.setId(shop.getId());
        shopInfoVO.setName(shop.getName());
        shopInfoVO.setDescription(shop.getDescription());
        shopInfoVO.setHeat(shop.getHeat());
        return shopInfoVO;
    }
}
