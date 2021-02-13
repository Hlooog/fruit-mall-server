package com.hl.fruitmall.service;

import com.hl.fruitmall.mapper.ShopCarMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Hl
 * @create 2021/2/10 22:15
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShopCarServiceTest {

    @Autowired
    private ShopCarMapper shopCarMapper;

    @Test
    void test(){
        /*List<ShopCar> shopCarList = shopCarMapper.selectByIds(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println(shopCarList);*/
    }
}
