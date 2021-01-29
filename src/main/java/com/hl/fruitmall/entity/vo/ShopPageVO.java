package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hl
 * @create 2020/12/28 16:39
 */
@Data
public class ShopPageVO {
    private Integer id;

    private Integer ownerId;

    private String ownerName;

    private String shopName;

    private String cityName;

    private Integer heat;

    private Integer violation;

    private Date createTime;

    private Date banTime;
}
