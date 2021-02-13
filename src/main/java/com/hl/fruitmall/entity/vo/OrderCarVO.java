package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Hl
 * @create 2021/2/12 16:02
 */
@Data
public class OrderCarVO {
    private Integer addressId;
    private List<Integer> carIds;
}
