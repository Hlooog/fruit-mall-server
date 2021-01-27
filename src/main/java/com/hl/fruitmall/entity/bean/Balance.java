package com.hl.fruitmall.entity.bean;

import lombok.Data;

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
public class Balance  {
    //主键id
    private Integer id;
    //商户id
    private Integer userId;
    //可提现的余额
    private BigDecimal withDrawable;
    //被冻结
    private BigDecimal frozen;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}