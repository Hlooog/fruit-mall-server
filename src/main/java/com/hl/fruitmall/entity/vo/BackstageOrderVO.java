package com.hl.fruitmall.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hl
 * @create 2021/2/15 23:15
 */
@Data
public class BackstageOrderVO {
    @ExcelIgnore
    private Integer id;
    @ExcelProperty("订单id")
    private String orderId;
    @ExcelIgnore
    private Integer commodityId;
    @ExcelProperty("商品名")
    private String commodityName;
    @ExcelProperty("数量")
    private Integer quantity;
    @ExcelIgnore
    private Integer shopId;
    @ExcelIgnore
    private Integer infoId;
    @ExcelProperty("商品规格")
    private String specification;
    @ExcelProperty("重量/KG")
    private Float weight;
    @ExcelProperty("价格")
    private BigDecimal price;
    @ExcelProperty("下单时间")
    private Date createTime;
    @ExcelIgnore
    private Integer userId;
    @ExcelProperty("收货人")
    private String name;
    @ExcelProperty("手机号码")
    private String phone;
    @ExcelProperty("地址")
    private String address;
    @ExcelIgnore
    private Integer status;
    @ExcelProperty("订单状态")
    private String statusStr;
    @ExcelProperty("快递单号")
    private String trackNumber;
}
