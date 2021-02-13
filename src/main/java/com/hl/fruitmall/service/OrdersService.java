package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.OrderCarVO;
import com.hl.fruitmall.entity.vo.OrderVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单表(Orders)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
public interface OrdersService {

    R createCar(OrderCarVO vo, HttpServletRequest request);

    R create(OrderVO orderVO, HttpServletRequest request);

    R userPage(Integer cur, HttpServletRequest request);
}