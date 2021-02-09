package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表(Comment)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:30
 */
@SuppressWarnings("serial")
@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //评论者id
    private Integer userId;
    //评论的水果id
    private Integer commodityId;
    //评论内容
    private String content;
    // 评分
    private Float score;
    // 规格
    private String specification;
    // 重量
    private Float weight;
    // 数量
    private Integer num;
    //创建时间
    private Date createTime;


}