package com.hl.fruitmall.entity.vo;

import lombok.Data;

/**
 * @author Hl
 * @create 2021/2/11 9:28
 */
@Data
public class CreateCarVO {
    private Integer userId;
    private Integer shopId;
    private Integer commodityId;
    private Integer infoId;
    private String shopName;
    private String commodityName;
    private Integer quantity;
    private String url;
}
