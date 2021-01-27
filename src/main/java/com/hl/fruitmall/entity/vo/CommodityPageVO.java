package com.hl.fruitmall.entity.vo;

import com.hl.fruitmall.entity.bean.Commodity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Hl
 * @create 2020/12/29 22:46
 */
@Data
public class CommodityPageVO {

    private Integer commodityId;

    private String commodityName;

    private List<String> imageList;

    private Integer sales;

    private Date createTime;

    private Date updateTime;

    public static String[] toArray(){
        return new String[]{
          "id","name","create_time","update_time"
        };
    }

    public static CommodityPageVO toCommodityPageVO(Commodity commodity,List<String> imageList, Integer sales){
        CommodityPageVO commodityPageVO = new CommodityPageVO();
        commodityPageVO.setCommodityId(commodity.getId());
        commodityPageVO.setCommodityName(commodity.getName());
        commodityPageVO.setCreateTime(commodity.getCreateTime());
        commodityPageVO.setImageList(imageList);
        commodityPageVO.setUpdateTime(commodity.getUpdateTime());
        commodityPageVO.setSales(sales);
        return commodityPageVO;
    }
}
