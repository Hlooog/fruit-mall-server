package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.List;

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
    private String avatar;
    private List<String> urlList;

    public ShopVO(Integer id, String name, String description, Integer cityId, String avatar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityId = cityId;
        this.avatar = avatar;
    }
}
