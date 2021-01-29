package com.hl.fruitmall.service;

import com.hl.fruitmall.entity.vo.CommodityPageVO;
import com.hl.fruitmall.mapper.CommodityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        List<CommodityPageVO> commodityPageVOS = commodityMapper.selectPage(10000);
        System.out.println(commodityPageVOS);
    }
}
