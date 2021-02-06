package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Hl
 * @create 2021/2/5 17:08
 */
@Data
public class ApplyMerchantVO {
    private Integer id;
    private String name;
    private String idCard;
    private String positive;
    private String negative;
    private List<String> urlList;
}
