package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺表(Shop)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
@SuppressWarnings("serial")
@Data
public class Shop implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //所有者id
    private Integer ownerId;
    //店名
    private String name;
    //店铺头像
    private String avatar;
    //店铺描述
    private String description;
    //收藏人数
    private Integer heat;
    //是否关店 0 没有 1 关闭
    private Integer isDelete;
    //违规次数
    private Integer violation;
    // 店铺解封时间
    private Date banTime;
    // 城市id
    private Integer cityId;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    public void addViolation() {
        this.violation++;
    }
}