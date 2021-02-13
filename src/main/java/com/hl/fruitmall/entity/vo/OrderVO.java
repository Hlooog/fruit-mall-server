package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.common.enums.OrderStatusEnum;
import com.hl.fruitmall.entity.bean.OrderInfo;
import lombok.Data;

/**
 * @author Hl
 * @create 2021/2/12 20:57
 */
@Data
public class OrderVO {

    private Integer commodityId;
    private String commodityName;
    private Integer shopId;
    private String shopName;
    private Integer infoId;
    private Integer quantity;
    private Integer addressId;

    public OrderInfo toOrderInfo(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setShopId(shopId);
        orderInfo.setStatus(OrderStatusEnum.UNPAID.getCode());
        orderInfo.setShopName(shopName);
        orderInfo.setQuantity(quantity);
        orderInfo.setCommodityName(commodityName);
        orderInfo.setCommodityId(commodityId);
        orderInfo.setInfoId(infoId);
        return orderInfo;
    }

}
