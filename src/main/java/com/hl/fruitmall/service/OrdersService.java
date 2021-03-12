package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.OrderCarVO;
import com.hl.fruitmall.entity.vo.OrderVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    R get(String orderId);

    void cancelOrder(String orderId);

    R queryOrder(String orderId);

    R applyRefund(Integer id);

    R cancel(String orderId);

    R merchantPage(Integer shopId, Integer cur, String key, String startTime, String endTime, Integer status);

    R ship(Map<String, Object> map);

    R merchantExport(Integer shopId, String startTime, String endTime, Integer status);

    R agree(Integer id);

    R refuse(Integer id);

    R adminExport(String startTime, String endTime);

    R adminPage(String key, Integer cur, Integer type, String startTime, String endTime);

    R confirm(Integer id);

    R getNumberReport(Integer id);

    R getPriceReport(Integer id);

    R getAdminNumberReport();

    R getAdminPriceReport();

    R bulkShip(MultipartFile file);
}