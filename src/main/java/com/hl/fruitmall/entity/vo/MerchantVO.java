package com.hl.fruitmall.entity.vo;

import lombok.Data;

/**
 * @author Hl
 * @create 2021/1/25 22:04
 */
@Data
public class MerchantVO {

    private Integer id;
    private Integer userId;
    private String IdCard;
    private String name;
    private String positive;
    private String negative;
    private Integer status;

}
