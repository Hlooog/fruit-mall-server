package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 收货信息表(Address)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:33
 */
@SuppressWarnings("serial")
@Data
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //用户id
    private Integer userId;
    //收货人姓名
    private String name;
    //收货人手机号码
    private String phone;
    //收货地址
    private String address;
}