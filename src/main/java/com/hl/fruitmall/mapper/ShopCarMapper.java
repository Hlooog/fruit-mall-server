package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.CreateCarVO;
import com.hl.fruitmall.entity.vo.ShopCarVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车表(ShopCar)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:40
 */
public interface ShopCarMapper {

    List<ShopCarVO> list(@Param("id") Integer id);

    void insert(@Param("vo") CreateCarVO carVO);

    void updateQuantity(@Param("id")Integer id,@Param("userId") Integer userId, @Param("num") int num);

    void delete(@Param("id") Integer id,@Param("userId") Integer userId);

    List<ShopCarVO> selectByIds(@Param("ids") List<Integer> carIds);

    void deleteBatch(@Param("ids") List<Integer> carIds, @Param("userId") Integer userId);
}