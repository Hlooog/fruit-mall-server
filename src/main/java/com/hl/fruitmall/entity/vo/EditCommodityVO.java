package com.hl.fruitmall.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Hl
 * @create 2021/1/31 16:50
 */
@Data
public class EditCommodityVO {
    private Integer id;
    private String name;
    private Integer shopId;
    private Integer varietyId;
    private List<String> urlList;
    private List<String> removeList;

    public EditCommodityVO(Integer id, String name, Integer varietyId, List<String> urlList) {
        this.id = id;
        this.name = name;
        this.varietyId = varietyId;
        this.urlList = urlList;
    }

    public EditCommodityVO() {
    }
}
