package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(User)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:41
 */
@SuppressWarnings("serial")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;

    private String phone;
    //密码
    private String password;
    //openId
    private String openId;
    //头像
    private String avatar;
    //昵称
    private String nickname;
    //角色类型 0 普通用户
    private Integer roleType;
    //是否被删除 0 没有 1 被删除
    private Integer isDelete;
    // 违规次数
    private Integer violation;
    // 解封禁时间
    private Date banTime;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    public void addViolation() {
        this.violation += 1;
    }
}