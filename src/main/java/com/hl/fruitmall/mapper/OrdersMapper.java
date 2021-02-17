package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.Orders;
import com.hl.fruitmall.entity.vo.UserOrderVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单表(Orders)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
public interface OrdersMapper {

    void insert(@Param("order") Orders orders);

    Integer getTotal(@Param("field") String field, @Param("value") Object value);

    List<UserOrderVO> selectPage(@Param("userId") Integer userId, @Param("cur") int cur);

    UserOrderVO selectByOrderId(@Param("orderId") String orderId);

    List<Map<Date, Integer>> getNumberReport(@Param("id") Integer id);

    List<Map<Date, BigDecimal>> getPriceReport(@Param("id") Integer id);
}