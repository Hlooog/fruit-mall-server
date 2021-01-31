package com.hl.fruitmall.service;

import com.hl.fruitmall.entity.vo.CommodityListVO;
import com.hl.fruitmall.mapper.CommodityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Hl
 * @create 2021/1/29 9:59
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommodityServiceTest {
    @Autowired
    private CommodityMapper commodityMapper;

    @Test
    void test(){
        List<CommodityListVO> commodityListVOS = commodityMapper.selectList(10000);
        System.out.println(commodityListVOS);
    }

    @Test
    void test1(){
        BigDecimal b1 = new BigDecimal(1);
        BigDecimal b2 = new BigDecimal(2);
        BigDecimal b3 = new BigDecimal(1);
        System.out.println("b1 == b2" + b1.compareTo(b2));
        System.out.println("b2 == b1" + b2.compareTo(b1));
        System.out.println("b1 == b3" + b1.compareTo(b3));
        System.out.println(b1.subtract(b2));
    }

    @Test
    void test2(){
        String url = "https://hl-fruit-mall.oss-cn-guangzhou.aliyuncs.com/2020/12/31/a344482f74ba402886fe9bf8dff1e315file";
        url = url.substring(url.indexOf("/", 8) + 1);
        System.out.println(url);
    }
}
