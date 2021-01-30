package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hl
 * @create 2021/1/25 21:45
 */
@Data
public class MerchantInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer userId;
    private String IdCard;
    private String name;
    private String positive;
    private String negative;
    private Integer status;
    private Date applyTime;
}
