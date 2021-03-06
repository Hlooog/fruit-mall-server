package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.entity.bean.Shop;
import lombok.Data;

/**
 * @author Hl
 * @create 2021/1/29 19:54
 */
@Data
public class ShopVO {
    private Integer id;
    private String name;
    private String description;
    private Integer cityId;

    public ShopVO(Integer id, String name, String description, Integer cityId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityId = cityId;
    }


    public Shop toShop() {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setDescription(description);
        shop.setCityId(cityId);
        return shop;
    }
}
