package com.hl.fruitmall.service;

import com.hl.fruitmall.entity.vo.BackstageOrderVO;
import com.hl.fruitmall.entity.vo.UserOrderInfoVO;
import com.hl.fruitmall.entity.vo.UserOrderVO;
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
    void test() {
        List<UserOrderInfoVO> infoList = orderInfoMapper.getInfoList("202102121714170788685804");
        System.out.println(infoList);
    }

    @Test
    void test1() {
        List<UserOrderVO> voList = ordersMapper.selectPage(10049, 0);
        System.out.println(voList);
    }

    @Test
    void test2() {
        /*UserOrderVO userOrderVO = ordersMapper.selectByOrderId("202102141106331620043786");
        System.out.println(userOrderVO);*/
//        BigDecimal total = new BigDecimal(0);
       /* for (UserOrderInfoVO info : userOrderVO.getInfoList()) {
            System.out.println(info.getPrice().multiply(new BigDecimal(info.getQuantity())));
            if (!info.getStatus().equals(OrderStatusEnum.UNPAID.getCode())){
                throw new GlobalException(ExceptionEnum.ORDER_HAS_ERROR);
            }
            total = total.add(info.getPrice().multiply(new BigDecimal(info.getQuantity())));
        }
        System.out.println(total);*/

        List<BackstageOrderVO> list = orderInfoMapper.selectPage(10004, 0, 10049, null, null, null);
        System.out.println(list);

    }

}
