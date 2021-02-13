package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.CommodityInfoVO;
import com.hl.fruitmall.entity.vo.EditCommodityInfoVO;
import com.hl.fruitmall.entity.vo.UserOrderInfoPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CommodityInfo)表数据库访问层
 *
 * @author JiongFy
 * @since 2020-12-31 20:43:12
 */
public interface CommodityInfoMapper {
    List<CommodityInfoVO> getInfoList(@Param("id") Integer id);

    void insertInfo(@Param("vo") EditCommodityInfoVO vo);

    EditCommodityInfoVO selectInfo(@Param("id") Integer id);

    void deleteInfo(@Param("field") String field, @Param("value") Object value);

    void updateInfo(@Param("vo") EditCommodityInfoVO vo);

    void decreaseStock(@Param("infoId") Integer infoId,
                       @Param("stock") Integer stock);

    List<HashMap<String,Integer>> selectByMap(@Param("map") Map<Integer, Integer> infoIdMap);



}