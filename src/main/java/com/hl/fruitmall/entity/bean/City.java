package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 城市表(City)实体类
 *
 * @author JiongFy
 * @since 2020-12-26 21:55:28
 */
@Data
public class City implements Serializable {
    private static final long serialVersionUID = 910978038586871922L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 城市名
     */
    private String name;
    /**
     * 父级id 0表示自己是父级
     */
    private Integer parentId;


}