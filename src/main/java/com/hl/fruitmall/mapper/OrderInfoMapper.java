package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.OrderInfo;
import com.hl.fruitmall.entity.vo.UserOrderInfoPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单详情表(OrderInfo)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:37
 */
public interface OrderInfoMapper {

    void insertBatch(@Param("list") List<OrderInfo> orderInfoList);

    void insert(@Param("info") OrderInfo orderInfo);

    List<UserOrderInfoPageVO> getInfoList(@Param("orderId") String orderId);

    Integer getTotal(@Param("field") String field, @Param("value") Object value);
}