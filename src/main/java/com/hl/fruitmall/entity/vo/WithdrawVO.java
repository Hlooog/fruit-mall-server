package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Hl
 * @create 2021/1/30 17:01
 */
@Data
public class WithdrawVO {

    private String account;
    private BigDecimal amount;
    private String phone;
    private String code;

}
