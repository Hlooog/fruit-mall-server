package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录表(Withdraw)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:47
 */
@SuppressWarnings("serial")
@Data
public class Withdraw implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //商户id
    private Integer shopId;
    // 手机号码
    private String phone;
    //账户
    private String account;
    //金额
    private BigDecimal amount;
    //提现状态
    private Integer status;
    //创建时间
    private Date createTime;

    public Withdraw(Integer shopId, String phone, String account, Integer status, BigDecimal amount) {
        this.shopId = shopId;
        this.phone = phone;
        this.account = account;
        this.status = status;
        this.amount = amount;
    }
}