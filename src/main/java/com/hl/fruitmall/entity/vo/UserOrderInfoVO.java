package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.common.enums.OrderStatusEnum;
import com.hl.fruitmall.common.uitls.EnumUtils;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2021/2/13 10:28
 */
@Data
public class UserOrderInfoVO {
    private Integer id;
    private String url;
    private Integer shopId;
    private Integer commodityId;
    private String shopName;
    private String commodityName;
    private BigDecimal price;
    private Integer quantity;
    private Integer infoId;
    private Integer status;
    private String specification;
    private Float weight;
    private String statusStr;
    private String trackNumber;

    public void setStatusStr(Integer status) {
        this.statusStr = EnumUtils.getByCode(status, OrderStatusEnum.class);
    }
}