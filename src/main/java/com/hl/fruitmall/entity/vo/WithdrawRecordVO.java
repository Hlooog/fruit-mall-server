package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hl
 * @create 2021/1/30 22:03
 */
@Data
public class WithdrawRecordVO {
    private String phone;
    private String account;
    private BigDecimal amount;
    private Date createTime;
    private Integer status;
}
