package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
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
public class WithdrawalRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //商户id
    private String phone;
    //账户
    private String account;
    //金额
    private BigDecimal amount;
    //提现状态
    private Integer status;
    //创建时间
    private Date createTime;

    public WithdrawalRecord(String phone, String account, Integer status, BigDecimal amount) {
        this.phone = phone;
        this.account = account;
        this.status = status;
        this.amount = amount;
    }
}