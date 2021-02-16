package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.OrderInfo;
import com.hl.fruitmall.entity.vo.BackstageOrderVO;
import com.hl.fruitmall.entity.vo.UserOrderInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    List<UserOrderInfoVO> getInfoList(@Param("orderId") String orderId);


    void updateStatus(@Param("field") String field, @Param("value") Object value, @Param("code") Integer code);

    List<BackstageOrderVO> selectPage(@Param("shopId") Integer shopId,
                                      @Param("cur") int cur,
                                      @Param("userId") Integer userId,
                                      @Param("start") Date start,
                                      @Param("end") Date end,
                                      @Param("status") Integer code);

    Integer getTotal(@Param("field") String field,
                     @Param("value") Object value,
                     @Param("start") Date start,
                     @Param("end") Date end);

    void updateTrack(@Param("id") Integer id,
                     @Param("trackNumber") String trackNumber,
                     @Param("code") Integer code);

    List<BackstageOrderVO> selectExportData(@Param("shopId") Integer shopId,
                                            @Param("start") Date start,
                                            @Param("end") Date end,
                                            @Param("status") Integer status);

    BackstageOrderVO selectById(@Param("id") Integer id);

    Integer getTotalByUserId(@Param("userId") Integer id,@Param("start") Date start,@Param("end") Date end);
}