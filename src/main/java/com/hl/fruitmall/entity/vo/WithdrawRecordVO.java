package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hl
 * @create 2021/2/1 10:58
 */
@Data
public class WithdrawRecordVO {
    //主键id
    private Integer id;
    //手机号码
    private String phone;
    // 商户id
    private Integer shopId;
    //账户
    private String account;
    //金额
    private BigDecimal amount;
    //提现状态
    private Integer status;
    //创建时间
    private Date createTime;
}
