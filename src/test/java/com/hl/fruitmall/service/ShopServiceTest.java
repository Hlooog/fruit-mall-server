package com.hl.fruitmall.service;

import com.hl.fruitmall.entity.vo.ShopPageVO;
import com.hl.fruitmall.mapper.ShopMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Hl
 * @create 2021/1/28 17:52
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShopServiceTest {
    @Autowired
    private ShopMapper shopMapper;
    @Test
    void test(){
        List<ShopPageVO> list = shopMapper.selectPage(0, "", null, null, 117);
        System.out.println(list);
    }
}
