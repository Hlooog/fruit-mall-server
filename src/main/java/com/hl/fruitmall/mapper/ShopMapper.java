package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.Shop;
import com.hl.fruitmall.entity.vo.ShopPageVO;
import com.hl.fruitmall.entity.vo.ShopVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 店铺表(Shop)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:39
 */
public interface ShopMapper {

    Shop selectByFiled(@Param("field") String field,@Param("value") Object value);

    void updateBanTime(@Param("id") Integer id,
                       @Param("banTime") Date banTime,
                       @Param("violation") Integer violation);

    void updateByField(@Param("field1") String field1,
                       @Param("value1") Integer value1,
                       @Param("field2") String field2,
                       @Param("value2") Integer value2);

    List<ShopPageVO> selectPage(@Param("cur") int cur,
                                @Param("key") String key,
                                @Param("start") Date start,
                                @Param("end") Date end,
                                @Param("cityId") Integer cityId);

    Integer getTotal(@Param("key") String key,
                     @Param("start") Date start,
                     @Param("end") Date end,
                     @Param("cityId") Integer cityId);

    void create(@Param("shopVO") ShopVO shopVO,@Param("id") Integer id);

    void update(@Param("shopVO") ShopVO shopVO);
}