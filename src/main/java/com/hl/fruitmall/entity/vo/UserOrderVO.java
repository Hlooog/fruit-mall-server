package com.hl.fruitmall.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Hl
 * @create 2021/2/13 10:14
 */
@Data
public class UserOrderVO {
    private Integer id;
    private String orderId;
    private String phone;
    private String name;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private List<UserOrderInfoVO> infoList;
}
