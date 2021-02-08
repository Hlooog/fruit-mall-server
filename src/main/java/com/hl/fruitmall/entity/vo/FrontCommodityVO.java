package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Hl
 * @create 2021/2/8 16:24
 */
@Data
public class FrontCommodityVO {

    private Integer id;
    private Integer shopId;
    private String name;
    private String shopName;
    private List<CommodityInfoVO> voList;
    private List<String> urlList;

}
