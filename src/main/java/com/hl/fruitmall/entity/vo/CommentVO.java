package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hl
 * @create 2021/2/9 22:40
 */
@Data
public class CommentVO {
    //主键id
    private Integer id;
    // 用户昵称
    private String nickname;
    // 用户头像
    private String avatar;
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
