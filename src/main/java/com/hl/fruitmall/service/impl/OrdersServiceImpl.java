package com.hl.fruitmall.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.wxpay.sdk.WXPayUtil;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.OrderStatusEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.*;
import com.hl.fruitmall.config.RabbitConfig;
import com.hl.fruitmall.entity.bean.OrderInfo;
import com.hl.fruitmall.entity.bean.Orders;
import com.hl.fruitmall.entity.vo.*;
import com.hl.fruitmall.listener.ShipListener;
import com.hl.fruitmall.mapper.CommodityInfoMapper;
import com.hl.fruitmall.mapper.OrderInfoMapper;
import com.hl.fruitmall.mapper.OrdersMapper;
import com.hl.fruitmall.mapper.ShopCarMapper;
import com.hl.fruitmall.service.OSSService;
import com.hl.fruitmall.service.OrdersService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:38
 */
@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ShopCarMapper shopCarMapper;

    @Resource(name = "taskExecutor")
    private Executor taskExecutor;

    @Autowired
    private CommodityInfoMapper commodityInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BalanceUtils balanceUtils;

    @Autowired
    private GlobalUtils globalUtils;

    @Autowired
    private OSSService ossService;

    public ReentrantLock stockLock = new ReentrantLock();

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R createCar(OrderCarVO vo, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        List<Integer> carIds = vo.getCarIds();
        List<ShopCarVO> shopCarList = shopCarMapper.selectByIds(carIds);
        Orders orders = new Orders();
        String orderId = generateOrderId();
        orders.setUserId(userId);
        orders.setAddressId(vo.getAddressId());
        orders.setOrderId(orderId);
        List<OrderInfo> orderInfoList = new ArrayList<>();
        Map<Integer, Integer> infoIdMap = new HashMap<>();
        final BigDecimal[] total = {new BigDecimal(0)};

        shopCarList.stream().forEach(s -> {
            if (!s.getUserId().equals(userId)) {
                throw new GlobalException(ExceptionEnum.ORDER_HAS_ERROR);
            }
            infoIdMap.put(s.getInfoId(), s.getQuantity());
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orderId);
            orderInfo.setCommodityId(s.getCommodityId());
            orderInfo.setCommodityName(s.getCommodityName());
            orderInfo.setInfoId(s.getInfoId());
            orderInfo.setQuantity(s.getQuantity());
            orderInfo.setShopId(s.getShopId());
            orderInfo.setShopName(s.getShopName());
            orderInfo.setStatus(OrderStatusEnum.UNPAID.getCode());
            orderInfoList.add(orderInfo);
            total[0] = total[0].add(s.getPrice().multiply(new BigDecimal(s.getQuantity())));
        });
        try {
            // 减库存需要加锁
            stockLock.lock();
            List<HashMap<String, Integer>> maps = commodityInfoMapper.selectByMap(infoIdMap);
            if (maps.size() < infoIdMap.size()) {
                throw new GlobalException(ExceptionEnum.INVENTORY_SHORTAGE);
            }
            CountDownLatch countDownLatch = new CountDownLatch(infoIdMap.size());
            maps.stream().forEach(m -> {
                taskExecutor.execute(() -> {
                    Integer infoId = m.get("id");
                    decreaseStock(countDownLatch, infoId, infoIdMap.get(infoId));
                });
            });
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new GlobalException(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION);
        } finally {
            stockLock.unlock();
        }
        ordersMapper.insert(orders);
        orderInfoMapper.insertBatch(orderInfoList);
        shopCarMapper.deleteBatch(vo.getCarIds(), userId);
        Map<String, Object> map = createNative(orderId, total[0]);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_ORDER_CANCEL_DLX,
                RabbitConfig.ROUTING_ORDER_CANCEL_DLX,
                orderId);
        return R.ok(new HashMap<String, Object>() {
            {
                put("orderId", orderId);
                put("codeUrl", map.get("code_url"));
                put("price", map.get("total_fee"));
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R create(OrderVO orderVO, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        String orderId = generateOrderId();
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setAddressId(orderVO.getAddressId());
        orders.setUserId(userId);

        OrderInfo orderInfo = orderVO.toOrderInfo();
        orderInfo.setOrderId(orderId);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(orderVO.getInfoId(), orderVO.getQuantity());
        try {
            stockLock.lock();
            List<HashMap<String, Integer>> maps = commodityInfoMapper.selectByMap(map);
            if (maps.size() < map.size()) {
                throw new GlobalException(ExceptionEnum.INVENTORY_SHORTAGE);
            }
            Integer infoId = maps.get(0).get("id");
            commodityInfoMapper.decreaseStock(infoId,
                    maps.get(0).get("stock") - map.get(infoId));
        } finally {
            stockLock.unlock();
        }
        ordersMapper.insert(orders);
        orderInfoMapper.insert(orderInfo);
        EditCommodityInfoVO vo = commodityInfoMapper.selectInfo(orderInfo.getInfoId());
        BigDecimal price = vo.getPrice().multiply(new BigDecimal(orderInfo.getQuantity()));
        Map<String, Object> nativeMap = createNative(orderId, price);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_ORDER_CANCEL_DLX,
                RabbitConfig.ROUTING_ORDER_CANCEL_DLX,
                orderId);
        return R.ok(new HashMap<String, Object>() {
            {
                put("orderId", orderId);
                put("price", price);
                put("codeUrl", nativeMap.get("code_url"));
            }
        });
    }

    @Override
    public R userPage(Integer cur, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        List<UserOrderVO> voList = ordersMapper.selectPage(userId, (cur - 1) * 10);
        Integer total = ordersMapper.getTotal("user_id", userId);
        voList.stream().forEach(vo -> {
            vo.getInfoList().stream().forEach(info -> {
                info.setStatusStr(EnumUtils.getByCode(info.getStatus(), OrderStatusEnum.class));
            });
        });
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", voList);
                put("total", total);
            }
        });
    }

    @Override
    public R get(String orderId) {
        UserOrderVO userOrderVO = ordersMapper.selectByOrderId(orderId);
        return R.ok(userOrderVO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void cancelOrder(String orderId) {
        UserOrderVO userOrderVO = ordersMapper.selectByOrderId(orderId);
        if (userOrderVO.getInfoList().get(0).equals(OrderStatusEnum.CANCEL.getCode())) {
            return;
        }
        Map<String, Object> queryMap = query(orderId);
        if (queryMap != null && !"SUCCESS".equals(queryMap.get("trade_state"))) {
            Map<String, Object> closeMap = close(orderId);
            if (closeMap != null && "SUCCESS".equals(closeMap.get("result_code"))) {
                try {
                    stockLock.lock();
                    CountDownLatch countDownLatch = new CountDownLatch(userOrderVO.getInfoList().size());
                    orderInfoMapper.updateStatus("order_id", orderId, OrderStatusEnum.CANCEL.getCode());
                    userOrderVO.getInfoList().stream().forEach(info -> {
                        increaseStock(countDownLatch, info.getInfoId(), info.getQuantity());
                    });
                } finally {
                    stockLock.unlock();
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R queryOrder(String orderId) {
        Map<String, Object> queryMap = query(orderId);
        if (queryMap != null && "SUCCESS".equals(queryMap.get("trade_state"))) {
            UserOrderVO userOrderVO = ordersMapper.selectByOrderId(orderId);
            // 修改订单状态
            orderInfoMapper.updateStatus("order_id", orderId, OrderStatusEnum.PAID.getCode());
            List<UserOrderInfoVO> infoList = orderInfoMapper.getInfoList(orderId);
            // 给商家加冻结余额
            balanceUtils.increaseFrozen(infoList);

            return R.ok("PAYED");
        }
        return R.ok("PAYING");
    }

    @Override
    public R applyRefund(Integer id) {
        orderInfoMapper.updateStatus("id", id, OrderStatusEnum.REFUNDING.getCode());
        return R.ok();
    }

    @Override
    public R cancel(String orderId) {
        cancelOrder(orderId);
        return R.ok();
    }

    @Override
    public R merchantPage(Integer shopId,
                          Integer cur,
                          Integer userId,
                          String startTime,
                          String endTime,
                          Integer status) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<BackstageOrderVO> list = orderInfoMapper.
                selectPage(shopId, (cur - 1) * 10, userId, start, end, status);
        list.stream().forEach(vo -> {
            vo.setStatusStr(EnumUtils.getByCode(vo.getStatus(), OrderStatusEnum.class));
        });
        Integer total = orderInfoMapper.getTotal("shop_id", shopId, start, end,status);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Override
    public R ship(Map<String, Object> map) {
        String trackNumber = (String) map.get("trackNumber");
        Integer id = (Integer) map.get("id");
        orderInfoMapper.updateTrack(id, trackNumber, OrderStatusEnum.SHIPPING.getCode());
        return R.ok();
    }

    @Override
    public R merchantExport(Integer shopId, String startTime, String endTime, Integer status) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<BackstageOrderVO> list = orderInfoMapper.selectExportData(shopId, start, end, status);
        list.stream().forEach(vo -> {
            vo.setStatusStr(EnumUtils.getByCode(vo.getStatus(), OrderStatusEnum.class));
        });
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, BackstageOrderVO.class).sheet(1).doWrite(list);
        String title = new SimpleDateFormat("yyyy:MM:dd HH:mm:sss").format(new Date());
        String name = title + ".xlsx";
        String url = ossService.uploadExcel(outputStream, name);
        return R.ok(url);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R agree(Integer id) {
        BackstageOrderVO orderInfo = orderInfoMapper.selectById(id);
        // TODO 微信返回支付金额

        // 返还库存
        try {
            stockLock.lock();
            commodityInfoMapper.decreaseStock(id, orderInfo.getQuantity());
        } finally {
            stockLock.unlock();
        }
        // 减商家余额
        balanceUtils.decreaseFrozen(orderInfo.getShopId(),
                orderInfo.getPrice().multiply(new BigDecimal(orderInfo.getQuantity())));

        // 修改订单状态
        orderInfoMapper.updateStatus("id", id, OrderStatusEnum.REFUND.getCode());
        return R.ok();
    }

    @Override
    public R refuse(Integer id) {
        BackstageOrderVO orderInfo = orderInfoMapper.selectById(id);
        if ("".equals(orderInfo.getTrackNumber())) {
            orderInfoMapper.updateStatus("id", id, OrderStatusEnum.PAID.getCode());
        } else {
            orderInfoMapper.updateStatus("id", id, OrderStatusEnum.SHIPPING.getCode());
        }
        return R.ok();
    }

    @Override
    public R adminExport(String startTime, String endTime) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<BackstageOrderVO> list = orderInfoMapper.selectExportData(null, start, end, null);
        list.stream().forEach(vo -> {
            vo.setStatusStr(EnumUtils.getByCode(vo.getStatus(), OrderStatusEnum.class));
        });
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, BackstageOrderVO.class).sheet(1).doWrite(list);
        String title = new SimpleDateFormat("yyyy:MM:dd HH:mm:sss").format(new Date());
        String name = title + ".xlsx";
        String url = ossService.uploadExcel(outputStream, name);
        return R.ok(url);
    }

    @Override
    public R adminPage(Integer id, Integer cur, Integer type, String startTime, String endTime) {
        Date[] dates = globalUtils.strToDate(startTime, endTime);
        Date start = dates[0], end = dates[1];
        List<BackstageOrderVO> list = null;
        Integer total = null;
        if (type.equals(0)) {
            list = orderInfoMapper.
                    selectPage(null, (cur - 1) * 10, id, start, end, null);
            total = orderInfoMapper.getTotalByUserId(id, start, end);
        } else if (type.equals(1)) {
            list = orderInfoMapper.
                    selectPage(id, (cur - 1) * 10, null, start, end, null);
            total = orderInfoMapper.getTotal("shop_id", id, start, end, null);
        }
        list.stream().forEach(vo -> {
            vo.setStatusStr(EnumUtils.getByCode(vo.getStatus(), OrderStatusEnum.class));
        });
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("total", total);
        return R.ok(map);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R confirm(Integer id) {
        BackstageOrderVO backstageOrderVO = orderInfoMapper.selectById(id);
        // 添加商家可提现余额
        balanceUtils.increaseWithdrawAble(backstageOrderVO.getShopId(),
                backstageOrderVO.getPrice().multiply(new BigDecimal(backstageOrderVO.getQuantity())));

        // 修改订单状态
        orderInfoMapper.updateStatus("id", id, OrderStatusEnum.CONFIRM_RECEIPT.getCode());
        return R.ok();
    }

    @Override
    public R getNumberReport(Integer id) {
        List<Map<Date,Integer>> list = ordersMapper.getNumberReport(id);
        return R.ok(list);
    }

    @Override
    public R getPriceReport(Integer id) {
        List<Map<Date,BigDecimal>> list = ordersMapper.getPriceReport(id);
        return R.ok(list);
    }

    @Override
    public R getAdminNumberReport() {
        List<Map<Date, Integer>> list = ordersMapper.getNumberReport(null);
        return R.ok(list);
    }

    @Override
    public R getAdminPriceReport() {
        List<Map<Date, BigDecimal>> list = ordersMapper.getPriceReport(null);
        return R.ok(list);
    }

    @Override
    public R bulkShip(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),
                    BackstageOrderVO.class,
                    new ShipListener(orderInfoMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    public Map<String, Object> query(String orderId) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderId);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            throw new GlobalException(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION);
        }
    }

    public Map<String, Object> close(String orderId) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderId);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            throw new GlobalException(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION);
        }
    }

    private Map<String, Object> createNative(String orderId, BigDecimal total) {
        HashMap<String, String> m = new HashMap<>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", orderId);
        m.put("out_trade_no", orderId);
        m.put("total_fee", total.multiply(new BigDecimal("100")).longValue() + "");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
        m.put("trade_type", "NATIVE");
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            String content = client.getContent();
            Map resultMap = WXPayUtil.xmlToMap(content);

            Map<String, Object> map = new HashMap<>();

            map.put("out_trade_no", orderId);
            map.put("total_fee", total);
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));
            return map;
        } catch (Exception e) {
            throw new GlobalException(ExceptionEnum.CREATE_NATIVE_HAS_ERR);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseStock(CountDownLatch countDownLatch, Integer id, Integer stock) {
        try {
            taskExecutor.execute(() -> {
                commodityInfoMapper.decreaseStock(id,
                        stock);
            });
        } finally {
            countDownLatch.countDown();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void increaseStock(CountDownLatch countDownLatch, Integer id, Integer stock) {
        try {
            taskExecutor.execute(() -> {
                commodityInfoMapper.increaseStock(id,
                        stock);
            });
        } finally {
            countDownLatch.countDown();
        }
    }

    private String generateOrderId() {
        Date now = new Date();
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
        StringBuilder sb = new StringBuilder(date);
        Random random = new Random();
        while (sb.length() < 24) {
            sb.append(Math.abs(random.nextInt() % 9));
        }
        return sb.toString();
    }
}