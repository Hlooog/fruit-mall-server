package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.Commodity;
import com.hl.fruitmall.entity.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 水果商品表(Commodity)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:34
 */
public interface CommodityMapper {

    List<CommodityListVO> selectList(@Param("id") Integer id);

    Integer getTotal(@Param("id") Integer id);

    void updateByField(@Param("field1") String field1,
                       @Param("value1") Object value1,
                       @Param("filed2") String field2,
                       @Param("value2") Object value2);

    List<Commodity> selectByShopId(@Param("id") Integer id);

    List<CommodityPageVO> selectPage(@Param("id") Integer id, @Param("cur") int cur);

    List<String> getUrl(@Param("id") Integer ic);

    CommodityPageVO selectByField(@Param("field") String filed, @Param("value") Object value);

    void deleteByField(@Param("field") String id, @Param("value") Object value);

    void deleteImage(@Param("urls") List<String> urlList);

    void insert(@Param("commodity") Commodity commodity);

    void insertImage(@Param("id") Integer id, @Param("list") List<String> urlList);

    void update(@Param("id") Integer id, @Param("name") String name, @Param("varietyId") Integer varietyId);

    CommodityVO select(@Param("id") Integer id);

    FrontCommodityVO selectInfo(@Param("id") Integer id);
}