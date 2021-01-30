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
    private Integer fruitId;
    //评论内容
    private String content;
    //点赞数
    private Integer like;
    //回复评论id
    private Integer toCommentId;
    //回复评论人
    private Integer toUserId;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    

}