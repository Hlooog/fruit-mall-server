package com.hl.fruitmall.service;

import com.hl.fruitmall.entity.vo.UserOrderInfoPageVO;
import com.hl.fruitmall.entity.vo.UserOrderPageVO;
import com.hl.fruitmall.mapper.OrderInfoMapper;
import com.hl.fruitmall.mapper.OrdersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Hl
 * @create 2021/2/13 10:39
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersServiceTest {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Test
    void test(){
        List<UserOrderInfoPageVO> infoList = orderInfoMapper.getInfoList("202102121714170788685804");
        System.out.println(infoList);
    }

    @Test
    void test1(){
        List<UserOrderPageVO> voList = ordersMapper.selectPage(10049, 0);
        System.out.println(voList);
    }
}
