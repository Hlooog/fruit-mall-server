package com.hl.fruitmall.entity.vo;


import lombok.Data;

import java.util.Date;

/**
 * @author Hl
 * @create 2020/11/21 0:44
 */
@Data
public class UserPageVO {

    private Integer id;

    private String nickname;

    private String phone;

    private Date banTime;

    private Integer violation;

    private Date createTime;


}
