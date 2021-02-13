package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.Orders;
import com.hl.fruitmall.entity.vo.UserOrderPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(Orders)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
public interface OrdersMapper {

    void insert(@Param("order") Orders orders);

    List<UserOrderPageVO> selectPage(@Param("userId") Integer userId,@Param("cur") int cur);
}