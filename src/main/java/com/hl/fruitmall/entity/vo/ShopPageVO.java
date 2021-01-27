package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.entity.bean.Shop;
import lombok.Data;

import java.util.Date;

/**
 * @author Hl
 * @create 2020/12/28 16:39
 */
@Data
public class ShopPageVO {
    private Integer shopId;

    private Integer ownerId;

    private String ownerName;

    private String shopName;

    private String description;

    private String cityName;

    private Integer heat;

    private Integer violation;

    private Date banTime;

    private Date createTime;

    private Date updateTime;

    public static ShopPageVO toShopPageVO(String name, Shop shop, String cityName){
        ShopPageVO shopPageVO = new ShopPageVO();
        shopPageVO.setShopId(shop.getId());
        shopPageVO.setOwnerId(shop.getOwnerId());
        shopPageVO.setOwnerName(name);
        shopPageVO.setShopName(shop.getName());
        shopPageVO.setDescription(shop.getDescription());
        shopPageVO.setCityName(cityName);
        shopPageVO.setHeat(shop.getHeat());
        shopPageVO.setBanTime(shop.getBanTime());
        shopPageVO.setViolation(shop.getViolation());
        shopPageVO.setCreateTime(shop.getCreateTime());
        shopPageVO.setUpdateTime(shop.getUpdateTime());
        return shopPageVO;
    }

    public static String[] toArray(){
        return new String[]{
                "id","owner_id","name","description","heat","violation","create_time","update_time","ban_time","city_id"
        };
    }
}
