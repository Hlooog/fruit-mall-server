package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hl
 * @create 2021/1/28 15:54
 */
@Data
public class MerchantVO {
    private Integer id;

    private String name;

    private String idCard;

    private String phone;

    private Date banTime;

    private Integer violation;

    private Date createTime;

}
