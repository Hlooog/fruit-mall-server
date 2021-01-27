package com.hl.fruitmall.entity.bean;

import lombok.Data;

/**
 * @author Hl
 * @create 2021/1/25 21:45
 */
@Data
public class MerchantInfo {
    private Integer id;
    private Integer userId;
    private String IdCard;
    private String name;
    private String positive;
    private String negative;
    private Integer status;

}
