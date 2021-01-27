package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录表(WithdrawalRecord)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:47
 */
@SuppressWarnings("serial")
@Data
public class WithdrawalRecord{
    //主键id
    private Integer id;
    //商户id
    private Integer userId;
    //账户
    private Integer account;
    //金额
    private BigDecimal amount;
    //创建时间
    private Date createTime;
}