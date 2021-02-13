package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.enums.OrderStatusEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.EnumUtils;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.common.uitls.TokenUtils;
import com.hl.fruitmall.entity.bean.OrderInfo;
import com.hl.fruitmall.entity.bean.Orders;
import com.hl.fruitmall.entity.bean.ShopCar;
import com.hl.fruitmall.entity.vo.OrderCarVO;
import com.hl.fruitmall.entity.vo.OrderVO;
import com.hl.fruitmall.entity.vo.UserOrderPageVO;
import com.hl.fruitmall.mapper.CommodityInfoMapper;
import com.hl.fruitmall.mapper.OrderInfoMapper;
import com.hl.fruitmall.mapper.OrdersMapper;
import com.hl.fruitmall.mapper.ShopCarMapper;
import com.hl.fruitmall.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    ReentrantLock reentrantLock = new ReentrantLock();

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R createCar(OrderCarVO vo, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        List<Integer> carIds = vo.getCarIds();
        List<ShopCar> shopCarList = shopCarMapper.selectByIds(carIds);
        Orders orders = new Orders();
        String orderId = generateOrderId();
        orders.setUserId(userId);
        orders.setAddressId(vo.getAddressId());
        orders.setOrderId(orderId);
        List<OrderInfo> orderInfoList = new ArrayList<>();
        Map<Integer,Integer> infoIdMap = new HashMap<>();
        shopCarList.stream().forEach(s -> {
            if (!s.getUserId().equals(userId)) {
                throw new GlobalException(ExceptionEnum.ORDER_HAS_ERROR);
            }
            infoIdMap.put(s.getInfoId(),s.getQuantity());
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
        });
        try {
            // 减库存需要加锁
            reentrantLock.lock();
            List<HashMap<String, Integer>> maps = commodityInfoMapper.selectByMap(infoIdMap);
            if (maps.size() < infoIdMap.size()) {
                throw new GlobalException(ExceptionEnum.INVENTORY_SHORTAGE);
            }
            CountDownLatch countDownLatch = new CountDownLatch(infoIdMap.size());
            maps.stream().forEach(m -> {
                taskExecutor.execute(() -> {
                    Integer infoId = m.get("id");
                    decreaseStock(countDownLatch, infoId, (m.get("stock") - infoIdMap.get(infoId)));
                });
            });
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new GlobalException(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION);
        } finally {
            reentrantLock.unlock();
        }
        ordersMapper.insert(orders);
        orderInfoMapper.insertBatch(orderInfoList);
        shopCarMapper.deleteBatch(vo.getCarIds(),userId);
        // TODO 跳转支付
        return R.ok();
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
        Map<Integer,Integer> map = new HashMap<>();
        map.put(orderVO.getInfoId(), orderVO.getQuantity());
        try {
            reentrantLock.lock();
            List<HashMap<String, Integer>> maps = commodityInfoMapper.selectByMap(map);
            if (maps.size() < map.size()) {
                throw new GlobalException(ExceptionEnum.INVENTORY_SHORTAGE);
            }
            Integer infoId = maps.get(0).get("id");
            commodityInfoMapper.decreaseStock(infoId,
                    maps.get(0).get("stock") - map.get(infoId));
        } finally {
            reentrantLock.unlock();
        }
        ordersMapper.insert(orders);
        orderInfoMapper.insert(orderInfo);
        // TODO 跳转支付
        return R.ok();
    }

    @Override
    public R userPage(Integer cur, HttpServletRequest request) {
        Integer userId = TokenUtils.getId(request);
        List<UserOrderPageVO> voList = ordersMapper.selectPage(userId,(cur - 1) * 10);
        Integer total = orderInfoMapper.getTotal("user_id",userId);
        voList.stream().forEach(vo -> {
            vo.getInfoList().stream().forEach(info -> {
                info.setStatusStr(EnumUtils.getByCode(info.getStatus(), OrderStatusEnum.class));
            });
        });
        return R.ok(new HashMap<String,Object>() {
            {
                put("data", voList);
                put("total", total);
            }
        });
    }

    public void decreaseStock(CountDownLatch countDownLatch,Integer id, Integer stock){
        try {
            taskExecutor.execute(() -> {
                commodityInfoMapper.decreaseStock(id,
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