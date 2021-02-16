package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2021/1/30 11:18
 */
@Data
public class BalanceVO {
    private BigDecimal withdraw;
    private BigDecimal withdrawAble;
    private BigDecimal frozen;
    private BigDecimal lumpSum;
    private Integer shopId;
}
