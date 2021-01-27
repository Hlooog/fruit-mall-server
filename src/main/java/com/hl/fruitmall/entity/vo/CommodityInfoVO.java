package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.entity.bean.CommodityInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2020/12/31 23:33
 */
@Data
public class CommodityInfoVO {

    private String specification;
    private Float weight;
    private Integer stock;
    private BigDecimal price;

    public static CommodityInfoVO toCommodityInfoVO(CommodityInfo commodityInfo) {
        CommodityInfoVO commodityInfoVO = new CommodityInfoVO();
        commodityInfoVO.setPrice(commodityInfo.getPrice());
        commodityInfoVO.setSpecification(commodityInfo.getSpecification());
        commodityInfoVO.setStock(commodityInfo.getStock());
        commodityInfoVO.setWeight(commodityInfo.getWeight());
        return commodityInfoVO;
    }
}
