package com.hl.fruitmall.entity.bean;

import lombok.Data;

/**
 * 收货信息表(AddressInfo)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:33
 */
@SuppressWarnings("serial")
@Data
public class AddressInfo {
    //主键id
    private Integer id;
    //用户id
    private Integer userId;
    //收货人姓名
    private Integer name;
    //收货人手机号码
    private String phone;
    //收货地址
    private String address;

}