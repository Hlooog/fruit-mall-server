package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 余额表(Balance)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:27
 */
@SuppressWarnings("serial")
@Data
public class Balance implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //商户id
    private String phone;
    //可提现的余额
    private BigDecimal withdrawAble;
    //以提现金额
    private BigDecimal withdraw;
    //被冻结
    private BigDecimal frozen;
    //总金额
    private BigDecimal lumpSum;
    //修改时间
    private Date updateTime;

}