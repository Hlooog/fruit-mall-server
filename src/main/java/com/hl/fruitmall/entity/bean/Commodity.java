package com.hl.fruitmall.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 水果商品表(Commodity)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //商户id
    private Integer shopId;
    //水果名称
    private String name;
    //种类id
    private Integer varietyId;
    //是否上架 0 上架 1 下架
    private Integer isUp;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    private Float score;
    private Integer number;
    private Integer keep;

    public Commodity(Integer shopId, String name, Integer varietyId) {
        this.shopId = shopId;
        this.name = name;
        this.varietyId = varietyId;
    }

    public void calScore(Float score){
        this.score = (this.score + score) / (this.number + 1);
    }
}