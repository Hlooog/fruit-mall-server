package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Hl
 * @create 2020/12/29 22:46
 */
@Data
public class CommodityListVO {

    private Integer id;

    private String name;

    private Integer isUp;

    private String varietyName;

    private BigDecimal price;

    private List<String> urlList;

}
