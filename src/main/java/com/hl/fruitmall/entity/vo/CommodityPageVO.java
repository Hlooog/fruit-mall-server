package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Hl
 * @create 2021/1/31 9:23
 */
@Data
public class CommodityPageVO {

    private Integer id;
    private String cname;
    private Integer varietyId;
    private String vname;
    private Integer isUp;
    private List<String> urlList;
    private Date createTime;
}
