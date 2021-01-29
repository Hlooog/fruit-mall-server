package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.CommodityPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 水果商品表(Commodity)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:34
 */
public interface CommodityMapper {

    List<CommodityPageVO> selectPage(@Param("id") Integer id);

    Integer getTotal(@Param("id") Integer id);

    void updateByField(@Param("field1") String field1,
                       @Param("value1") Object value1,
                       @Param("filed2") String field2,
                       @Param("value2") Object value2);
}