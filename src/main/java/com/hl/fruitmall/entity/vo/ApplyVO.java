package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hl
 * @create 2021/1/25 22:04
 */
@Data
public class ApplyVO {

    private Integer id;
    private Integer userId;
    private String idCard;
    private String name;
    private String positive;
    private String negative;
    private Integer status;
    private Date applyTime;
}
